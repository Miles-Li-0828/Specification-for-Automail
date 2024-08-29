import java.util.*;

/**
 * Floor Robots
 *
 */
public class FloorRobot extends Robot
{
    private int targetRoom = 1;
    private boolean movingToCR = false;
    private List<Integer> signals = new ArrayList<>();

    /**
     * Constructor
     */
    public FloorRobot(int capacity) {super(capacity);}

    /**
     * getters and setters
     */
    public List<Integer> getSignals() {return signals;}
//    public void setSignals(Queue<Integer> signals) {this.signals = signals;}
    public boolean isMovingToCR() {return movingToCR;}
    public int getTargetRoom() {return targetRoom;}
    public void setMovingToCR(boolean movingToCR) {this.movingToCR = movingToCR;}

    /**
     * Robot engine for Flooring Mode
     *
     * @param robotsController: the robot controller
     */
    @Override
    public void engine(RobotsController robotsController)
    {
        if (super.isEmpty() && movingToCR)
        {
            System.out.println("Signal empty? " + this.signals.toString());
            if (super.getRoom() < targetRoom)
            {
                super.move(Direction.RIGHT, robotsController);
            }
            else if (super.getRoom() > targetRoom)
            {
                super.move(Direction.LEFT, robotsController);
            }
            else
            {
                movingToCR = false;
                // signals.remove(targetRoom);
            }
        }
        // If still has items, deliver them all
        else if (!super.isEmpty())
        {
            System.out.println("signal empty? " + this.signals.toString());
            movingToCR = false;
            if (super.getRoom() == super.getItems().getFirst().myRoom())
            {
                //then deliver all relevant items to that room
                do
                {
                    Item deliverItem = super.getItems().removeFirst();
                    int itemWeight = deliverItem instanceof Parcel p ? p.myWeight() : 0;
                    super.setCapacity(super.getCapacity() + itemWeight);
                    Simulation.deliver(deliverItem);
                } while (!super.getItems().isEmpty() && super.getRoom() == super.getItems().getFirst().myRoom());
            }
            else if (super.getRoom() < super.getItems().getFirst().myRoom())
            {
                super.move(Direction.RIGHT, robotsController);
            }
            else if (super.getRoom() > super.getItems().getFirst().myRoom())
            {
                super.move(Direction.LEFT, robotsController);
            }
        }
        // If robot has no items
        else if (super.isEmpty() && !movingToCR)
        {
            System.out.println(this.getId() + " " + this.signals.toString() + " ");
            Building building = Building.getBuilding();
            // if a robot sends a signal to me, move to it
            if (!this.signals.isEmpty() && building.isOccupied(super.getFloor(), signals.getFirst() + 1))
            {
                System.out.println(this.getId() + " " + this.signals.toString() + " ");
                targetRoom = signals.removeFirst();
                System.out.println(this.getId() + " " + this.signals.toString() + " ");
                movingToCR = true;
                if (super.getRoom() < targetRoom)
                {
                    super.move(Direction.RIGHT, robotsController);
                }
                else if (super.getRoom() > targetRoom)
                {
                    super.move(Direction.LEFT, robotsController);
                }
            }
            // else, do nothing
        }
    }

}
