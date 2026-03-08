/**
 * Represents a single seat reservation for a user.
 *
 * <p>Precondition: The provided seat designator must be in the format {@code [1-50][A-J]}.
 * Postcondition: A reservation is created with a derived seat class and price based on row.
 *
 * @see ReservationManager
 * @see User
 */
public class Reservation implements Comparable<Reservation> {
    /**
     * Enumerates supported reservation seat classes and their base prices.
     *
     * @see Reservation#getPrice()
     */
    public enum Type {
        NONE(0),
        ECON(250),
        ECON_PLUS(500),
        FIRST_CLASS(1000);

        private final int price;

        /**
         * Creates a reservation type with a fixed price.
         *
         * <p>Precondition: {@code price >= 0}.
         * Postcondition: The enum constant stores the provided price.
         *
         * @input price non-negative integer seat price
         * @see Type#getPrice()
         */
        Type(int price) {
            this.price = price;
        }

        /**
         * Returns the configured price for this reservation type.
         *
         * <p>Precondition: None.
         * Postcondition: No state is modified.
         *
         * @input none
         * @return integer price for this seat class
         * @see Reservation#getPrice()
         */
        public int getPrice() {
            return price;
        }

        /**
         * Returns a display label for this reservation type.
         *
         * <p>Precondition: None.
         * Postcondition: No state is modified.
         *
         * @input none
         * @return human-readable seat class label
         * @see Enum#toString()
         */
        public String toString() {
            return switch (this) {
                case NONE -> "NONE";
                case ECON -> "Economy";
                case ECON_PLUS -> "Economy Plus";
                case FIRST_CLASS -> "First Class";
            };
        }
    }

    public User user;
    private final Type type;
    private final int seatNum;
    private final char seatLetter;

    /**
     * Validates whether a seat string matches the expected range and letter set.
     *
     * <p>Precondition: {@code seatNum != null} and has 2-3 characters.
     * Postcondition: No state is modified.
     *
     * @input seatNum candidate seat designator in format {@code [1-50][A-J]}
     * @return {@code true} if valid; otherwise {@code false}
     * @see Reservation#Reservation(User, String)
     */
    public static boolean checkSeatNum(String seatNum) {
        if (seatNum.length() < 2 || seatNum.length() > 3) {
            return false;
        }

        int num = Integer.parseInt(seatNum.substring(0, 1));
        char c = Character.toUpperCase(seatNum.charAt(1));

        if (Character.isDigit(seatNum.charAt(1))) {
            num = Integer.parseInt(seatNum.substring(0, 2));
            c = Character.toUpperCase(seatNum.charAt(2));
        }


        if (num < 1 || num > 50) {
            return false;
        }

        if (c < 'A' || c > 'J') {
            return false;
        }

        return true;
    }

    /**
     * Creates a reservation for the given user and seat.
     *
     * <p>Precondition: {@code user != null} and {@code seatNum} passes {@link #checkSeatNum(String)}.
     * Postcondition: A reservation is initialized with seat row, seat letter, and class type.
     *
     * @input user reservation owner
     * @input seatNum seat designator in format {@code [1-50][A-J]}
     * @see Reservation#checkSeatNum(String)
     * @see Reservation.Type
     */
    public Reservation(User user, String seatNum) throws IllegalArgumentException {
        this.user = user;

        if (!checkSeatNum(seatNum)) {
            throw new IllegalArgumentException("Seat Number must be in format [1-50][A-J]");
        }

        if (seatNum.length() < 3) {
            this.seatNum = Integer.parseInt(seatNum.substring(0, 1));
            this.seatLetter = Character.toUpperCase(seatNum.charAt(1));
        } else {
            this.seatNum = Integer.parseInt(seatNum.substring(0,2));
            this.seatLetter = Character.toUpperCase(seatNum.charAt(2));
        }

        if (this.seatNum < 5) {
            this.type = Type.FIRST_CLASS;
        } else if (this.seatNum < 16) {
            this.type = Type.ECON_PLUS;
        } else {
            this.type = Type.ECON;
        }
    }

    /**
     * Compares this reservation against a seat string.
     *
     * <p>Precondition: {@code seat} is in format {@code [1-50][A-J]}.
     * Postcondition: No state is modified.
     *
     * @input seat seat designator to compare
     * @return {@code true} if both row and seat letter match; otherwise {@code false}
     * @see Reservation#equals(int, char)
     */
    public boolean equals(String seat) {
        return Integer.parseInt(seat.substring(0, 1)) == seatNum && seat.charAt(1) == seatLetter;
    }

    /**
     * Compares this reservation against a row and seat letter.
     *
     * <p>Precondition: {@code row} is expected in {@code [1,50]} and {@code letter} in {@code [A,J]}.
     * Postcondition: No state is modified.
     *
     * @input row seat row number
     * @input letter seat letter
     * @return {@code true} if both row and seat letter match; otherwise {@code false}
     * @see Reservation#equals(String)
     */
    public boolean equals(int row, char letter) {
        return seatNum == row && seatLetter == letter;
    }

    /**
     * Orders reservations by row first, then seat letter.
     *
     * <p>Precondition: {@code r != null}.
     * Postcondition: No state is modified.
     *
     * @input r reservation to compare against
     * @return negative, zero, or positive based on seat ordering
     * @see Comparable#compareTo(Object)
     */
    public int compareTo(Reservation r) {
        return seatNum == r.seatNum ? Character.compare(seatLetter, r.seatLetter) : seatNum - r.seatNum;
    }

    /**
     * Returns the reservation price based on seat class.
     *
     * <p>Precondition: None.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return reservation price
     * @see Type#getPrice()
     */
    public int getPrice() { return type.getPrice(); }

    /**
     * Returns the seat row number.
     *
     * <p>Precondition: None.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return seat row value
     * @see Reservation#getSeatLetter()
     */
    public int getSeatNum() { return seatNum; }

    /**
     * Returns the seat letter.
     *
     * <p>Precondition: None.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return seat letter value
     * @see Reservation#getSeatNum()
     */
    public char getSeatLetter() { return seatLetter; }

    /**
     * Returns the reservation seat class.
     *
     * <p>Precondition: None.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return reservation type enum value
     * @see Reservation.Type
     */
    public Type getType() { return type; }

    /**
     * Builds a printable seat and price summary.
     *
     * <p>Precondition: None.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return formatted string containing seat and price
     * @see ReservationManager#viewReservations(User)
     */
    public String getSeatAndPrice() {
        return seatNum + String.valueOf(seatLetter) + " $" + type.getPrice() + " ";
    }

    /**
     * Prints the seat and username mapping for manifest output.
     *
     * <p>Precondition: {@code user != null}.
     * Postcondition: A single line is written to standard output.
     *
     * @input none
     * @return none
     * @see ReservationManager#showManifestList(User)
     */
    public void printSeatAndName() {
        System.out.println(seatNum + String.valueOf(seatLetter) + ": " + user.getUsername());
    }
}