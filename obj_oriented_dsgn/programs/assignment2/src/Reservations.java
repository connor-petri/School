import java.util.ArrayList;

public class Reservations {
    public static class ReservationException extends Exception {
        ReservationException(String msg) {
            super(msg);
        }
    }

    private final ArrayList<Reservation> reservations = new ArrayList<>();

    public Reservations(String fileName) {
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

    public ArrayList<Reservation> getReservations() { return reservations; }

    public ArrayList<Reservation> getUserReservations(User user) {
        ArrayList<Reservation> list = new ArrayList<>();
        for (Reservation r : reservations) {
            if (user.equals(r.user)) {
                list.add(r);
            }
        }
        return list;
    }
}
