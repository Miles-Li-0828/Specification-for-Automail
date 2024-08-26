public class Letter extends Item
{
    Letter(int floor, int room, int arrival)
    {
        super(floor, room, arrival);
    }

    public String toString()
    {
        return "Floor: " + super.myFloor() + ", Room: " + super.myRoom() + ", Arrival: " + super.myArrival();
    }

}
