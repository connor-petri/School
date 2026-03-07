import java.io.IOException;
import java.util.*;

public class ReservationSystem {
    private static ReservationManager rm;
    private static final Scanner s = new Scanner(System.in);
    private static User user;

    private static boolean validateInput(char c, char[] valid) {
        for (char v : valid) {
            if (c == v) { return true; }
        }
        return false;
    }

    private static char getValidInput(String prompt, char[] valid) {
        System.out.print(prompt + " ");
        char input = s.nextLine().toLowerCase().charAt(0);
        while (!validateInput(input, valid)) {
            System.out.println("Invalid input");
            System.out.print(prompt + " ");
            input = s.nextLine().charAt(0);
        }
        return input;
    }

    private static void adminMenu() throws IOException {
        for (;;) {
            char input = getValidInput("Show [M]anifest List or E[X]it?", new char[]{'m', 'x'});

            switch (input) {
                case 'm':
                    rm.showManifestList(user);
                    break;
                case 'x':
                    UserManager.getInstance().save();
                    rm.save(user);
                    System.exit(0);
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }

    private static void mainMenu() {
        for (;;) {
            char input = getValidInput("Check [A]vailability Make [R]eservation [C]ancel Reservation [V]iew Reservations [D]one",
                                        new  char[]{'a', 'r', 'c', 'v', 'd'});

            switch (input) {
                case 'a':
                    rm.checkAvailability();
                    break;
                case 'r':
                    rm.makeReservation(user);
                    break;
                case 'c':
                    rm.cancelReservation(user);
                    break;
                case 'v':
                    rm.viewReservations(user);
                    break;
                case 'd':
                    return;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }

    private static void register() throws IOException {
        System.out.println("Register:\n");
        System.out.print("Username: ");
        String username = s.nextLine();
        System.out.print("Password: ");
        String password = s.nextLine();

        UserManager.getInstance().addUser(username, password);
        System.out.print("Registration Successful");
        login();
    }

    private static void login() throws IOException {
        System.out.println("Login:\n");
        System.out.print("Username: ");
        String username = s.nextLine();
        System.out.print("Password: ");
        String password = s.nextLine();

        user = UserManager.getInstance().getUser(username, password);

        if  (user == null) {
            System.out.println("Invalid username or password");
        }

        if (user.isAdmin()) {
            adminMenu();
        } else {
            mainMenu();
        }
    }

    public static void main(String[] args) throws IOException {
        UserManager.load(args[1]);
        rm = new ReservationManager(args[0]);

        for (;;) {
            char input = getValidInput("[L]og in or [R]egister?", new char[]{'l', 'r'});
            switch (input) {
                case 'l':
                    login();
                    break;
                case 'r':
                    register();
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }
}
