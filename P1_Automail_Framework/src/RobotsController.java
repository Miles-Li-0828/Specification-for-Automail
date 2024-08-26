import java.util.*;

import static java.lang.String.format;

/**
 * Pure fabrication Class: Robot controller
 * Control the behaviours of all the robots, reduce the coupling between MailRoom and Robot
 * Increase the cohesion of MailRoom
 * Apply Factory Pattern:
 * Delegate object creation and control to subclass, and abstracts the objects creation.
 * Encapsulate objects and increase the code re-usability.
 * @author Miles Li, Skylar Khant
 * @Since: 26/08/2024
 */
public class RobotsController
{
    // Mode should be controlled by Simulation
    private final int numRobots;
    private int robotCapacity;

    Queue<Robot> idleRobots;
    List<Robot> activeRobots;
    List<Robot> deactivatingRobots; // Don't treat a robot as both active and idle by swapping directly

    private final MailRoom mailRoom;

    /**
     * Constructor
     *
     * @param numRobots: The number of Robots
     */
    public RobotsController(int numRobots, int numFloors, int robotCapacity)
    {
        this.numRobots = numRobots;
        this.robotCapacity = robotCapacity;
        activeRobots = new ArrayList<>();
        deactivatingRobots = new ArrayList<>();

        mailRoom = new MailRoom(numFloors);
    }

    /**
     * Getters
     */
    public MailRoom getMailRoom() {return mailRoom;}
    public List<Robot> getActiveRobots() {return activeRobots;}
    public List<Robot> getDeactivatingRobots() {return deactivatingRobots;}
    public Queue<Robot> getIdleRobots() {return idleRobots;}
    public int getRobotCapacity() {return robotCapacity;}

    /**
     * Setters
     */
    public void setActiveRobots(List<Robot> activeRobots) {this.activeRobots = activeRobots;}
    public void setDeactivatingRobots(List<Robot> deactivatingRobots) {this.deactivatingRobots = deactivatingRobots;}
    public void setIdleRobots(Queue<Robot> idleRobots) {this.idleRobots = idleRobots;}
    public void setRobotCapacity(int robotCapacity) {this.robotCapacity = robotCapacity;}

    /**
     * Time tick simulation
     */
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

    /**
     * Dispatch the robots
     */
    void robotDispatch()
    {
        // Can dispatch at most one robot; it needs to move out of the way for the next
        System.out.println("Dispatch at time = " + Simulation.now());
        // Need an idle robot and space to dispatch (could be a traffic jam)

        if (!idleRobots.isEmpty() && !Building.getBuilding().isOccupied(0,0))
        {
            int fwei = mailRoom.floorWithEarliestItem();
            if (fwei >= 0)
            {  // Need an item or items to deliver, starting with earliest
                Robot robot = idleRobots.remove();
                loadRobot(fwei, robot);
                // Room order for left to right delivery
                robot.sort();
                activeRobots.add(robot);
                System.out.println("Dispatch @ " + Simulation.now() +
                        " of Robot " + robot.getId() + " with " + robot.numItems() + " item(s)");
                robot.place(0, 0);
            }
        }
    }

    /**
     * Robot return to mail room
     *
     * @param robot: robot
     */
    void robotReturn(Robot robot)
    {
        Building building = Building.getBuilding();
        int floor = robot.getFloor();
        int room = robot.getRoom();
        assert floor == 0 && room == building.NUMROOMS+1: format("robot returning from wrong place - floor=%d, room ==%d", floor, room);
        assert robot.isEmpty() : "robot has returned still carrying at least one item";
        building.remove(floor, room);
        deactivatingRobots.add(robot);
    }

    /**
     * Load Item to Robot
     *
     * @param floor: floor number
     * @param robot: specific robot
     */
    void loadRobot(int floor, Robot robot)
    {
        ListIterator<Item> iter = mailRoom.getWaitingForDelivery()[floor].listIterator();
        while (iter.hasNext())
        {
            // In timestamp order
            Item item = iter.next();
            if (robot.add(item))  //Hand it over
            {
                iter.remove();
            }

        }
    }
}
