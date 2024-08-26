/**
 * The robot only for CYCLING mode
 */
public class CyclingRobot extends Robot
{
    /**
     * Constructor
     */
    public CyclingRobot(int capacity) {super(capacity);}

    /**
     * The robot engine for Cycling Robot
     *
     * @param robotsController: The robot controller for returning the robot
     */
    @Override
    public void engine(RobotsController robotsController)
    {
        Building building = Building.getBuilding();
        // Circle mode
        if (super.isEmpty())
        {
            // Return to MailRoom
            if (super.getRoom() == building.NUMROOMS + 1)
            {
                // in right end column
                super.move(Building.Direction.DOWN, robotsController);  //move towards mailroom
            }
            else
            {
                super.move(Building.Direction.RIGHT, robotsController); // move towards right end column
            }
        }
        else
        {
            // Items to deliver
            if (super.getFloor() == super.getItems().getFirst().myFloor())
            {
                // On the right floor
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
                else
                {
                    super.move(Building.Direction.RIGHT, robotsController); // move towards next delivery
                }
            }
            else
            {
                super.move(Building.Direction.UP, robotsController); // move towards floor
            }
        }
    }
}
