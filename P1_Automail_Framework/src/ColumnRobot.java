import java.util.ArrayList;
import java.util.ListIterator;

public class ColumnRobot extends Robot
{
    /**
     * Constructor
     */
    public ColumnRobot(int capacity) {super(capacity);}

    @Override
    public void engine(RobotsController robotsController)
    {
        // 100% sure this controller is FlooringController
        FlooringController flooringController = (FlooringController)robotsController;

        // If this robot contains items, move to the corresponded floor
        if (!this.getItems().isEmpty())
        {
            int targetFloor = this.getItems().getFirst().myFloor();
            if (super.getFloor() < targetFloor)
            {
                super.move(Direction.UP, robotsController);
            }
            else if (super.getFloor() > targetFloor)
            {
                super.move(Direction.DOWN, robotsController);
            }
            else
            {


            }
        }
        else
        {
            super.move(Direction.DOWN, robotsController);
        }
    }

    /**
     * Transfer the item to another robot
     *
     * @param robot: another robot
     */
    void transfer(Robot robot)
    {
        // Transfers every item assuming receiving robot has capacity
        ListIterator<Item> iter = super.getItems().listIterator();
        while(iter.hasNext())
        {
            Item item = iter.next();
            if (robot.add(item)) //Hand it over
            {
                if (item instanceof Parcel parcel)
                    super.setCapacity(super.getCapacity() + parcel.myWeight());
                iter.remove();
            }
            else
                break;
        }
    }
}
