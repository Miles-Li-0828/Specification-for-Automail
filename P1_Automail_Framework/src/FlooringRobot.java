import java.util.ListIterator;

public class FlooringRobot extends Robot
{
    /**
     * Constructor
     */
    public FlooringRobot(int capacity) {super(capacity);}

    /**
     * Robot engine for Flooring Mode
     *
     * @param robotsController
     */
    @Override
    public void engine(RobotsController robotsController)
    {
        return;
    }

    /**
     * * Need to Modify *
     * Transfer the item to another robot
     *
     * @param robot: antother robot
     */
    void transfer(Robot robot)
    {
        // Transfers every item assuming receiving robot has capacity
        ListIterator<Item> iter = robot.getItems().listIterator();
        while(iter.hasNext())
        {
            Item item = iter.next();
            this.add(item); //Hand it over
            iter.remove();
        }
    }
}
