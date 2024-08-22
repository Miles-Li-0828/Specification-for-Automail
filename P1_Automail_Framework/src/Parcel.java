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
        return super.toString() + "Weight: " + weight;
    }

    int myFloor() { return super.getFloor(); }
    int myRoom() { return super.getRoom(); }
    int myArrival() { return super.getArrival(); }
    int myWeight() {return weight;}
}
