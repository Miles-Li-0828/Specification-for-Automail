import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import static java.lang.String.format;

/**
 * The Robot class
 *  Utilised Factory pattern,
 *  define a family of robots, Encapsulate each one, use different kind in different mode
 *  Increase the code re-usability and reduce the cost of maintaining and operation if
 *  there are more modes in the future in this software.
 * @ Author: Miles Li; Skylar Khant; Lam Nguyen
 * @ Since: 22/08/2024
 */
public abstract class Robot
{
    private static int count = 1;
    final private String id;
    private int floor;
    private int room;
    private int capacity;
    private final int MAX_CAPACITY;
    private List<Item> items = new ArrayList<>();

    /**
     * Constructor
     */
    public Robot(int capacity)
    {
        this.id = "R" + count++;
        this.capacity = capacity;
        MAX_CAPACITY = capacity;
    }

    public String toString()
    {
        return "Id: " + id + " Floor: " + floor + ", Room: " + room + ", #items: " + numItems() + ", capacity: " + capacity ;
    }


    /**
     * Getters
     */
    int getFloor() { return floor; }
    int getRoom() { return room; }
    public String getId() { return id; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int newCapacity) { capacity = newCapacity; }
    public void setItems(List<Item> items) {this.items = items;}
    public int getMAX_CAPACITY() {return MAX_CAPACITY;}

    boolean isEmpty() { return items.isEmpty(); }
    public List<Item> getItems() {return items;}

    /**
     * Place a robot in the specific room
     *
     * @param floor: floor number
     * @param room: room number
     */
    public void place(int floor, int room)
    {
        Building building = Building.getBuilding();
        building.place(floor, room, id);
        this.floor = floor;
        this.room = room;
    }

    /**
     * Move a robot
     *
     * @param direction: direction
     * @param robotsController: The robot controller to return a robot
     */
    protected void move(Direction direction, RobotsController robotsController)
    {
        Building building = Building.getBuilding();

        boolean[][] occupiedRooms = building.getOccupied();

        int dfloor, droom;
        assert occupiedRooms[floor][room] : format("move from unoccupied position floor=%d; room=%d", floor, room);
        switch (direction)
        {
            case UP ->
            {
                assert floor < building.NUMFLOORS + 1 : format("attempt to move above building floor=%d; room=%d", floor, room);
                assert room == 0 || room == building.NUMROOMS + 1 : format("attempt to move up through ceiling floor=%d; room=%d", floor, room);
                dfloor = floor + 1;
                droom = room;
            }
            case DOWN ->
            {
                assert floor > 0 : format("attempt to move below mailroom floor=%d; room=%d", floor, room);
                assert room == 0 || room == building.NUMROOMS + 1 : format("attempt to move down through floor floor=%d; room=%d", floor, room);
                dfloor = floor - 1;
                droom = room;
            }
            case LEFT ->
            {
                assert room > 1 : format("attempt to move left outside building floor=%d; room=%d", floor, room);
                dfloor = floor;
                droom = room - 1;
            }
            case RIGHT ->
            {
                assert room < building.NUMROOMS + 1 : format("attempt to move right outside building floor=%d; room=%d", floor, room);
                dfloor = floor;
                droom = room + 1;
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + direction);
        }
        if (!building.isOccupied(dfloor, droom))
        {
            // If the destination is occupied, do nothing
            building.drawMove(floor, room, dfloor, droom, id); // need to modify here
            floor = dfloor; room = droom;
            if (floor == 0)
            {
                System.out.printf("About to return: " + this + "\n");
                robotsController.robotReturn(this);
            }
        }
    }


    /**
     * robot's engine
     */
    public abstract void engine(RobotsController robotsController);

    /**
     * Get the number of Items that the robot contain
     * * Need to modify*
     *
     * @return int: the number of items
     */
    public int numItems ()
    {
        return items.size();
    }

    /**
     * Add a new Item to robot
     * * need to modify*
     *
     * @param item: new item
     */
    public boolean add(Item item)
    {
        int itemWeight = item instanceof Parcel p ? p.myWeight() : 0;

        if (capacity >= itemWeight)
        {
            items.add(item);
            capacity -= itemWeight;
            return true;
        }
        return false;
    }

    /**
     * Sort the letters.
     * * I don't think this should be done by robot*
     */
    void sort()
    {
        Collections.sort(items);
    }
}
