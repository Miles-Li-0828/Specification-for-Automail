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
        if (!this.isEmpty())
        {
            if (this.getFloor() != this.getItems().getFirst().myFloor())
            {
                super.move(Direction.UP, robotsController);
            }

            if (this.getFloor() == this.getItems().getFirst().myFloor())
            {
                Building building = Building.getBuilding();
                // 1 floor different
                FloorRobot fr = (FloorRobot) flooringController.getFloorRobots().get(super.getFloor() - 1);

                if (super.getId().equals("R1") &&
                        building.isOccupied(super.getFloor(), super.getRoom() + 1) &&
                        fr.isEmpty() && !fr.isMovingToCR())
                {
                    transfer(fr);
                    fr.setWaitingForItems(true);
                }
                else if (super.getId().equals("R2") &&
                        building.isOccupied(super.getFloor(), super.getRoom() - 1) &&
                        fr.isEmpty() && !fr.isMovingToCR())
                {
                    transfer(fr);
                    fr.setWaitingForItems(true);
                }
                // else if robot is not next to me, send a signal to it
                else if (!fr.isMovingToCR())
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
        if (robot instanceof FloorRobot fr && fr.isEmpty())
        {
            // Transfers every item assuming receiving robot has capacity
            robot.setItems(super.getItems());
            robot.setCapacity(super.getCapacity());
            super.setItems(new ArrayList<>());
            super.setCapacity(super.getMAX_CAPACITY());
        }
    }

    /**
     * Send signal to floor robot and ask it to come to this side
     */
    void sendSignal(FloorRobot fr)
    {
        int targetRoom = this.getRoom() == 0 ? 1 : Building.getBuilding().NUMROOMS;
        if (!fr.getSignals().containsKey(targetRoom))
        {
            fr.getSignals().put(targetRoom, Simulation.now());
        }
    }
}
