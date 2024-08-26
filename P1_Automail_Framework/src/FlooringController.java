import java.util.LinkedList;
import java.util.Queue;

/**
 * The controller for Flooring mode
 * @author Miles Li
 * @since 26/08/2024
 */
public class FlooringController extends RobotsController
{
    /**
     * Constructor
     *
     * @param numRobots     : The number of Robots
     * @param numFloors
     * @param robotCapacity
     */
    public FlooringController(int numRobots, int numFloors, int robotCapacity)
    {
        super(numRobots, numFloors, robotCapacity);
        Queue<Robot> idleRobots = new LinkedList<>();
        for (int i = 0; i < numRobots; i++)
        {
            idleRobots.add(new FlooringRobot(robotCapacity));
        }
        super.setIdleRobots(idleRobots);
    }


    @Override
    public void tick()
    {
        return;
    }

    @Override
    public void robotDispatch()
    {
        return;
    }
}
