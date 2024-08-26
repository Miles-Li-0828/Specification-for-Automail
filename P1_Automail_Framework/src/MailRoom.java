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
    List<Item>[] waitingForDelivery; // 这里Letter要改成item

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
    public List<Item>[] getWaitingForDelivery() {return waitingForDelivery;}

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
     * Put new arrived items to responded floor
     *
     * @param items: Items
     */
    public void arrive(List<Item> items)
    {
        for (Item item : items)
        {
            waitingForDelivery[item.myFloor()-1].add(item);
            System.out.println(item.toString());
        }
    }
}
