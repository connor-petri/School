import java.io.*;
import java.util.*;

/**
 * Manages in-memory reservation operations and file persistence.
 *
 * Precondition: The user subsystem should be loaded before constructing this manager.
 * Postcondition: Reservation data is available for querying, mutation, and persistence.
 *
 * @see Reservation
 * @see UserManager
 */
public class ReservationManager {
    private final TreeSet<Reservation> reservations = new TreeSet<>();
    private static final Scanner s = new Scanner(System.in);
    private final File file;

    /**
     * Creates a reservation manager and loads existing reservations from disk.
     *
     * Precondition: fileName != null and UserManager.getInstance() has been initialized.
     * Postcondition: Backing file exists and reservations contains parsed records.
     *
     * @input fileName base filename without extension
        * @see UserManager
     */
    public ReservationManager(String fileName) throws IOException {
        file = new File(fileName + ".txt");
        if (!file.exists()) {
            file.createNewFile();
            System.out.println("Reservation file " + fileName + " created.");
            return;
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> lines = br.readAllLines();

        String[] data;
        for (String line : lines) {
            data = line.split(",");
            if (data.length < 1) { continue; }
            User user = UserManager.getInstance().getUser(data[0]);

            for (String seatNum : Arrays.copyOfRange(data, 1, data.length)) {
                reservations.add(new Reservation(user, seatNum));
            }
        }
    }

    /**
     * Persists all users and their seat assignments to the reservation file.
     *
     * Precondition: Caller should be an admin user and file is writable.
     * Postcondition: Reservation file is overwritten with current in-memory reservation state.
     *
     * @input user caller attempting save operation
     * @return none
     * @see User
     * @see UserManager
     */
    public void save(User user) throws IOException {
        if (!user.isAdmin()) { return; }

        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);

        for (User u : UserManager.getInstance().getUsers()) {
            bw.write(u.getUsername() + ",");
            for (Reservation r : getUserReservations(u)) {
                bw.write(r.getSeatNum() + String.valueOf(r.getSeatLetter()) + ",");
            }
            bw.newLine();
        }

        bw.close();
        fw.close();
    }

    /**
     * Returns the internal reservation set.
     *
     * Precondition: None.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return all tracked reservations
     * @see ReservationManager
     */
    private TreeSet<Reservation> getReservations() { return reservations; }

    /**
     * Returns reservations that belong to a specific user.
     *
     * Precondition: user != null.
     * Postcondition: No state is modified.
     *
     * @input user target reservation owner
     * @return sorted set of reservations owned by the user
     * @see Reservation
     */
    private TreeSet<Reservation> getUserReservations(User user) {
        TreeSet<Reservation> list = new TreeSet<>();
        for (Reservation r : reservations) {
            if (user.equals(r.user)) {
                list.add(r);
            }
        }
        return list;
    }

    /**
     * Prints available seats for a single row.
     *
     * Precondition: r should be in [1,50].
     * Postcondition: Row availability is printed to standard output.
     *
     * @input r row number to inspect
     * @return none
     * @see ReservationManager
     */
    private void printFreeSeatsInRow(int r) {
        boolean match;
        System.out.print(r + ": ");

        for (char l = 'A'; l <= 'J'; l++) {
            final int row = r;
            final char letter = l;
            match = reservations.stream().anyMatch(res -> res.equals(row, letter));

            if (!match) {
                System.out.print(l + " ");
            }
        }
        System.out.println();
    }

    /**
     * Prints seat availability grouped by seat class.
     *
     * Precondition: None.
     * Postcondition: Availability report is printed to standard output.
     *
     * @input none
     * @return none
     * @see Reservation
     */
    public void checkAvailability() {
        int r = 1;

        System.out.println("Seat Availability:");
        System.out.println();

        System.out.println("First (price: $1000/ea)");
        for (; r < 5; r++) {
            printFreeSeatsInRow(r);
        }
        System.out.println();

        System.out.println("Economy Plus (price: $500/ea)");
        for (; r < 16; r++) {
            printFreeSeatsInRow(r);
        }
        System.out.println();

        System.out.println("Economy (price: $250/ea");
        for (; r <= 50; r++) {
            printFreeSeatsInRow(r);
        }
        System.out.println();
    }

