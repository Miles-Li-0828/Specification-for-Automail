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
            System.out.println(robot.isEmpty());
            robot.engine(this);
        }

        // Simulation time unit
        for (Robot activeRobot : super.getActiveRobots())
        {
            System.out.printf("About to tick: " + activeRobot.toString() + "\n");
            activeRobot.engine(this);
        }
        robotDispatch();  // dispatch a robot if conditions are met
        // These are returning robots who shouldn't be dispatched in the previous step
        ListIterator<Robot> iter = super.getDeactivatingRobots().listIterator();
        while (iter.hasNext())
        {
            // In timestamp order
            Robot robot = iter.next();
            iter.remove();
            List<Robot> activeRobots = super.getActiveRobots();
            Queue<Robot> idleRobots = super.getIdleRobots();
            activeRobots.remove(robot);
            idleRobots.add(robot);
            super.setActiveRobots(activeRobots);
            super.setIdleRobots(idleRobots);
        }




        return;
    }

    @Override
    public void robotDispatch()
    {
        // Dispatch all the robots in flooring case
        System.out.println("Dispatch at time = " + Simulation.now());

        if (!super.getIdleRobots().isEmpty())
        {
            int fwei = super.getMailRoom().floorWithEarliestItem();
            Robot robot = super.getIdleRobots().remove();
            loadRobot(fwei, robot);
            robot.sortLtoR();

            List<Robot> activeRobots = super.getActiveRobots();
            activeRobots.add(robot);
            super.setActiveRobots(activeRobots);
            System.out.println("Dispatch @ " + Simulation.now() +
                    " of Robot " + robot.getId() + " with " + robot.numItems() + " item(s)");
            if (robot.getId().equals("R1"))
            {
                robot.place(0, 0);
            }
            else if (robot.getId().equals("R2"))
            {
                robot.place(0, Building.getBuilding().NUMROOMS + 1);
                robot.sortRtoL();
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
