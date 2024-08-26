import java.util.*;

/**
 * The controller for Cycling mode
 * @author Miles Li
 * @since 26/08/2024
 */
public class CyclingController extends RobotsController
{
    /**
     * Constructor
     *
     * @param numRobots: The number of Robots
     * @param numFloors: The number of floors
     * @param robotCapacity: The max capacity of robot
     */
    public CyclingController(int numRobots, int numFloors, int robotCapacity)
    {
        super(numRobots, numFloors, robotCapacity);
        Queue<Robot> idleRobots = new LinkedList<>();
        for (int i = 0; i < numRobots; i++)
        {
            idleRobots.add(new CyclingRobot(robotCapacity));
        }
        super.setIdleRobots(idleRobots);
    }

    /**
     * Time tick simulation
     */
    @Override
    public void tick()
    {
        // Simulation time unit
        for (Robot activeRobot : activeRobots)
        {
            System.out.printf("About to tick: " + activeRobot.toString() + "\n");
            activeRobot.engine(this);
        }
        robotDispatch();  // dispatch a robot if conditions are met
        // These are returning robots who shouldn't be dispatched in the previous step
        ListIterator<Robot> iter = deactivatingRobots.listIterator();
        while (iter.hasNext())
        {
            // In timestamp order
            Robot robot = iter.next();
            iter.remove();
            activeRobots.remove(robot);
            idleRobots.add(robot);
        }
    }
}
