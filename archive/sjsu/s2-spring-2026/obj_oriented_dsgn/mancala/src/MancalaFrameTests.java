import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MancalaFrameTests {
    @Test
    public void startButtonShowsBoard() {
        MancalaController controller = new MancalaController(new BoardModel(4));
        MancalaFrame frame = new MancalaFrame(controller);

        frame.getStartScreenPanel().getStartButton().doClick();

        assertTrue(frame.getBoardPanel() != null);

        frame.dispose();
    }


    @Test
    public void undoButtonCallsController() {
        BoardModel model = new BoardModel(4);
        MancalaController controller = new MancalaController(model);
        controller.startGame(4);
        MancalaFrame frame = new MancalaFrame(controller);

        frame.getStartScreenPanel().getStartButton().doClick(); // need to start game to show undo button

        // Make a move first so undo has something to revert
        controller.pitClicked(BoardModel.Player.ONE, 3); // extra turn (lands in bank)
        int bankBefore = model.getBank(BoardModel.Player.ONE).numStones();
        frame.getUndoButton().doClick();

        // After undo, bank should be back to 0
        assertEquals(0, model.getBank(BoardModel.Player.ONE).numStones());
        assertTrue(bankBefore > 0);

        frame.dispose();
    }

    @Test
    public void newGame3ButtonResetsBoard() {
        BoardModel model = new BoardModel(4);
        MancalaController controller = new MancalaController(model);
        controller.startGame(4);
        MancalaFrame frame = new MancalaFrame(controller);

        frame.getStartScreenPanel().getStartButton().doClick(); // need to start game to show new game buttons

        // Make a move to change state
        controller.pitClicked(BoardModel.Player.ONE, 3);
        frame.getNewGame3Button().doClick();

        // Board should be reset with 3 stones per pit
        assertEquals(3, model.getPit("A1").numStones());
        assertEquals(3, model.getPit("B1").numStones());
        assertEquals(0, model.getBank(BoardModel.Player.ONE).numStones());

        frame.dispose();
    }

    @Test
    public void newGame4ButtonResetsBoard() {
        BoardModel model = new BoardModel(4);
        MancalaController controller = new MancalaController(model);
        controller.startGame(4);
        MancalaFrame frame = new MancalaFrame(controller);

        frame.getStartScreenPanel().getStartButton().doClick(); // need to start game to show new game buttons

        // Make a move to change state
        controller.pitClicked(BoardModel.Player.ONE, 3);
        frame.getNewGame4Button().doClick();

        // Board should be reset with 4 stones per pit
        assertEquals(4, model.getPit("A1").numStones());
        assertEquals(4, model.getPit("B1").numStones());
        assertEquals(0, model.getBank(BoardModel.Player.ONE).numStones());

        frame.dispose();
    }
}
