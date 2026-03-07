import java.io.*;
import java.util.*;

public class ReservationManager {
    private final TreeSet<Reservation> reservations = new TreeSet<>();
    private static final Scanner s = new Scanner(System.in);
    private final File file;

    public ReservationManager(String fileName) throws IOException {
        file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
            System.out.println("Reservation file " + fileName + " created.");
            return;
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> lines = br.readAllLines();
//        lines.removeFirst(); // Remove header row
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

    public void save(User user) throws IOException {
        if (!user.isAdmin()) { return; }

        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);

        for (User u : UserManager.getInstance().getUsers()) {
            bw.write(u.getUsername() + ",");
            for (Reservation r : getUserReservations(u)) {
                bw.write(r.getSeatNum() + ",");
            }
        }

        bw.close();
        fw.close();
    }

    private TreeSet<Reservation> getReservations() { return reservations; }

    private TreeSet<Reservation> getUserReservations(User user) {
        TreeSet<Reservation> list = new TreeSet<>();
        for (Reservation r : reservations) {
            if (user.equals(r.user)) {
                list.add(r);
            }
        }
        return list;
    }

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
