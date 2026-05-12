import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for turn switching, win conditions, notifications, and undo limits.
 * 
 * @author Edward Cui
 */
public class ControllerTests {

    // Helper class to test GameListener functionality
    private static class MockGameListener implements MancalaController.GameListener {
        int stateChangedCount = 0;
        int gameOverCount = 0;
        BoardModel.Player winner = null;

        @Override
        public void onGameStateChanged() {
            stateChangedCount++;
        }

        @Override
        public void onGameOver(BoardModel.Player winner) {
            gameOverCount++;
            this.winner = winner;
        }
    }

    /**
     * Tests that the controller initializes correctly.
     */
    @Test
    public void testInitialization() {
        BoardModel model = new BoardModel(4);
        MancalaController controller = new MancalaController(model);

        // Verify controller initializes with Player ONE, game not over, and no winner
        assertEquals(BoardModel.Player.ONE, controller.getCurrentPlayer());
        assertFalse(controller.isGameOver());
        assertNull(controller.getWinner());
    }

    /**
     * Tests that listeners are properly notified of game start modifications.
     */
    @Test
    public void testGameListenerNotifications() {
        BoardModel model = new BoardModel(4);
        MancalaController controller = new MancalaController(model);
        MockGameListener listener = new MockGameListener();

        controller.addGameListener(listener);
        assertEquals(0, listener.stateChangedCount);

        // Listener should be notified when game starts
        controller.startGame(4);
        assertEquals(1, listener.stateChangedCount);
    }

    /**
     * Tests pit choices, empty pit constraints, and basic turn progression.
     */
    @Test
    public void testMoveResultsAndTurnSwitching() {
        BoardModel model = new BoardModel(4);
        MancalaController controller = new MancalaController(model);
        controller.startGame(4);

        // Valid move: Player ONE selects pit 2, turn passes to Player TWO
        MancalaController.MoveResult res1 = controller.pitClicked(BoardModel.Player.ONE, 2);
        assertEquals(MancalaController.MoveResult.SUCCESS, res1);
        assertEquals(BoardModel.Player.TWO, controller.getCurrentPlayer());

        // Invalid move: Player tries to move opponent's pit
        MancalaController.MoveResult res2 = controller.pitClicked(BoardModel.Player.ONE, 3);
        assertEquals(MancalaController.MoveResult.INVALID_PIT, res2);

        // Valid move: Player TWO moves pit 1, leaving it empty
        MancalaController.MoveResult res3 = controller.pitClicked(BoardModel.Player.TWO, 1);
        assertEquals(MancalaController.MoveResult.SUCCESS, res3);

        // Valid move: Player ONE makes a move to pass turn back to Player TWO
        controller.pitClicked(BoardModel.Player.ONE, 1);

        // Invalid move: Player TWO attempts to move an empty pit
        MancalaController.MoveResult res4 = controller.pitClicked(BoardModel.Player.TWO, 1);
        assertEquals(MancalaController.MoveResult.INVALID_PIT, res4);
    }

    /**
     * Tests extra turn logic.
     */
    @Test
    public void testExtraTurn() {
        BoardModel model = new BoardModel(4);
        MancalaController controller = new MancalaController(model);
        controller.startGame(4);

        // Extra turn is awarded when move ends in player's bank
        MancalaController.MoveResult res1 = controller.pitClicked(BoardModel.Player.ONE, 3);
        assertEquals(MancalaController.MoveResult.EXTRA_TURN, res1);
        assertEquals(BoardModel.Player.ONE, controller.getCurrentPlayer());
    }

    /**
     * Tests player-based undo constraints.
     */
    @Test
    public void testUndoManagerConstraints() {
        BoardModel model = new BoardModel(4);
        MancalaController controller = new MancalaController(model);
        controller.startGame(4);

        // Undo an extra turn move
        controller.pitClicked(BoardModel.Player.ONE, 3);
        assertTrue(controller.undo());

        // Consecutive undos are rejected
        assertFalse(controller.undo());
        assertEquals(MancalaController.MoveResult.UNDO_ALREADY_USED, controller.getLastMoveResult());

        // Pass to Player TWO and change turn
        controller.pitClicked(BoardModel.Player.ONE, 3);
        controller.pitClicked(BoardModel.Player.ONE, 4);

        // Undo is possible after turn changes if opponent hasn't moved
        assertEquals(BoardModel.Player.TWO, controller.getCurrentPlayer());
        assertTrue(controller.undo());
        assertEquals(BoardModel.Player.ONE, controller.getCurrentPlayer());

        // Player TWO cannot undo before making a move
        controller.pitClicked(BoardModel.Player.ONE, 4); // Player ONE moves again
        // Now it's Player TWO's turn
        // If Player TWO hasn't moved, they can click undo to undo Player ONE's move
        assertTrue(controller.undo());
        assertEquals(BoardModel.Player.ONE, controller.getCurrentPlayer());
    }

    /**
     * Tests undo limit of 3 per turn.
     */
    @Test
    public void testUndoThreeLimit() {
        BoardModel model = new BoardModel(4);
        MancalaController controller = new MancalaController(model);
        controller.startGame(4);

        // Turn 1
        controller.pitClicked(BoardModel.Player.ONE, 3);
        controller.undo();
        controller.pitClicked(BoardModel.Player.ONE, 3);
        controller.undo();
        controller.pitClicked(BoardModel.Player.ONE, 3);
        controller.undo();

        // 4th undo attempt is rejected
        controller.pitClicked(BoardModel.Player.ONE, 3);
        assertFalse(controller.undo());
        assertEquals(MancalaController.MoveResult.UNDO_LIMIT_REACHED, controller.getLastMoveResult());
        
        // Finish turn and start Player TWO's turn
        controller.pitClicked(BoardModel.Player.ONE, 2); // Switches to P2
        controller.pitClicked(BoardModel.Player.TWO, 1); // P2 moves, resets counter
        
        // P2 can undo 3 times
        assertTrue(controller.undo());
        controller.pitClicked(BoardModel.Player.TWO, 1);
        assertTrue(controller.undo());
        controller.pitClicked(BoardModel.Player.TWO, 1);
        assertTrue(controller.undo());
        controller.pitClicked(BoardModel.Player.TWO, 1);
        assertFalse(controller.undo());
    }

    /**
     * Tests game over and winner logic.
     */
    @Test
    public void testGameOverAndWinner() {
        BoardModel model = new BoardModel(4);
        MancalaController controller = new MancalaController(model);
        controller.startGame(4);
        MockGameListener listener = new MockGameListener();
        controller.addGameListener(listener);

        // Play valid moves until Game Over state is reached
        int maxTurns = 500; // Increased because undos might prolong game in tests if not careful
        while (!controller.isGameOver() && maxTurns-- > 0) {
            BoardModel.Player cp = controller.getCurrentPlayer();
            boolean moved = false;
            for (int i = 1; i <= 6; i++) {
                if (model.getPits(cp).get(i).numStones() > 0) {
                    controller.pitClicked(cp, i);
                    moved = true;
                    break;
                }
            }
            if (!moved) break;
        }

        // Game should be over and winner should be determined
        assertTrue(controller.isGameOver());
        assertTrue(listener.gameOverCount > 0);
        assertNotNull(controller.getWinner());
        assertEquals(controller.getWinner(), listener.winner);
    }
}
