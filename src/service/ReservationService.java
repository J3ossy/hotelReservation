package service;

import model.*;

import java.util.*;

public class ReservationService {

    private static final ReservationService SINGLETON = new ReservationService();

    private ReservationService() {
    }

    public static ReservationService getSingleton() {
        return SINGLETON;
    }

    private static final Collection<IRoom> rooms = new HashSet<>();
    private static final Collection<Reservation> reservations = new HashSet<>();

    /**
     * This method adds a room to the room collection/HasSet
     *
     * @param room The room which will be added
     */
    public void addRoom(IRoom room) {
        rooms.add(room);
    }

    /**
     * This method searches by the roomId if the room exists
     *
     * @param roomId The room Number to look for
     * @return foundRoom Returns the room with the same room Number or Null if there is no equal room number
     */
    public IRoom getARoom(String roomId) {
        IRoom foundRoom = null;
        for (IRoom room : rooms) {
            if (roomId.equals(room.getRoomNumber())) {
                foundRoom = room;
            }
        }
        return foundRoom;
    }

    /**
     * This method creates a reservation for a specific room and adds to the reservation collection/hasSet
     *
     * @param customer The customer which reserves the room
     * @param room     The room that gets reserved
     * @param checkIn  The check-in date
     * @param checkOut The check-out date
     * @return reservation Returns the reservation that was made
     */
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkIn, Date checkOut) {
        Reservation reservation = new Reservation(customer, room, checkIn, checkOut);
        reservations.add(reservation);
        return reservation;
    }

    /**
     * This method finds available rooms within a date range
     *
     * @param checkIn  The check-in date
     * @param checkOut The check-out date
     * @return findAvailableRooms Returns an ArrayList with the available rooms for the date range
     */
    public Collection<IRoom> findRooms(Date checkIn, Date checkOut) {

        Collection<IRoom> findAvailableRooms = new ArrayList<>(rooms);
        Collection<Reservation> allReservations = getReservations();

        for (Reservation reservation : allReservations) {
            if (checkIn.before(reservation.getCheckOutDate()) && checkOut.after(reservation.getCheckInDate())) {
                findAvailableRooms.remove(reservation.getRoom());
            }
        }
        return findAvailableRooms;
    }

    /**
     * This method gets all reservation by an given customer
     *
     * @param customer The customer which we want the reservation from
     * @return reservationByCustomer Returns an arrayList with all reservation for that customer
     */
    public Collection<Reservation> getCustomersReservations(Customer customer) {
        List<Reservation> reservationsByCustomer = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                reservationsByCustomer.add(reservation);
            }
        }
        return reservationsByCustomer;
    }

    /**
     * This method gets all Rooms existing
     *
     * @return rooms Returns HasSet of all rooms in the Hotel
     */
    public Collection<IRoom> getAllRooms() {
        return rooms;
    }

    /**
     * This method gets all reservations existing and prints an information if there are no reservations at all
     *
     * @return reservations Returns a HasSet of all reservation in the hotel
     */
    public Collection<Reservation> printAllReservation() {
        if (reservations.isEmpty()) {
            System.out.println("no reservations were made!");
        }
        return reservations;
    }

    /**
     * This method gets all reservations existing
     *
     * @return reservations Returns a HasSet of all reservation in the hotel
     */
    Collection<Reservation> getReservations() {
        return reservations;
    }
}

