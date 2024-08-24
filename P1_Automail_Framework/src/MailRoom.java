import java.util.*;

import static java.lang.String.format;

/**
 * The mail room in the building
 *
 * @ Author: Miles Li; Skylar Khant; Lam Nguyen
 * @ Since: 22/08/2024
 */
public class MailRoom
{
    // Mode should be controlled by Simulation
    List<Letter>[] waitingForDelivery; // 这里Letter要改成item

    Queue<Robot> idleRobots;
    List<Robot> activeRobots;
    List<Robot> deactivatingRobots; // Don't treat a robot as both active and idle by swapping directly

    /**
     * Constructor of Mailroom
     *
     * @param numFloors: The number of floors
     */
    MailRoom(int numFloors)
    {
        waitingForDelivery = new List[numFloors];
        for (int i = 0; i < numFloors; i++)
        {
            waitingForDelivery[i] = new LinkedList<>();
        }
    }

    /**
     * Getter of waiting items
     */
    public List<Letter>[] getWaitingForDelivery() {return waitingForDelivery;}

    /**
     * * Here need to be improved *
     * Judge if there are items left in the mail room
     *
     * @return boolean: There are items left
     */
    public boolean hasItemsForDelivery()
    {
        for (int i = 0; i < Building.getBuilding().NUMFLOORS; i++)
        {
            // i represent the floor number
            if (!waitingForDelivery[i].isEmpty())
            {
                    return true;
            }
        }
        return false;
    }

    /**
     * Find the floor number that contains the earliest item
     * * need to modify *
     *
     * @return int: floor number
     */
    public int floorWithEarliestItem()
    {
        int floor = -1;
        int earliest = Simulation.now() + 1;
        for (int i = 0; i < Building.getBuilding().NUMFLOORS; i++)
        {
            if (!waitingForDelivery[i].isEmpty())
            {
                int arrival = waitingForDelivery[i].getFirst().myArrival();
                if (earliest > arrival)
                {
                    floor = i;
                    earliest = arrival;
                }
            }
        }
        return floor;
    }

    /**
     * Sort Items based on Items' features
     */
    public void sortItems()
    {
        for (int i = 0; i < Building.getBuilding().NUMFLOORS; i++)
        {
            Collections.sort(waitingForDelivery[i]);
        }
    }


    /**
     * Put new arrived items to responded floor
     *
     * @param items: Items
     */
    public void arrive(List<Letter> items)
    {
        for (Letter item : items)
        {
            waitingForDelivery[item.myFloor()-1].add(item);
            System.out.printf("Item: Time = %d Floor = %d Room = %d Weight = %d\n",
                    item.myArrival(), item.myFloor(), item.myRoom(), 0);
        }
    }
}
