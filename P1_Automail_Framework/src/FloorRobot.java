import java.util.*;

/**
 * Floor Robots
 *
 */
public class FloorRobot extends Robot
{
    private boolean toRight = false;

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
        ListIterator<Item> iter = super.getItems().listIterator();
        while (iter.hasNext())
        {
            Item item = iter.next();
            iter.remove();
            if (super.getRoom() == item.myRoom())
            {
                Simulation.deliver(item);
                List<Item> remainingItems = super.getItems();
                remainingItems.remove(item);
                super.setItems(remainingItems);
            }
            else if (super.getRoom() < item.myRoom())
            {
                super.move(Direction.RIGHT, robotsController);
                toRight = true;
            }
            else
            {
                super.move(Direction.LEFT, robotsController);
                toRight = false;
            }
        }

        // If robot has no items and
        // If robot is head to a column robot, move to it
        // If robot is right next to a colum robot, wait for transfer
        // No column robots ahead and no items, do nothing
    }

    /**
     * Detect if there's a column robot waiting on the right side of this robot
     */
    private boolean detectRight()
    {
        return true;
    }

    /**
     * Detect if there's a column robot waiting on the left side of this robot
     */
    private boolean detectLeft()
    {
        return true;
    }
}
