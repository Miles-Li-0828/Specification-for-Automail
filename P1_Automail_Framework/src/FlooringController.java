import java.util.*;

/**
 * The controller for Flooring mode
 * @author Miles Li
 * @since 26/08/2024
 */
public class FlooringController extends RobotsController
{
    private List<Robot> columnRobots = new ArrayList<>();
    private List<Robot> floorRobots = new ArrayList<>();
    private boolean started = false;

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
            if (i < 2)
            {
                ColumnRobot columnRobot = new ColumnRobot(robotCapacity);
                idleRobots.add(columnRobot);
                columnRobots.add(columnRobot);
            }
            else
            {
                floorRobots.add(new FloorRobot(robotCapacity));
            }
        }
        super.setIdleRobots(idleRobots);
    }

    /**
     * Getters
     */
    public List<Robot> getColumnRobots() {return columnRobots;}
    public List<Robot> getFloorRobots() {return floorRobots;}

    @Override
    public void tick()
    {
        if (!started)
        {
            initialiseFloorRobots();
            started = true;
        }

        for (Robot robot: floorRobots)
        {
            robot.engine(this);
        }

        super.tick();

    }

    @Override
    public void robotDispatch()
    {
        // Dispatch all the robots in flooring case
        System.out.println("Dispatch at time = " + Simulation.now());

        while (!super.getIdleRobots().isEmpty())
        {
            int fwei = super.getMailRoom().floorWithEarliestItem();
            if (fwei >= 0)
            {
                Robot robot = super.loadAndActivateRobot(fwei);
                if (robot.getId().equals("R1")) {
                    robot.place(0, 0);
                } else if (robot.getId().equals("R2")) {
                    robot.place(0, Building.getBuilding().NUMROOMS + 1);
                    robot.sortRtoL();
                }
            }
        }
    }

    /**
     * Initialise floor robots to their floors
     */
    public void initialiseFloorRobots()
    {
        int floor = 1;
        for (Robot robot: floorRobots)
        {
            robot.place(floor++, 1);
        }
    }
}
