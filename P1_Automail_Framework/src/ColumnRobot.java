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

    }

    /**
     * Transfer the item to another robot
     *
     * @param robot: another robot
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
