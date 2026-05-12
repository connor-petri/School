import java.util.*;

/**
 * Model for a Mancala board including the methods needed for controlling a game.
 * Note that this does not keep track of turns.
 * @see Pit
 * @author Connor Petri
 * @version 0.2
 */
public class BoardModel {
    /**
     * This enum is used to represent the concept of player 1 and player 2. Mostly used as function
     * input parameters
     * @see BoardModel
     */
    enum Player {
        ONE, TWO
    }

    /**
     * An object representing a single pit in a game of mancala.
     * @see BoardModel
     * @author Connor Petri
     */
    public class Pit {
        private String name;
        private int numStones;
        private Pit next, adj;
        private Player owner;

        private Pit(Player owner, String name, int numStones) {
            this.owner = owner;
            this.name = name;
            this.numStones = numStones;
        }

        /**
         * Retrieves the name of the Pit (i.e. A4)
         * @return String representation of pit name
         */
        public String name() {
            return name;
        }

        /**
         * Indicates which player the pit belongs to
         * @return Player enum instance representing which player owns this pit
         */
        public Player getOwner() {
            return owner;
        }

        /**
         * Retrieves the number of stones in the current pit
         * @return number of stones in pit
         */
        public int numStones() {
            return numStones;
        }

        /**
         * Retrieves the next pit in the order, including banks
         * @return Reference to next pit
         */
        public Pit next() {
            return next;
        }
    }

    private ArrayList<Pit> player1Pits;
    private ArrayList<Pit> player2Pits;
    private int stonesPerPit;

    // Previous turn
    private int[] p1StonesPrevious;
    private int[] p2StonesPrevious;

    /**
     * Construct a board model with either 3 or 4 stones per pit. It connects each pit
     * to the next one on the board.
     * @param stonesPerPit
     * @throws IllegalArgumentException
     */
    public BoardModel(int stonesPerPit) throws IllegalArgumentException {
        this.stonesPerPit = stonesPerPit;
        if  (stonesPerPit < 3 || stonesPerPit > 4) {
            throw new IllegalArgumentException("Stones-per-pit must be either 3 or 4");
        }

        player1Pits = new ArrayList<>();
        player2Pits = new ArrayList<>();

        player1Pits.add(new Pit(Player.ONE, "Bank1", 0));
        player2Pits.add(new Pit(Player.TWO, "Bank2", 0));
        Pit current = getBank(Player.TWO);
        for (int i = 0; i < 6; i++) {
            current.next = new Pit(Player.ONE, "A" + (i + 1), stonesPerPit);
            player1Pits.add(current.next);
            current = current.next;
        }
        current.next = getBank(Player.ONE);
        current = current.next;
        for (int i = 0; i < 6; i++) {
            current.next = new Pit(Player.TWO, "B" + (i + 1), stonesPerPit);
            player2Pits.add(current.next);
            current = current.next;
        }
        current.next = getBank(Player.TWO);

        // Assign adjacency
        for (int i = 1; i < 7; i++) {
            player1Pits.get(i).adj = player2Pits.get(7 - i);
            player2Pits.get(7-i).adj = player1Pits.get(i);
        }
    }

    /**
     * Returns a reference to the bank of the specified player
     * @param player
     * @return reference to player's bank
     */
    public Pit getBank(Player player) {
        return switch (player) {
            case Player.ONE -> player1Pits.getFirst();
            case Player.TWO -> player2Pits.getFirst();
        };
    }

    public Pit getPit(String name) throws IllegalArgumentException {
        for (Pit p : player1Pits) {
            if (p.name().equals(name)) {
                return p;
            }
        }
        for (Pit p : player2Pits) {
            if (p.name().equals(name)) {
                return p;
            }
        }
        throw new IllegalArgumentException("No such pit " + name);
    }

    public ArrayList<Pit> getPits(Player player) {
        return switch (player) {
            case Player.ONE -> player1Pits;
            case Player.TWO -> player2Pits;
        };
    }

    /**
     * Resets the board to a new game state
     */
    public void reset() {
        for (Pit pit : player1Pits) {
            pit.numStones = stonesPerPit;
        }
        for (Pit pit : player2Pits) {
            pit.numStones = stonesPerPit;
        }
        getBank(Player.ONE).numStones = 0;
        getBank(Player.TWO).numStones = 0;
    }

    /**
     * Resets the board with the given number of stones per pit
     * @param stonesPerPit
     */
    public void setStonesPerPit(int stonesPerPit) throws IllegalArgumentException {
        if (stonesPerPit < 3 || stonesPerPit > 4) {
            throw new IllegalArgumentException("Stones-per-pit must be either 3 or 4");
        }
        this.stonesPerPit = stonesPerPit;
        reset();
    }

