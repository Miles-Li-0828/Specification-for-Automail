import java.util.*;

/**
 * The controller for Flooring mode
 * @author Miles Li
 * @since 26/08/2024
 */
public class FlooringController extends RobotsController
{
    private List<Robot> columnRobots = new ArrayList<>();
    private List<Robot> floorRobot = new ArrayList<>();

    /**
     * Constructor
     *
     * @param numRobots: The number of Robots
     * @param numFloors: The number of floors
     * @param robotCapacity: The max capacity of robots
     */
    public FlooringController(int numRobots, int numFloors, int robotCapacity)
    {
        super(numRobots, numFloors, robotCapacity);
        Queue<Robot> idleRobots = new LinkedList<>();
        for (int i = 0; i < numRobots; i++)
        {
            idleRobots.add(new FloorRobot(robotCapacity));
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
