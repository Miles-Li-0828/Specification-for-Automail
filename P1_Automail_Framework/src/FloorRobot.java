import java.util.*;

/**
 * Floor Robots
 *
 */
public class FloorRobot extends Robot
{
    private int targetRoom = 1;
    private boolean movingToCR = false;
    private Map<Integer, Integer> signals = new HashMap<>();

    /**
     * Constructor
     */
    public FloorRobot(int capacity) {super(capacity);}

    /**
     * getters and setters
     */
    public Map<Integer, Integer> getSignals() {return signals;}
    public boolean isMovingToCR() {return movingToCR;}
    public int getTargetRoom() {return targetRoom;}

    public void moveToCRDirection(RobotsController robotsController)
    {
        if (super.getRoom() < targetRoom)
        {
            super.move(Direction.RIGHT, robotsController);
        }
        else if (super.getRoom() > targetRoom)
        {
            super.move(Direction.LEFT, robotsController);
        }
        if (super.getRoom() == targetRoom)
        {
            movingToCR = false;
        }
    }
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
            moveToCRDirection(robotsController);
        }
        // If still has items, deliver them all
        else if (!super.isEmpty())
        {
            movingToCR = false;
            if (super.getRoom() == super.getItems().getFirst().myRoom())
            {
                //then deliver all relevant items to that room
                super.deliverAllItemsInCurrentRoom();
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
            // if a robot sends a signal to me, move to it
            if (!this.signals.isEmpty())
            {
                if (signals.size() == 2)
                {
                    targetRoom = signals.get(1) <= signals.get(Building.getBuilding().NUMROOMS) ?
                            1 : Building.getBuilding().NUMROOMS;
                    signals.remove(targetRoom);
                }
                else
                {
                    Map.Entry<Integer, Integer> signalEntry = signals.entrySet().iterator().next();
                    targetRoom = signalEntry.getKey();
                    signals.remove(targetRoom);
                }
                movingToCR = true;
                moveToCRDirection(robotsController);
            }
            // else, do nothing
        }
    }

}
