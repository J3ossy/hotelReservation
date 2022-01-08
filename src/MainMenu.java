import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.*;

public class MainMenu {

    private static final HotelResource hr = HotelResource.getSINGLETON();

    private static final DateTimeFormatter dateFormat = new DateTimeFormatterBuilder()
            .parseStrict()
            .appendPattern("dd/MM/uuu")
            .toFormatter()
            .withResolverStyle(ResolverStyle.STRICT);

    public static void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        int selection = 0;
        boolean keepRunning = true;
        printMainMenu();

        while (keepRunning) {
            try {
                selection = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid Input! Enter an Integer (number) between 1 and 5");
                printMainMenu();
            }

            switch (selection) {
                case 1:
                    findAndReserveARoom();
                    break;
                case 2:
                    seeMyReservation();
                    break;
                case 3:
                    createAccount();
                    break;
                case 4:
                    AdminMenu.adminMenu();
                    break;
                case 5:
                    System.out.println("Exit");
                    keepRunning = false;
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Input! The number must be within 1 and 5!");
                    printMainMenu();
                    break;
            }
        }
    }

    private static void createAccount() {
        final Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your email (format: name@example.com):");
        String email = scanner.nextLine();
        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();

        try {
            hr.createACustomer(email, firstName, lastName);
            System.out.println("Account created");
            printMainMenu();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            createAccount();
        }
    }

    public static void seeMyReservation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your Email:");
        String email = scanner.nextLine();
        Customer customer = hr.getCustomer(email);

        try {
            if (Objects.isNull(customer)) {
                System.out.println("Account not found, please enter a valid email or create a new account");
                printMainMenu();
            } else {
                Collection<Reservation> reservations = hr.getCustomersReservations(email);
                if (reservations.isEmpty()) {
                    System.out.println("No reservation for " + customer.toString());
                    printMainMenu();
                } else
                    for (Reservation reservation : reservations) {
                        System.out.println(reservation.toString());
                        printMainMenu();
                    }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            seeMyReservation();
        }
    }

    public static void findAndReserveARoom() {
        Customer customer;
        String checkIn;
        String checkOut;
        String roomNr;
        Date dateCheckIn = null;
        Date dateCheckOut = null;
        String pattern = "dd/MM/yyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        boolean isDateValid;
        Collection<IRoom> availableRooms;
        String seeFreeRoomsNextWeek;


        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you have an account with us? (y/n): ");
        String hasAccount = scanner.nextLine().toLowerCase();

        switch (hasAccount) {
            case "y":
                System.out.println("Please enter your Email:");
                String email = scanner.nextLine();
                customer = hr.getCustomer(email);
                try {
                    if (Objects.isNull(customer)) {
                        System.out.println("Account not found, please enter a valid email or create a new account");
                        printMainMenu();
                    }
                } catch (Exception e) {
                    e.getMessage();
                }

                do {
                    System.out.println("Please enter check In Date in format dd/mm/yyyy: ");
                    checkIn = scanner.nextLine();
                    isDateValid = isValidDate(checkIn, dateFormat);

                    if (isDateValid)
                        dateCheckIn = getDate(checkIn, simpleDateFormat);

                } while (!isDateValid);

                do {
                    System.out.println("Please enter check Out Date in format dd//mm/yyyy: ");
                    checkOut = scanner.nextLine();
                    isDateValid = isValidDate(checkOut, dateFormat);

                    if (isDateValid) {
                        dateCheckOut = getDate(checkOut, simpleDateFormat);

                        if (!dateCheckIn.before(dateCheckOut)) {
                            System.out.println("Check out date can't be before check in!");
                            isDateValid = false;
                        }
                    }
                } while (!isDateValid);

                availableRooms = hr.findARoom(dateCheckIn, dateCheckOut);
                for (IRoom room : availableRooms) {
                    System.out.println(room.toString());
                }

                if (availableRooms.isEmpty()) {
                    System.out.println("There are no more rooms for this date.\n");

                    do {
                        System.out.println("Would you like to see rooms available in the next 7 days? (y/n): ");
                        seeFreeRoomsNextWeek = scanner.nextLine().toLowerCase().trim();
                    } while (!seeFreeRoomsNextWeek.equals("y") && (!seeFreeRoomsNextWeek.equals("n")));

                    if (seeFreeRoomsNextWeek.equals("y")) {

                        dateCheckIn = new Date(dateCheckIn.getTime() + Duration.ofDays(7).toMillis());
                        dateCheckOut = new Date(dateCheckOut.getTime() + Duration.ofDays(7).toMillis());
                        availableRooms = hr.findARoom(dateCheckIn, dateCheckOut);
                        for (IRoom room : availableRooms) {
                            System.out.println(room.toString());
                        }
                        if (availableRooms.isEmpty()) {
                            System.out.println("There are no more rooms for this date.\n");
                        } else {
                            System.out.println("Enter the number of the room you'd like to reserve: ");
                            roomNr = scanner.nextLine();
                            hr.bookARoom(customer, hr.getRooms(roomNr), dateCheckIn, dateCheckOut);
                        }
                        printMainMenu();
                    }
                }

                System.out.println("Enter the number of the room you'd like to reserve: ");
                roomNr = scanner.nextLine();
                hr.bookARoom(customer, hr.getRooms(roomNr), dateCheckIn, dateCheckOut);
                System.out.println("Reservation confirmed: " + hr.getCustomersReservations(customer.getEmail()));
                printMainMenu();
                break;
            case "n":
                System.out.println("Please create an account");
                createAccount();
                break;
            default:
                System.out.println("Invalid input enter y or n!");
                findAndReserveARoom();
                break;
        }
    }

    public static boolean isValidDate(String date, DateTimeFormatter dateFormat) {
        try {
            LocalDate.parse(date, dateFormat);
        } catch (DateTimeParseException e) {
            System.out.println("Please enter a valid Date");
            return false;
        }
        return true;
    }

    public static Date getDate(String strDate, SimpleDateFormat simpleDateFormat) {
        try {
            return simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            System.out.println("Some problem occurred parsing the date");
        }
        return null;
    }

    public static void printMainMenu() {
        System.out.println("\nWelcome to the Hotel Reservation\n" +
                "---------------------------------\n" +
                "1. Find and reserve a room\n" +
                "2. See my reservations\n" +
                "3. Create an account\n" +
                "4. Admin menu\n" +
                "5. Exit\n" +
                "--------------------------------\n" +
                "Please select a number: \n");
    }
}
