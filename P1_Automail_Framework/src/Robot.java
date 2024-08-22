import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * The Robot class
 * <p>
 * @ Author: Miles Li; Skylar Khant; Lam Nguyen
 * @ Since: 22/08/2024
 */
public class Robot
{
    private static int count = 1;
    final private String id;
    private int floor;
    private int room;
    final private MailRoom mailroom;
    final private List<Letter> letters = new ArrayList<>();

    /**
     * Constructor
     *
     * @param mailroom: create a robot in specific mail room
     */
    public Robot(MailRoom mailroom)
    {
        this.id = "R" + count++;
        this.mailroom = mailroom;
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
     */
    private void move(Building.Direction direction)
    {
        Building building = Building.getBuilding();
        int dfloor, droom;
        switch (direction)
        {
            case UP    -> {dfloor = floor+1; droom = room;}
            case DOWN  -> {dfloor = floor-1; droom = room;}
            case LEFT  -> {dfloor = floor;   droom = room-1;}
            case RIGHT -> {dfloor = floor;   droom = room+1;}
            default -> throw new IllegalArgumentException("Unexpected value: " + direction);
        }
        if (!building.isOccupied(dfloor, droom))
        {
            // If destination is occupied, do nothing
            building.move(floor, room, direction, id);
            floor = dfloor; room = droom;
            if (floor == 0)
            {
                System.out.printf("About to return: " + this + "\n");
                mailroom.robotReturn(this);
            }
        }
    }

    /**
     * * Need to Modify *
     * Transfer the item to another robot
     *
     * @param robot: antother robot
     */
    void transfer(Robot robot)
    {
        // Transfers every item assuming receiving robot has capacity
        ListIterator<Letter> iter = robot.letters.listIterator();
        while(iter.hasNext())
        {
            Letter letter = iter.next();
            this.add(letter); //Hand it over
            iter.remove();
        }
    }

    /**
     * Time simulation
     */
    void tick()
    {
            Building building = Building.getBuilding();
            // Circle mode
            if (letters.isEmpty())
            {
                // Return to MailRoom
                if (room == building.NUMROOMS + 1)
                {
                    // in right end column
                    move(Building.Direction.DOWN);  //move towards mailroom
                }
                else
                {
                    move(Building.Direction.RIGHT); // move towards right end column
                }
            }
            else
            {
                // Items to deliver
                if (floor == letters.getFirst().myFloor())
                {
                    // On the right floor
                    if (room == letters.getFirst().myRoom())
                    {
                        //then deliver all relevant items to that room
                        do
                        {
                            Simulation.deliver(letters.removeFirst());
                        } while (!letters.isEmpty() && room == letters.getFirst().myRoom());
                    }
                    else
                    {
                        move(Building.Direction.RIGHT); // move towards next delivery
                    }
                }
                else
                {
                    move(Building.Direction.UP); // move towards floor
                }
            }
    }

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
