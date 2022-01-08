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

    public void addRoom(IRoom room) {
        rooms.add(room);
    }


    public IRoom getARoom(String roomId) {
        IRoom foundRoom = null;
        for (IRoom room : rooms) {
            if (roomId.equals(room.getRoomNumber())) {
                foundRoom = room;
            }
        }
        return foundRoom;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkIn, Date checkOut) {
        Reservation reservation = new Reservation(customer, room, checkIn, checkOut);
        reservations.add(reservation);
        return reservation;
    }

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

    public Collection<Reservation> getCustomersReservations(Customer customer) {
        List<Reservation> reservationsByCustomer = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                reservationsByCustomer.add(reservation);
            }
        }
        return reservationsByCustomer;
    }

    public Collection<IRoom> getAllRooms() {
        return rooms;
    }

    public Collection<Reservation> printAllReservation() {
        if (reservations.isEmpty()) {
            System.out.println("no reservations were made!");
        }
        return reservations;
    }

    public Collection<Reservation> getReservations() {
        return reservations;
    }
}

