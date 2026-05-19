import javax.swing.SwingUtilities;

public class MancalaApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BoardModel model = new BoardModel(4); // Default to 4 stones per pit
            MancalaController controller = new MancalaController(model);
            new MancalaFrame(controller);
        });
    }
}
