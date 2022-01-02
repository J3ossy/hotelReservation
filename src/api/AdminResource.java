package api;

import model.*;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    private static final AdminResource SINGLETON = new AdminResource();
    private AdminResource(){}

    public static AdminResource getSINGLETON() {
        return SINGLETON;
    }

    private final CustomerService cs = CustomerService.getSingleton();
    private final ReservationService rs = ReservationService.getSingleton();

    public Customer getCustomer(String email) {
        return cs.getCustomer(email);
    }

    /*
    public void addRoom(List<IRoom> rooms) {
        rooms.forEach(rs::addRoom);
    */

    public Collection<IRoom> getAllRooms() {
        return rs.getAllRooms();
    }

    public Collection<Customer> getAllCustomers(){
        return cs.getAllCustomer();
    }

    public void displayAllReservations(){
        rs.printAllReservation();
    }
}
