public class Reservation {
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
    }

    public User user;
    private Type type = Type.NONE;
    private final int seatNum;
    private final char seatLetter;

    private boolean checkSeatNum(String seatNum) {
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

    public Reservation(User user, Type type, String seatNum) throws IllegalArgumentException {
        this.user = user;
        this.type = type;

        if (!checkSeatNum(seatNum)) {
            throw new IllegalArgumentException("Seat Number must be in format [1-50][A-J]");
        }

        this.seatNum = Integer.parseInt(seatNum.substring(0, 1));
        this.seatLetter = Character.toUpperCase(seatNum.charAt(1));
    }

    public boolean equals(Reservation other) {
        return seatNum == other.seatNum && seatLetter == other.seatLetter;
    }

    public int getSeatNum() { return seatNum; }
    public char getSeatLetter() { return seatLetter; }

    public void printSeatAndPrice() {
        System.out.print(Integer.toString(seatNum) + String.valueOf(seatLetter) + " $" + type.getPrice() + " ");
    }

    public void printSeatAndName() {
        System.out.println(Integer.toString(seatNum) + String.valueOf(seatLetter) + ": " + user.getName());
    }
}