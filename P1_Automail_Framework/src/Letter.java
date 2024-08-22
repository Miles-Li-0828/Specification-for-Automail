public class Letter extends Item
{
    Letter(int floor, int room, int arrival)
    {
        super(floor, room, arrival);
    }

    public String toString()
    {
        return super.toString();
    }

    int myFloor() { return super.getFloor(); }
    int myRoom() { return super.getRoom(); }
    int myArrival() { return super.getArrival(); }
}
