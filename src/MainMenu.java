import api.HotelResource;

import java.util.Scanner;

public class MainMenu {

    private static final HotelResource hr = HotelResource.getSINGLETON();

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
                    System.out.println("Find a Room Service");
                    break;
                case 2:
                    System.out.println("Customer");
                    break;
                case 3:
                    createAccount();
                    break;
                case 4:
                    System.out.println("Case 4");
                    break;
                case 5:
                    System.out.println("Exit");
                    keepRunning = false;
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
