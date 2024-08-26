import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import static java.lang.String.format;

/**
 * The Robot class
 *  Utilised Template pattern,
 *  define a family of robots, Encapsulate each one, make them interchangeable.
 *  Increase the code re-usability and reduce the cost of maintaining and operation if
 *  there are more modes in the future in this software.
 *
 * <p>
 * @ Author: Miles Li; Skylar Khant; Lam Nguyen
 * @ Since: 22/08/2024
 */
public abstract class Robot
{
    private static int count = 1;
    final private String id;
    private int floor;
    private int room;
    final private List<Letter> letters = new ArrayList<>();

    /**
     * Constructor
     */
    public Robot()
    {
        this.id = "R" + count++;
    }

    public String toString()
    {
        return "Id: " + id + " Floor: " + floor + ", Room: " + room + ", #items: " + numItems() + ", Load: " + 0 ;
    }


    /**
     * Getters
     */
    int getFloor() { return floor; }
    int getRoom() { return room; }
    public String getId() {return id;}
    boolean isEmpty() { return letters.isEmpty(); }
    public List<Letter> getLetters() {return letters;}

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
    protected void move(Building.Direction direction, RobotsController robotsController)
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
            // If destination is occupied, do nothing
            building.updateRoomStatus(floor, room, dfloor, droom, id); // need to modify here
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
        return letters.size();
    }

    /**
     * Add a new Item to robot
     * * need to modify*
     *
     * @param item: new item
     */
    public void add(Letter item)
    {
        letters.add(item);
    }

    /**
     * Sort the letters.
     * * I don't think this should be done by robot*
     */
    void sort()
    {
        Collections.sort(letters);
    }
}
