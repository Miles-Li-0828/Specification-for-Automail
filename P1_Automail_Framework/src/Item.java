/**
 * Item, including Letter and parcel
 *
 * @Author: Miles Li; Skylar Khant; Lam Nguyen
 * @Since: 21/08/2024
 */
public abstract class Item implements Comparable<Item>
{
    private int floor;
    private int room;
    private int arrival;

    /**
     * Constructor of Item
     *
     * @param floor: Target floor number
     * @param room: Target room number
     * @param arrival: if arrival?
     */
    public Item (int floor, int room, int arrival)
    {
        this.floor = floor;
        this.room = room;
        this.arrival = arrival;
    }

    /**
     * Getters
     */
    public int myFloor() {return floor;}
    public int myRoom() {return room;}
    public int myArrival() {return arrival;}

    @Override public int compareTo(Item i)
    {
        int floorDiff = this.floor - i.floor;  // Don't really need this as only deliver to one floor at a time
        return (floorDiff == 0) ? this.room - i.room : floorDiff;
    }

    public abstract String toString();
}
