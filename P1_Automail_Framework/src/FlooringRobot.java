import java.util.ListIterator;

public class FlooringRobot extends Robot
{
    /**
     * Constructor
     */
    public FlooringRobot() {super();}

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
        ListIterator<Letter> iter = robot.getLetters().listIterator();
        while(iter.hasNext())
        {
            Letter letter = iter.next();
            this.add(letter); //Hand it over
            iter.remove();
        }
    }
}
