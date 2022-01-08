package model;

public class Room implements IRoom {

    private final String roomNumber;
    private final Double roomPrice;
    private final RoomType enumeration;

    public Room(String roomNumber, Double roomPrice, RoomType enumeration){
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.enumeration = enumeration;
    }

    @Override
    public String toString() {
        return "Room number: " + roomNumber + " ,Room price: " + roomPrice +
                ", Room type; " + enumeration;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return roomPrice;
    }

    @Override
    public RoomType getRoomType() {
        return enumeration;
    }

    @Override
    public boolean isFree() {
        return false;
    }
}
