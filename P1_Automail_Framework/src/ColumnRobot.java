import java.util.ArrayList;
import java.util.ListIterator;

public class ColumnRobot extends Robot
{
    /**
     * Constructor
     */
    public ColumnRobot(int capacity) {super(capacity);}

    @Override
    public void engine(RobotsController robotsController)
    {
        // 100% sure this controller is FlooringController
        FlooringController flooringController = (FlooringController)robotsController;

        // If this robot contains items, move to the corresponded floor
        if (!this.getItems().isEmpty())
        {
            int targetFloor = this.getItems().getFirst().myFloor();
            if (super.getFloor() < targetFloor)
            {
                super.move(Direction.UP, robotsController);
            }
            else if (super.getFloor() > targetFloor)
            {
                super.move(Direction.DOWN, robotsController);
            }
            else
            {
                // 1 floor different
                FloorRobot fr = (FloorRobot) flooringController.getFloorRobots().get(super.getFloor() - 1);
                // If robot next to me, transfer
                if (super.getRoom() == 0 && fr.getRoom() == 1 && fr.isWaiting())
                {
                    transfer(fr);
                }
                else if (super.getRoom() == Building.getBuilding().NUMROOMS + 1 &&
                        fr.getRoom() == Building.getBuilding().NUMROOMS && fr.isWaiting())
                {
                    transfer(fr);
                }
                // else if robot is not next to me, send a signal to it
                else
                {
                    sendSignal(fr);
                }
            }
        }
        else
        {
            super.move(Direction.DOWN, robotsController);
        }
    }

    /**
     * Transfer the item to another robot
     *
     * @param robot: another robot
     */
    void transfer(Robot robot)
    {
        if (robot instanceof FloorRobot fr && fr.isWaiting())
        {
            // Transfers every item assuming receiving robot has capacity
            robot.setItems(super.getItems());
            robot.setCapacity(super.getCapacity());
            super.setItems(new ArrayList<>());
            super.setCapacity(super.getMAX_CAPACITY());
            fr.setMovingToCR(false);
        }
    }

    /**
     * Send signal to floor robot and ask it to come to this side
     */
    void sendSignal(FloorRobot fr)
    {
        if (!fr.getSignals().contains(this))
            fr.getSignals().add(this);
    }
}
