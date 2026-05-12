import static org.junit.jupiter.api.Assertions.*;
import javax.swing.JButton;
import org.junit.jupiter.api.Test;

public class BoardPanelTests {
    @Test
    public void createsAllPitButtons() {
        BoardModel model = new BoardModel(4);
        MancalaController controller = new MancalaController(model);
        controller.startGame(4);
        BoardPanel panel = new BoardPanel(controller, new ClassicBoardStyle());

        for (int i = 1; i <= 6; i++) {
            assertNotNull(panel.getPitButton("A" + i));
            assertNotNull(panel.getPitButton("B" + i));
        }
    }

    @Test
    public void clickingOwnPit() {
        BoardModel model = new BoardModel(4);
        MancalaController controller = new MancalaController(model);
        controller.startGame(4);

        BoardPanel panel = new BoardPanel(controller, new ClassicBoardStyle());

        JButton a3 = panel.getPitButton("A3");
        int stonesBefore = model.getPit("A3").numStones();
        a3.doClick();
        assertEquals(0, model.getPit("A3").numStones());
        assertTrue(stonesBefore > 0);
    }

    @Test
    public void clickingOpponentPit() {
        BoardModel model = new BoardModel(4);
        MancalaController controller = new MancalaController(model);
        controller.startGame(4);

        BoardPanel panel = new BoardPanel(controller, new ClassicBoardStyle());

        int stonesBefore = model.getPit("B4").numStones();
        JButton b4 = panel.getPitButton("B4");
        b4.doClick();
        assertEquals(stonesBefore, model.getPit("B4").numStones());
    }
}
