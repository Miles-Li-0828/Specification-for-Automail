/**
 * The robot only for CYCLING mode
 */
public class CyclingRobot extends Robot
{
    /**
     * Constructor
     */
    public CyclingRobot() {super();}

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
            if (super.getFloor() == super.getLetters().getFirst().myFloor())
            {
                // On the right floor
                if (super.getRoom() == super.getLetters().getFirst().myRoom())
                {
                    //then deliver all relevant items to that room
                    do
                    {
                        Simulation.deliver(super.getLetters().removeFirst());
                    } while (!super.getLetters().isEmpty() && super.getRoom() == super.getLetters().getFirst().myRoom());
                }
                else
                {
                    move(Building.Direction.RIGHT, robotsController); // move towards next delivery
                }
            }
            else
            {
                move(Building.Direction.UP, robotsController); // move towards floor
            }
        }
    }
}
