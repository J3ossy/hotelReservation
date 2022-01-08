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
                System.out.println("Invalid Input! Enter an Integer (number) between 1 and 6");
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
                    addTestData();
                    break;
                case 6:
                    MainMenu.mainMenu();
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Number must be within 1-6!");
            }
        }
    }

    private static void addTestData() {

        hr.createACustomer("test@email.com", "Bob", "Tester");
        hr.createACustomer("test@domain.com", "John", "Doe");
        hr.createACustomer("sandro@email.com", "Max", "Mustermann");

        List<IRoom> testRooms = new ArrayList<>();
        Room room = new Room("10", 10.0, RoomType.SINGLE);
        Room room1 = new Room("20", 20.0, RoomType.DOUBLE);
        Room room2 = new Room("21", 25.0, RoomType.DOUBLE);
        testRooms.add(room);
        testRooms.add(room1);
        testRooms.add(room2);
        ar.addRoom(testRooms);

        Date cIDate = new Date();
        Date cODate = new Date();
        hr.bookARoom(ar.getCustomer("test@domain.com"), hr.getRooms("10"), cIDate, cODate);
        hr.bookARoom(ar.getCustomer("sandro@email.com"), hr.getRooms("20"), cIDate, cODate);

        System.out.println("Test data has been added , go an check it out!");
        printAdminMenu();
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
        printAdminMenu();
    }

    public static void displayAllReservations() {
        Collection<Reservation> allReservations = ar.displayAllReservations();
        for (Reservation reservation : allReservations) {
            System.out.println(reservation.toString());
        }
        printAdminMenu();
    }

    public static void addRoom()  {
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
            scanner.nextLine(); // throw  '\n' away not consumed by nextDouble()
            selection = scanner.nextLine().toLowerCase();

            if (selection.equals("n")) {
                keepRunning = false;
                ar.addRoom(addedRooms);
                System.out.println("Rooms are added!");
                printAdminMenu();
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
                    break;
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
        printAdminMenu();
    }

    public static void printAdminMenu() {
        System.out.println("\nYou're in the Admin mode!\n" +
                "---------------------------------\n" +
                "1. See all Customers\n" +
                "2. See all Rooms\n" +
                "3. See all Reservations\n" +
                "4. Add a Room\n" +
                "5. Add Test Data\n" +
                "6. Back to Main Menu\n" +
                "--------------------------------\n" +
                "Please select a number: \n");
    }
}
