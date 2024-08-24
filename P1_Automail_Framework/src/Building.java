import static java.lang.String.format;

/**
 * Building class to present building in auto-mail system
 *
 * @Author: Miles Li; Skylar Khant; Lam Nguyen
 * @Since: 21/08/2024
 */
public class Building
{
    private static boolean initialised = false;
    private static Building singleton = null;
    final public int NUMFLOORS;
    final public int NUMROOMS;
    private boolean[][] occupied;
    public enum Direction {LEFT, RIGHT, UP, DOWN}


    private static BuildingGrid bg;

    private static int NUMF;
    private static int NUMR;

    /**
     * Constructor of Building Singleton
     */
    private Building()
    {
        // System.out.println("Building constructor");
        this.NUMFLOORS = NUMF;
        this.NUMROOMS = NUMR;
        occupied = new boolean[NUMFLOORS+1][NUMROOMS+2]; // robot space in building, initialised to zero (false)
        bg = new BuildingGrid(NUMFLOORS, NUMROOMS);
    }

    /**
     * getters
     */
    public boolean[][] getOccupied() {return occupied;}

    /**
     * Initialise the Building
     *
     * @param numFloors: The Number of floors
     * @param numRooms: The number of Rooms
     */
    public static void initialise(int numFloors, int numRooms)
    {
        assert !initialised : "Attempt to reinitialise Building";
        assert numFloors > 0 : "Non-positive numFloors";
        assert numRooms > 0 : "Non-positive numRooms";
        NUMF = numFloors;
        NUMR = numRooms;
        initialised = true;
    }


    /**
     * Create the singleton Building
     *
     * @return Building
     */
    public static Building getBuilding()
    {
        if (singleton == null)
        {
            assert initialised : "Failure to initialise Building";
            singleton = new Building();
        }
        return singleton;
    }


    /**
     * Check if the specific room is occupied
     *
     * @param floor: floor number
     * @param room: room number
     * @return boolean: if the room is occupie
     */
    boolean isOccupied(int floor, int room)
    {
        return occupied[floor][room];
    }

    /**
     * Remove the robot from the specific room
     *
     * @param floor: floor number
     * @param room: room number
     */
    void remove(int floor, int room)
    {
        assert occupied[floor][room] : format("remove from unoccupied position floor=%d; room=%d", floor, room);
        occupied[floor][room] = false;
        bg.update(floor, room, "");  // Display
    }

    /**
     * Place a robot in a specific room
     *
     * @param floor: floor number
     * @param room: room number
     * @param id: robot id
     */
    void place(int floor, int room, String id)
    {
        assert !occupied[floor][room] : format("place at occupied position floor=%d; room=%d", floor, room);
        occupied[floor][room] = true;
        bg.update(floor, room, id);  // Display
    }

    /**
     * Update the room status in the building based on the positions of Robots
     *
     * @param floor: the floor number before robot move
     * @param room: the room number after robot move
     * @param dfloor: the floor number after robot move
     * @param droom: teh room number after robot move
     * @param id: Robot id
     */
    void updateRoomStatus(int floor, int room, int dfloor, int droom, String id)
    {
        assert occupied[floor][room] : format("move from unoccupied position floor=%d; room=%d", floor, room);
        assert !occupied[dfloor][droom] : format("attempt move to occupied position floor=%d; room=%d", dfloor, droom);
        occupied[floor][room] = false;
        bg.update(floor, room, ""); // Display
        occupied[dfloor][droom] = true;
        bg.update(dfloor, droom, id); // Display
    }
}
