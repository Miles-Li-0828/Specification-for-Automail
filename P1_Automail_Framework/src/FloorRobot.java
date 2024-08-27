import java.util.*;

/**
 * Floor Robots
 *
 */
public class FloorRobot extends Robot
{
    private boolean toRight = false;
    private boolean toLeft = false;

    /**
     * Constructor
     */
    public FloorRobot(int capacity) {super(capacity);}

    /**
     * Robot engine for Flooring Mode
     *
     * @param robotsController: the robot controller
     */
    @Override
    public void engine(RobotsController robotsController)
    {
        // If still has items, deliver them all
        if (!super.getItems().isEmpty())
        {
            Item item = super.getItems().removeFirst();
            if (super.getRoom() == item.myRoom())
            {
                Simulation.deliver(item);
                List<Item> remainingItems = super.getItems();
                remainingItems.remove(item);
                super.setItems(remainingItems);
                if (item instanceof Parcel parcel)
                {
                    this.setCapacity(this.getCapacity() + parcel.myWeight());
                }
            }
            else if (super.getRoom() < item.myRoom())
            {
                super.move(Direction.RIGHT, robotsController);
            }
            else
            {
                super.move(Direction.LEFT, robotsController);
            }
        }
        // If robot has no items
        else
        {
            // If there's a Column Robot next to this robot, transfer items
            if (super.getRoom() == 1 && detectLeft())
            {
                toRight = toLeft = false;
            }
            else if (super.getRoom() == Building.getBuilding().NUMROOMS - 2 && detectRight())
            {
                toRight = toLeft = false;
            }
            else
            {
                if (detectRight() && !toLeft)
                {
                    super.move(Direction.RIGHT, robotsController);
                    toRight = true;
                    toLeft = false;
                }
                else if (detectLeft() && !toRight)
                {
                    super.move(Direction.LEFT, robotsController);
                    toLeft = true;
                    toRight = false;
                }
            }
        }
    }

    /**
     * Detect if there's a column robot waiting on the right side of this robot
     */
    private boolean detectRight()
    {
        boolean[][] occupied = Building.getBuilding().getOccupied();
        return occupied[super.getFloor()][Building.getBuilding().NUMROOMS - 1];
    }

    /**
     * Detect if there's a column robot waiting on the left side of this robot
     */
    private boolean detectLeft()
    {
        boolean[][] occupied = Building.getBuilding().getOccupied();
        return occupied[super.getFloor()][0];
    }
}
