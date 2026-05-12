import java.util.ArrayList;

/**
 * Controller orchestrating interaction between the Mancala Model and View.
 * Handles primary game loops, rules enforcement, validation, and turn
 * transitions.
 * 
 * @author Edward Cui
 */

public class MancalaController {
    /**
     * Enum enumerating the resulting action after a player selects a pit.
     */
    public enum MoveResult {
        SUCCESS, EXTRA_TURN, GAME_OVER, INVALID_PIT,
        UNDO_SUCCESS, UNDO_LIMIT_REACHED, UNDO_ALREADY_USED, UNDO_INVALID
    }

    /**
     * Unimplemented interface for view components observing game state changes.
     */
    public interface GameListener {
        // Triggered when the board updates or player turn changes.
        void onGameStateChanged();

        /**
         * Triggered when the game finishes.
         * 
         * @param winner The player who won, or null if a tie.
         */
        void onGameOver(BoardModel.Player winner);
    }

    // Private helper encapsulating undo limit tracking and turn-state locks.
    private class UndoManager {
        private BoardModel.Player playerWhoMoved;
        private int undosUsedThisTurn = 0;
        private boolean movePendingUndo = false;

        public UndoManager() {
            reset();
        }

        /**
         * Prepares undo tracker prior to a move execution.
         * Resets turn counter if the player changed.
         */
        public void saveState(BoardModel.Player currentPlayer) {
            if (currentPlayer != playerWhoMoved) {
                undosUsedThisTurn = 0;
            }
            playerWhoMoved = currentPlayer;
            movePendingUndo = true;
        }

        public boolean canUndo() {
            return playerWhoMoved != null && movePendingUndo && undosUsedThisTurn < 3;
        }

        public void performUndo() {
            undosUsedThisTurn++;
            movePendingUndo = false;
        }

        public void reset() {
            playerWhoMoved = null;
            undosUsedThisTurn = 0;
            movePendingUndo = false;
        }
    }

    private BoardModel model;
    private UndoManager undoManager;
    private BoardModel.Player currentPlayer;
    private ArrayList<GameListener> listeners;
    private boolean gameOver;
    private MoveResult lastMoveResult;

    public MancalaController(BoardModel model) {
        this.model = model;
        this.undoManager = new UndoManager();
        this.listeners = new ArrayList<>();
        this.currentPlayer = BoardModel.Player.ONE;
        this.gameOver = false;
    }

    /**
     * Attaches a listener to receive state updates.
     */
    public void addGameListener(GameListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Initializes the game.
     * 
     * @param stonesPerPit The initial stones filling each player pit.
     */
    public void startGame(int stonesPerPit) {
        model.setStonesPerPit(stonesPerPit);
        currentPlayer = BoardModel.Player.ONE;
        gameOver = false;
        undoManager.reset();
        lastMoveResult = null;
        notifyListeners();
    }

    /**
     * Logic executed when a pit is clicked by the user.
     * 
     * @param pitSide The player side that owns the pit
     * @param pitNum  The 1-based index of the pit (1-6)
     * @return Result of the move action
     */
    public MoveResult pitClicked(BoardModel.Player pitSide, int pitNum) {
        if (gameOver) {
            lastMoveResult = MoveResult.GAME_OVER;
            return MoveResult.GAME_OVER;
        }

        if (pitSide != currentPlayer) {
            lastMoveResult = MoveResult.INVALID_PIT;
            notifyListeners();
            return MoveResult.INVALID_PIT;
        }
        BoardModel.Pit pit = model.getPits(pitSide).get(pitNum);
        if (pit.numStones() == 0) {
            lastMoveResult = MoveResult.INVALID_PIT;
            notifyListeners();
            return MoveResult.INVALID_PIT;
        }

        undoManager.saveState(currentPlayer);

        boolean extraTurn = model.turn(currentPlayer, pitNum);

        if (checkGameOver()) {
            gameOver = true;
            lastMoveResult = MoveResult.GAME_OVER;
            notifyListeners();
            for (GameListener l : listeners) {
                l.onGameOver(getWinner());
            }
            return MoveResult.GAME_OVER;
        }

        if (!extraTurn) {
            currentPlayer = (currentPlayer == BoardModel.Player.ONE) ? BoardModel.Player.TWO : BoardModel.Player.ONE;
            lastMoveResult = MoveResult.SUCCESS;
            notifyListeners();
            return MoveResult.SUCCESS;
        } else {
            lastMoveResult = MoveResult.EXTRA_TURN;
            notifyListeners();
            return MoveResult.EXTRA_TURN;
        }
    }

    /**
     * Undoes the last move if permitted by rules.
     * 
     * @return true if undo was successful.
     */
    public boolean undo() {
        if (gameOver) {
            lastMoveResult = MoveResult.GAME_OVER;
            notifyListeners();
            return false;
        }

        if (!undoManager.canUndo()) {
            if (undoManager.playerWhoMoved == null) {
                lastMoveResult = MoveResult.UNDO_INVALID;
            } else if (undoManager.undosUsedThisTurn >= 3) {
                lastMoveResult = MoveResult.UNDO_LIMIT_REACHED;
            } else if (!undoManager.movePendingUndo) {
                lastMoveResult = MoveResult.UNDO_ALREADY_USED;
            } else {
                lastMoveResult = MoveResult.UNDO_INVALID;
            }
            notifyListeners();
            return false;
        }

        if (model.undo()) {
            currentPlayer = undoManager.playerWhoMoved;
            undoManager.performUndo();
            lastMoveResult = MoveResult.UNDO_SUCCESS;
            notifyListeners();
            return true;
        }
        
        lastMoveResult = MoveResult.UNDO_INVALID;
        notifyListeners();
        return false;
    }

    /**
     * Checks if the game has ended.
     */
    private boolean checkGameOver() {
        boolean p1Empty = true, p2Empty = true;
        for (int i = 1; i <= 6; i++) {
            if (model.getPits(BoardModel.Player.ONE).get(i).numStones() > 0) {
                p1Empty = false;
            }
            if (model.getPits(BoardModel.Player.TWO).get(i).numStones() > 0) {
                p2Empty = false;
            }
        }
        return p1Empty || p2Empty;
    }

    /**
     * Returns the winner of the game, or null if it's a tie.
     * 
     * @return The winning Player enum, or null.
     */
    public BoardModel.Player getWinner() {
        int p1Score = model.getBank(BoardModel.Player.ONE).numStones();
        int p2Score = model.getBank(BoardModel.Player.TWO).numStones();
        if (p1Score > p2Score)
            return BoardModel.Player.ONE;
        if (p2Score > p1Score)
            return BoardModel.Player.TWO;
        return null;
    }

    /**
     * Returns the current player.
     * 
     * @return The active Player enum.
     */
    public BoardModel.Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns true if the game is over.
     * 
     * @return true if game is in a game-over state.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Notifies all listeners of game state changes.
     */
    private void notifyListeners() {
        for (GameListener l : listeners) {
            l.onGameStateChanged();
        }
    }

    /**
     * Convenience wrapper for pitClicked using the current player's side.
     * 
     * @param pitNum The 1-based index of the pit (1-6)
     * @return Result of the move action
     */
    public MoveResult playPit(int pitNum) {
        return pitClicked(currentPlayer, pitNum);
    }

    /**
     * Returns the backing model for read-only view access.
     * 
     * @return The BoardModel instance.
     */
    public BoardModel getModel() {
        return model;
    }

    /**
     * Returns the result of the last move attempt.
     * 
     * @return The last MoveResult, or null if no move has been made.
     */
    public MoveResult getLastMoveResult() {
        return lastMoveResult;
    }
}
