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

    public void addRoom(IRoom room){
        rooms.add(room);
    }


    public IRoom getARoom(String roomId) {
        for (IRoom room : rooms) {
            if (roomId.equals(room.getRoomNumber())) {
                return room;
            }
        }
        return null;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkIn, Date checkOut) {
        Reservation reservation = new Reservation(customer, room, checkIn, checkOut);
        reservations.add(reservation);
        return reservation;
    }


    public Collection<IRoom> findRooms(Date checkIn, Date checkOut) {

        List<IRoom> findAvailableRooms = new ArrayList<>();
        for (IRoom room : rooms) {
            for (Reservation reservation : reservations) {
                if (room.getRoomNumber().equals(reservation.getRoom().getRoomNumber()) &&
                        (checkIn.before(reservation.getCheckInDate()) &&
                                checkOut.before(reservation.getCheckInDate())) ||
                        (checkIn.after(reservation.getCheckOutDate()) &&
                                checkOut.after(reservation.getCheckOutDate()))) {
                    findAvailableRooms.add(room);
                }
            }
        }
        System.out.println(findAvailableRooms);
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
}

