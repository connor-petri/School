import java.io.IOException;
import java.util.*;

/**
 * Console entry point for login, registration, and reservation workflows.
 *
 * <p>Precondition: Expected file arguments are provided to {@link #main(String[])}.
 * Postcondition: The process loops until explicitly terminated.
 *
 * @see ReservationManager
 * @see UserManager
 */
public class ReservationSystem {
    private static ReservationManager rm;
    private static final Scanner s = new Scanner(System.in);
    private static User user;

    /**
     * Validates whether a single-character input is in an allowed set.
     *
     * <p>Precondition: {@code valid != null}.
     * Postcondition: No state is modified.
     *
     * @input c input character to validate
     * @input valid list of accepted characters
     * @return {@code true} if input is allowed; otherwise {@code false}
     * @see #getValidInput(String, char[])
     */
    private static boolean validateInput(char c, char[] valid) {
        for (char v : valid) {
            if (c == v) { return true; }
        }
        return false;
    }

    /**
     * Repeatedly prompts until a valid menu character is entered.
     *
     * <p>Precondition: {@code prompt != null} and {@code valid} contains one or more choices.
     * Postcondition: A valid choice character is returned.
     *
     * @input prompt message displayed to the user
     * @input valid accepted character options
     * @return validated character selection
     * @see #validateInput(char, char[])
     */
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

    /**
     * Runs the admin-only menu loop.
     *
     * <p>Precondition: {@code user != null} and user has admin privileges.
     * Postcondition: May persist users/reservations and terminate process on exit choice.
     *
     * @input none
     * @return none
     * @see ReservationManager#showManifestList(User)
     */
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

    /**
     * Runs the standard user reservation menu loop.
     *
     * <p>Precondition: {@code user != null} and {@code rm != null}.
     * Postcondition: User actions are executed until done is selected.
     *
     * @input none
     * @return none
     * @see ReservationManager#makeReservation(User)
     */
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

    /**
     * Registers a new non-admin user through console input.
     *
     * <p>Precondition: {@link UserManager} has been loaded.
     * Postcondition: New user is added and login flow is invoked.
     *
     * @input none
     * @return none
     * @see UserManager#addUser(String, String)
     */
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

    /**
     * Authenticates a user and routes to admin or standard menu.
     *
     * <p>Precondition: {@link UserManager} has been loaded.
     * Postcondition: Global {@code user} is assigned when credentials are valid.
     *
     * @input none
     * @return none
     * @see UserManager#getUser(String, String)
     */
    private static void login() throws IOException {
        System.out.println("Login:\n");
        System.out.print("Username: ");
        String username = s.nextLine();
        System.out.print("Password: ");
        String password = s.nextLine();

        user = UserManager.getInstance().getUser(username, password);

        if  (user == null) {
            System.out.println("Invalid username or password");
            return;
        }

        if (user.isAdmin()) {
            adminMenu();
        } else {
            mainMenu();
        }
    }

    /**
     * Application entry point.
     *
     * <p>Precondition: {@code args[0]} is reservation filename and {@code args[1]} is user filename.
     * Postcondition: User and reservation managers are initialized, then menu loop begins.
     *
     * @input args command-line arguments for reservation and user files
     * @return none
     * @see UserManager#load(String)
     * @see ReservationManager#ReservationManager(String)
     */
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
