package api;

import model.*;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {

    private static final HotelResource SINGLETON = new HotelResource();
    private HotelResource(){}

    public static HotelResource getSINGLETON() {
        return SINGLETON;
    }

    private final CustomerService cs = CustomerService.getSingleton();
    private final ReservationService rs =  ReservationService.getSingleton();

    public Customer getCustomer(String email) {
        return cs.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) {
        cs.addCustomer(email, firstName, lastName);
    }

    public IRoom getRooms(String roomNumber) {
        return rs.getARoom(roomNumber);
    }

    public Reservation bookARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        return rs.reserveARoom(customer, room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
       return rs.getCustomersReservations(getCustomer(customerEmail));
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut){
        return rs.findRooms(checkIn,checkOut);
    }
}
