public class Reservation implements Comparable<Reservation> {
    public enum Type {
        NONE(0),
        ECON(250),
        ECON_PLUS(500),
        FIRST_CLASS(1000);

        private final int price;

        Type(int price) {
            this.price = price;
        }

        public int getPrice() {
            return price;
        }

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
    private Type type = Type.NONE;
    private final int seatNum;
    private final char seatLetter;

    public static boolean checkSeatNum(String seatNum) {
        if (seatNum.length() != 2) {
            return false;
        }

        if (!Character.isDigit(seatNum.charAt(0)) || !Character.isAlphabetic(seatNum.charAt(1))) {
            return false;
        }

        int num = Integer.parseInt(seatNum.substring(0, 1));
        char c = Character.toUpperCase(seatNum.charAt(1));

        if (num < 1 || num > 50) {
            return false;
        }

        if (c < 'A' || c > 'J') {
            return false;
        }

        return true;
    }

    public Reservation(User user, String seatNum) throws IllegalArgumentException {
        this.user = user;

        if (!checkSeatNum(seatNum)) {
            throw new IllegalArgumentException("Seat Number must be in format [1-50][A-J]");
        }

        this.seatNum = Integer.parseInt(seatNum.substring(0, 1));
        this.seatLetter = Character.toUpperCase(seatNum.charAt(1));

        if (this.seatNum < 5) {
            this.type = Type.FIRST_CLASS;
        } else if (this.seatNum < 16) {
            this.type = Type.ECON_PLUS;
        } else {
            this.type = Type.ECON;
        }
    }

    public boolean equals(String seat) {
        return Integer.parseInt(seat.substring(0, 1)) == seatNum && seat.charAt(1) == seatLetter;
    }

    public boolean equals(int row, char letter) {
        return seatNum == row && seatLetter == letter;
    }

    public int compareTo(Reservation r) {
        return seatNum == r.seatNum ? Character.compare(seatLetter, r.seatLetter) : seatNum - r.seatNum;
    }

    public User getUser() { return user; }
    public int getPrice() { return type.getPrice(); }
    public int getSeatNum() { return seatNum; }
    public char getSeatLetter() { return seatLetter; }
    public Type getType() { return type; }

    public String getSeatAndPrice() {
        return seatNum + String.valueOf(seatLetter) + " $" + type.getPrice() + " ";
    }

    public void printSeatAndName() {
        System.out.println(seatNum + String.valueOf(seatLetter) + ": " + user.getName());
    }
}