package model;

public class Room implements IRoom {

    private String roomNumber;
    private Double roomPrice;
    private RoomType enumeration;

    public Room(String roomNumber, Double roomPrice, RoomType enumeration){
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.enumeration = enumeration;
    }

    @Override
    public String toString() {
        return "Room number: " + roomNumber + ",Room price: " + roomPrice +
                ", Room type; " + enumeration;
    }

    @Override
    public String getRoomNumber() {
        return null;
    }

    @Override
    public Double getRoomPrice() {
        return null;
    }

    @Override
    public RoomType getRoomType() {
        return null;
    }

    @Override
    public boolean isFree() {
        return false;
    }
}
