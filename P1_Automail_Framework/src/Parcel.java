public class Parcel extends Item
{
    private int weight;

    Parcel(int floor, int room, int arrival, int weight)
    {
        super(floor, room, arrival);
        this.weight = weight;
    }

    public String toString()
    {
        return "Floor: " + super.myFloor() + ", Room: " + super.myRoom() + ", Arrival: " + super.myArrival() + ", Weight: " + weight;
    }

    int myWeight() {return weight;}
}