    /**
     * Undo the previous turn. Cannot be used twice in a row
     * @return true/false depending on if undo was successful/allowed
     */
    public boolean undo() {
        if (p1StonesPrevious == null || p2StonesPrevious == null) {
            return false;
        }

        for (int i = 0; i < 7; i++) {
            player1Pits.get(i).numStones = p1StonesPrevious[i];
            player2Pits.get(i).numStones = p2StonesPrevious[i];
        }
        p1StonesPrevious = null;
        p2StonesPrevious = null;
        return true;
    }

    /**
     * Executes a turn for the specified player and pit number (1-6)
     * @param player
     * @param pitNum
     * @throws IllegalArgumentException if pitNum is invalid or the selected pit is empty
     * @return boolean representing if an extra turn has occurred
     */
    public boolean turn(Player player, int pitNum) {
        if (pitNum < 1 || pitNum > 6) {
            throw new IllegalArgumentException("Pit number must be between 1 and 6");
        }

        Pit current = switch (player) {
            case Player.ONE -> player1Pits.get(pitNum);
            case Player.TWO -> player2Pits.get(pitNum);
        };

        if (current.numStones() == 0) {
            throw new IllegalArgumentException("Pit " + pitNum + " is empty");
        }

        // Save previous board state for undo
        p1StonesPrevious = new int[7];
        p2StonesPrevious = new int[7];
        for (int i = 0; i < 7; i++) {
            p1StonesPrevious[i] = player1Pits.get(i).numStones;
            p2StonesPrevious[i] = player2Pits.get(i).numStones;
        }

        // Turn mechanics
        int hand = current.numStones;
        current.numStones = 0;
        while (hand != 0) {
            current = current.next;
            boolean skip = (player == Player.ONE && current.equals(getBank(Player.TWO))
                            || (player == Player.TWO && current.equals(getBank(Player.ONE))));
            if (!skip) {
                current.numStones++;
                hand--;
            }
        }

        // Capture mechanic
        System.out.println(current.name);
        if (current.adj != null && current.numStones == 1 && current.adj.numStones > 0 && current.owner == player) {
            getBank(player).numStones += current.adj.numStones + 1;
            current.adj.numStones = 0;
            current.numStones = 0;
        }

        // Check for empty pits on either side
        boolean p1empty = true, p2empty = true;
        for (int i = 1; i < player1Pits.size(); i++) {
            if (player1Pits.get(i).numStones != 0) {
                p1empty = false;
            }
            if (player2Pits.get(i).numStones != 0) {
                p2empty = false;
            }
        }

        if (p1empty) {
            for (int i = 1; i < player2Pits.size(); i++) {
                getBank(Player.TWO).numStones += player2Pits.get(i).numStones;
                player2Pits.get(i).numStones = 0;
            }
        }

        if (p2empty) {
            for (int i = 1; i < player1Pits.size(); i++) {
                getBank(Player.ONE).numStones += player1Pits.get(i).numStones;
                player1Pits.get(i).numStones = 0;
            }
        }

        // Return true if there is a free turn
        return current.equals(getBank(player));
    }

    public void printList() {
        for (Pit pit : player1Pits) {
            System.out.println(pit.name + " next: " + pit.next.name() + " adj: " + (pit.adj == null ? "" : pit.adj.name()));
        }
        for (Pit pit : player2Pits) {
            System.out.println(pit.name + " next: " + pit.next.name() + " adj: " + (pit.adj == null ? "" : pit.adj.name()));
        }
    }

    /**
     * Used for debugging only. Prints a representation of the current board state to the console
     */
    public void printBoard() {
        System.out.printf("""
                Bank2: %d
                %d A1 B6 %d
                %d A2 B5 %d
                %d A3 B4 %d
                %d A4 B3 %d
                %d A5 B2 %d
                %d A6 B1 %d
                Bank1: %d
                \n \n""", player2Pits.get(0).numStones, player1Pits.get(1).numStones, player2Pits.get(6).numStones,
                player1Pits.get(2).numStones, player2Pits.get(5).numStones, player1Pits.get(3).numStones,
                player2Pits.get(4).numStones, player1Pits.get(4).numStones, player2Pits.get(3).numStones,
                player1Pits.get(5).numStones, player2Pits.get(2).numStones, player1Pits.get(6).numStones,
                player2Pits.get(1).numStones, player1Pits.get(0).numStones);
    }
}