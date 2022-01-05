import api.AdminResource;
import api.HotelResource;
import com.sun.tools.javac.Main;
import model.*;

import java.util.*;

public class AdminMenu {

    private static final HotelResource hr = HotelResource.getSINGLETON();
    private static final AdminResource ar = AdminResource.getSINGLETON();


    public static void adminMenu() {
        Scanner scanner = new Scanner(System.in);
        int selection = 0;
        boolean keepRunning = true;
        printAdminMenu();

        while (keepRunning) {
            try {
                selection = scanner.nextInt();
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid Input! Enter an Integer (number) between 1 and 5");
                adminMenu();
            }

            switch (selection) {
                case 1:
                    getAllCustomers();
                    break;
                case 2:
                    getAllRooms();
                    break;
                case 3:
                    displayAllReservations();
                    break;
                case 4:
                    addRoom();
                    break;
                case 5:
                    MainMenu.mainMenu();
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Number must be within 1-5!");
            }
        }
    }


    public static void getAllCustomers() {
        Collection<Customer> allCustomers = ar.getAllCustomers();
        if (allCustomers.isEmpty()) {
            System.out.println("No customers found!");
        } else {
            for (Customer customer : allCustomers) {
                System.out.println(customer.toString());
            }
        }
    }

    public static void displayAllReservations() {
        Collection<Reservation> allReservations = ar.displayAllReservations();
        for (Reservation reservation : allReservations) {
            System.out.println(reservation.toString());
        }
    }

    public static void addRoom() {
        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;
        String selection;
        String roomNumber;
        Double roomPrice;
        RoomType roomType;
        List<IRoom> addedRooms = new ArrayList<>();

        while (keepRunning) {
            System.out.println("Enter Room number: ");
            roomNumber = scanner.nextLine();
            System.out.println("Enter Room price: ");
            roomPrice = scanner.nextDouble();
            roomType = enumSelector();

            Room newRoom = new Room(roomNumber, roomPrice, roomType);
            addedRooms.add(newRoom);
            System.out.println("Do you want to add another room? (y/n)");
            String choice = scanner.nextLine();

            if (choice.equals("n")) {
                keepRunning = false;
                ar.addRoom(addedRooms);
            }
        }
    }

    public static RoomType enumSelector() {
        Scanner scanner = new Scanner(System.in);
        RoomType roomType = null;
        boolean validSelection = false;

        do {
            System.out.println("Enter room type (SINGLE/DOUBLE): ");
            String choiceRoomType = scanner.nextLine().toLowerCase();

            switch (choiceRoomType) {
                case "single":
                    roomType = RoomType.SINGLE;
                    validSelection = true;
                    break;
                case "double":
                    roomType = RoomType.DOUBLE;
                    validSelection = true;
                default:
                    System.out.println("Invalid input! only single or double rooms available.");
            }
        } while (!validSelection);
        return roomType;
    }

    public static void getAllRooms() {
        Collection<IRoom> allRooms = ar.getAllRooms();
        if (allRooms.isEmpty()) {
            System.out.println("There are no Rooms in this Hotel!");
        } else for (IRoom room : allRooms) {
            System.out.println(room.toString());
        }
    }

    public static void printAdminMenu() {
        System.out.println("\nYou're in the Admin mode!\n" +
                "---------------------------------\n" +
                "1. See all Customers\n" +
                "2. See all Rooms\n" +
                "3. See all Reservations\n" +
                "4. Add a Room\n" +
                "5. Back to Main Menu\n" +
                "--------------------------------\n" +
                "Please select a number: \n");
    }
}
