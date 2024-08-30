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
     * Dispatch the robots
     */
    @Override
    public void robotDispatch()
    {
        // Can dispatch at most one robot; it needs to move out of the way for the next
        System.out.println("Dispatch at time = " + Simulation.now());
        // Need an idle robot and space to dispatch (could be a traffic jam)

        if (!super.getIdleRobots().isEmpty() && !Building.getBuilding().isOccupied(0,0))
        {
            int fwei = super.getMailRoom().floorWithEarliestItem();
            if (fwei >= 0)
            {
                Robot robot = super.loadAndActivateRobot(fwei);
                robot.place(0, 0);
            }
        }
    }
}
