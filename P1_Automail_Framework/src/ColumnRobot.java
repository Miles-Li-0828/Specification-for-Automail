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
            if (super.getFloor() != targetFloor)
            {
                super.move(Direction.UP, robotsController);
            }
            else
            {
                // If the Floor Robot is next to this robot, transfer items. Otherwise, wait
                if (super.getRoom() == 0 &&
                        Building.getBuilding().isOccupied(super.getFloor(), super.getRoom() + 1))
                {
                    transfer(flooringController.getColumnRobots().get(super.getFloor()));
                }
                else if (super.getRoom() == Building.getBuilding().NUMROOMS - 1 &&
                            Building.getBuilding().isOccupied(super.getFloor(), super.getRoom() - 1))
                {
                    transfer(flooringController.getColumnRobots().get(super.getFloor()));
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
        // Transfers every item assuming receiving robot has capacity
        robot.setItems(this.getItems());
        robot.setCapacity(this.getCapacity());
        this.setCapacity(this.getMAX_CAPACITY());
        this.setItems(new ArrayList<>());
    }
}
