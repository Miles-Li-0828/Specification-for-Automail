// Check that maxweight (of parcel) is less than or equal to the maxcapacity of robot.

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * The controller for the whole system: Simulation
 *
 * @Author: Miles Li; Skylar Khant; Lam Nguyen
 * @Since: 22/08/2024
 */
public class Simulation
{
    private static final Map<Integer, List<Item>> waitingToArrive = new HashMap<>();
    private static int time = 0;
    public final int endArrival;
    private RobotsController robotsController;
    private static int timeout;

    private static int deliveredCount = 0;
    private static int deliveredTotalTime = 0;

    /**
     * Constructor
     *
     * @param properties: properties
     */
    Simulation(Properties properties)
    {
        int seed = Integer.parseInt(properties.getProperty("seed"));
        Random random = new Random(seed);
        this.endArrival = Integer.parseInt(properties.getProperty("mail.endarrival"));
        int numLetters = Integer.parseInt(properties.getProperty("mail.letters"));
        int numParcels = Integer.parseInt(properties.getProperty("mail.parcels"));
        int maxWeight = Integer.parseInt(properties.getProperty("mail.parcelmaxweight"));
        int numFloors = Integer.parseInt(properties.getProperty("building.floors"));
        int numRooms = Integer.parseInt(properties.getProperty("building.roomsperfloor"));
        int numRobots = Integer.parseInt(properties.getProperty("robot.number"));
        int robotCapacity = Integer.parseInt(properties.getProperty("robot.capacity"));
        timeout = Integer.parseInt(properties.getProperty("timeout"));

        Mode mode = Mode.valueOf(properties.getProperty("mode"));

        Building.initialise(numFloors, numRooms);
        Building building = Building.getBuilding();

        // Factory method
        switch (mode)
        {
            case Mode.CYCLING:
                this.robotsController = new CyclingController(numRobots, numFloors, robotCapacity);
                break;
            case Mode.FLOORING:
                this.robotsController = new FlooringController(numFloors + 2, numFloors, robotCapacity);
                break;
        }

        for (int i = 0; i < numLetters; i++)
        {
            //Generate letters
            int arrivalTime = random.nextInt(endArrival)+1;
            int floor = random.nextInt(building.NUMFLOORS)+1;
            int room = random.nextInt(building.NUMROOMS)+1;
            addToArrivals(arrivalTime, new Letter(floor, room, arrivalTime));
        }
        for (int i = 0; i < numParcels; i++)
        {
            // Generate parcels
            int arrivalTime = random.nextInt(endArrival)+1;
            int floor = random.nextInt(building.NUMFLOORS)+1;
            int room = random.nextInt(building.NUMROOMS)+1;
            int weight = random.nextInt(maxWeight)+1;
            addToArrivals(arrivalTime, new Parcel(floor, room, arrivalTime, weight));
            // What am I going to do with all these values?
        }
    }

    /**
     * Deliver the item
     * * Should be in the Robot class *
     *
     * @param mailItem: Item
     */
    public static void deliver(Item mailItem)
    {
        System.out.println("Delivered: " + mailItem);
        deliveredCount++;
        deliveredTotalTime += now() - mailItem.myArrival();
    }

    /**
     * Mark the items which are arrived already
     *
     * @param arrivalTime: arrival time
     * @param item: specific item
     */
    void addToArrivals(int arrivalTime, Item item)
    {
        System.out.println(item.toString());
        if (waitingToArrive.containsKey(arrivalTime))
        {
            waitingToArrive.get(arrivalTime).add(item);
        }
        else
        {
            LinkedList<Item> items = new LinkedList<>();
            items.add(item);
            waitingToArrive.put(arrivalTime, items);
        }
    }

    /**
     * The time now
     *
     * @return int: current time
     */
    public static int now() { return time; }

    /**
     * What will happen in every time step
     */
    void step()
    {
        // External events
        if (waitingToArrive.containsKey(time))
            robotsController.getMailRoom().arrive(waitingToArrive.get(time));
        // Internal events
        robotsController.tick();
    }

    /**
     * Run the simulation
     */
    void run()
    {
        while (time++ <= endArrival || robotsController.getMailRoom().hasItemsForDelivery())
        {
            step();
            try
            {
                TimeUnit.MILLISECONDS.sleep(timeout);
            }
            catch (InterruptedException e)
            {
                System.out.println("Sleep interrupted!");
            }
        }
        System.out.printf("Finished: Items delivered = %d; Average time for delivery = %.2f%n",
                deliveredCount, (float) deliveredTotalTime/deliveredCount);
    }

}
