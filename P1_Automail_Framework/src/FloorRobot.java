import java.util.*;

/**
 * Floor Robots
 *
 */
public class FloorRobot extends Robot
{
    private int targetRoom;
    private boolean waiting = false;
    private boolean movingToCR = false;
    private Queue<ColumnRobot> signals = new LinkedList<>();

    /**
     * Constructor
     */
    public FloorRobot(int capacity) {super(capacity);}

    /**
     * getters and setters
     */
    public boolean isWaiting() {return waiting;}
    public Queue<ColumnRobot> getSignals() {return signals;}
    public void setSignals(Queue<ColumnRobot> signals) {this.signals = signals;}
    public boolean isMovingToCR() {return movingToCR;}
    public void setMovingToCR(boolean movingToCR) {this.movingToCR = movingToCR;}

    /**
     * Robot engine for Flooring Mode
     *
     * @param robotsController: the robot controller
     */
    @Override
    public void engine(RobotsController robotsController)
    {
        if (movingToCR)
        {
            waiting = false;
            if (super.getRoom() < targetRoom)
            {
                super.move(Direction.RIGHT, robotsController);
            }
            else
            {
                super.move(Direction.LEFT, robotsController);
            }
        }
        // If still has items, deliver them all
        else if (!super.getItems().isEmpty())
        {
            movingToCR = false;
            waiting = false;
            Item item = super.getItems().removeFirst();
            targetRoom = item.myRoom();
            if (super.getRoom() == targetRoom)
            {
                Simulation.deliver(item);
                super.getItems().remove(item);
                if (item instanceof Parcel parcel)
                {
                    this.setCapacity(this.getCapacity() + parcel.myWeight());
                }
            }
            else if (super.getRoom() < targetRoom)
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
            waiting = true;
            // if a robot sends a signal to me, move to it
            if (!signals.isEmpty() && !movingToCR)
            {
                ColumnRobot cr = signals.remove();
                waiting = false;
                movingToCR = true;
                targetRoom = cr.getRoom() == 0 ? 1 : Building.getBuilding().NUMROOMS;
            }
            // else, do nothing
        }
    }
}
