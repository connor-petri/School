import java.util.ArrayList;

public class ReservationManager {
    public static class ReservationException extends Exception {
        ReservationException(String msg) {
            super(msg);
        }
    }

    private final ArrayList<Reservation> reservations = new ArrayList<>();

    public ReservationManager(String fileName) {
        // load file
    }

    public void add(User user, Reservation.Type type, String seatNum) throws IllegalArgumentException, ReservationException {
        Reservation r = new Reservation(user, type, seatNum);
        if (reservations.contains(r)) {
            throw new ReservationException("Seat already reserved");
        }
        // Write to file
        reservations.add(r);
    }

    private ArrayList<Reservation> getReservations() { return reservations; }

    private ArrayList<Reservation> getUserReservations(User user) {
        ArrayList<Reservation> list = new ArrayList<>();
        for (Reservation r : reservations) {
            if (user.equals(r.user)) {
                list.add(r);
            }
        }
        return list;
    }

    public void checkAvailability() {
        ArrayList<String> available = new ArrayList<>();

        for (int row = 1; row <= 50; row++) {
            for (char letter = 'A'; letter <= 'J'; letter++) {
                int r = row;
                char l = letter;
                if (reservations.stream().noneMatch(res -> res.getSeatNum() == r && res.getSeatLetter() == l)) {
                    available.add(row + String.valueOf(l));
                }
            }
        }

        for (String s : available) {
            System.out.println("First (price: $1000/ea");
            for ()
        }
    }

    public void makeReservation(User user) {

    }

    public void cancelReservation(User user) {

    }


}
