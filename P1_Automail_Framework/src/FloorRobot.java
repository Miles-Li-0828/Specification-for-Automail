import java.util.*;

/**
 * Floor Robots
 *
 */
public class FloorRobot extends Robot
{
    private boolean toRight = false;
    private boolean toLeft = false;
    private boolean waiting = false;

    /**
     * Constructor
     */
    public FloorRobot(int capacity) {super(capacity);}
    public boolean isWaiting() {return waiting;}

    /**
     * Robot engine for Flooring Mode
     *
     * @param robotsController: the robot controller
     */
    @Override
    public void engine(RobotsController robotsController)
    {
        // If still has items, deliver them all
        if (!super.getItems().isEmpty())
        {
            waiting = false;
            Item item = super.getItems().removeFirst();
            if (super.getRoom() == item.myRoom())
            {
                Simulation.deliver(item);
                if (item instanceof Parcel parcel)
                {
                    this.setCapacity(this.getCapacity() + parcel.myWeight());
                }
            }
            else if (super.getRoom() < item.myRoom())
            {
                super.move(Direction.RIGHT, robotsController);
            }
            else
            {
                super.move(Direction.LEFT, robotsController);
            }
        }
        // If robot has no items
        else
        {


        }
    }

    /**
     * Detect if there's a column robot waiting on the right side of this robot
     */
    private boolean detectRight()
    {
        boolean[][] occupied = Building.getBuilding().getOccupied();
        return occupied[super.getFloor()][Building.getBuilding().NUMROOMS + 1];
    }

    /**
     * Detect if there's a column robot waiting on the left side of this robot
     */
    private boolean detectLeft()
    {
        boolean[][] occupied = Building.getBuilding().getOccupied();
        return occupied[super.getFloor()][0];
    }
}