    /**
     * Guides a user through creating a reservation.
     *
     * Precondition: user != null.
     * Postcondition: A reservation may be added to the set if confirmed and available.
     *
     * @input user user making the reservation
     * @return none
     * @see Reservation
     * @see ReservationManager
     */
    public void makeReservation(User user) {
        System.out.println("Choose a seat number [1-50][A-J]:");
        Reservation r = null;
        String seatNum = "";

        boolean success = false;
        while (!success) {
            try {
                System.out.print("Enter seat number [1-50][A-J] (q to quit): ");
                seatNum = s.nextLine();

                if  (seatNum.equalsIgnoreCase("q")) {
                    return;
                }

                r = new Reservation(user, seatNum);
                success = !reservations.contains(r);

                System.out.println(success ? "" : "Seat " + seatNum + " is already reserved. Please choose another seat");
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid seat number. Must be [1-50][A-J]");
            }
        }

        System.out.println("Seat: " + seatNum);
        System.out.println("Type: " + r.getType().toString());
        System.out.println("Price: " + r.getPrice());
        System.out.print("Confirm Reservation? (y/n):");
        System.out.println();

        if (s.nextLine().equalsIgnoreCase("y")) {
            reservations.add(r);
            System.out.println("Reservation added successfully.");
            System.out.println();
            return;
        }

        System.out.print("Reservation canceled. Would you like to make another? (y/n):");
        if (s.nextLine().equalsIgnoreCase("y")) {
            makeReservation(user);
        }
    }

    /**
     * Cancels one reservation selected by the user.
     *
     * Precondition: user != null and user has at least one reservation to cancel.
     * Postcondition: Matching reservation is removed from the set when found.
     *
     * @input user user requesting cancellation
     * @return none
     * @see ReservationManager
     */
    public void cancelReservation(User user) {
        viewReservations(user);
        TreeSet<Reservation> userRes = getUserReservations(user);
        String seatNum;
        boolean valid;
        do {
            System.out.print("Select a seat number [1-50][A-J]:");
            seatNum = s.nextLine();
            valid = Reservation.checkSeatNum(seatNum);
            System.out.print(valid ? "" : "Invalid seat number format.\n");
            final String n = seatNum;
            valid = userRes.stream().anyMatch(res -> res.equals(n));
            System.out.print(valid ? "" : "Reservation with seat number " + seatNum + " is not found.\n");
        } while (!valid);

        Reservation cancel = null;
        for (Reservation r : userRes) {
            if (r.equals(seatNum)) {
                cancel = r;
                break;
            }
        }

        reservations.remove(cancel);
        System.out.println("Reservation removed successfully.");
    }

    /**
     * Displays all reservations and total amount due for a user.
     *
     * Precondition: user != null.
     * Postcondition: Reservation summary is printed to standard output.
     *
     * @input user target user for reservation summary
     * @return none
     * @see Reservation
     */
    public void viewReservations(User user) {
        TreeSet<Reservation> userReservations = getUserReservations(user);
        System.out.print("Name: " + user.getUsername() + " Seats: ");
        StringBuilder output = new StringBuilder();

        for (Reservation r : userReservations) {
            output.append(r.getSeatAndPrice());
            output.append(", ");
        }
        output.deleteCharAt(output.length() - 2);

        System.out.print(output);
        System.out.println();

        int total = 0;
        for (Reservation r : userReservations) {
            total += r.getPrice();
        }
        System.out.println("Total due: " + total);
    }

    /**
     * Prints manifest grouped by seat class for admin users.
     *
     * Precondition: user != null; output occurs only when user is admin.
     * Postcondition: Manifest rows are printed to standard output for existing reservations.
     *
     * @input user caller requesting manifest
     * @return none
     * @see User
     * @see Reservation
     */
    public void showManifestList(User user) {
        if (!user.isAdmin()) {
            return;
        }

        System.out.println("First:");
        for (Reservation r : reservations) {
            if (r.getType() == Reservation.Type.FIRST_CLASS) {
                r.printSeatAndName();
            }
        }

        System.out.println("Economy Plus:");
        for (Reservation r : reservations) {
            if (r.getType() == Reservation.Type.ECON_PLUS) {
                r.printSeatAndName();
            }
        }

        System.out.println("Economy:");
        for (Reservation r : reservations) {
            if (r.getType() == Reservation.Type.ECON) {
                r.printSeatAndName();
            }
        }
    }
}
