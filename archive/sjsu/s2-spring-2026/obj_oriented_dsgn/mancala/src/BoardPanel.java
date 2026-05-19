import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class BoardPanel extends JPanel {
    private final MancalaController controller;
    private final BoardStyle style;
    private final Map<String, PitComponent> pits = new HashMap<>();
    private final BankComponent bank1Component = new BankComponent("Bank 1");
    private final BankComponent bank2Component = new BankComponent("Bank 2");

    public BoardPanel(MancalaController controller, BoardStyle style) {
        this.controller = controller;
        this.style = style;

        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        setBackground(style.getBoardBackground());

        add(buildCenterBoard(), BorderLayout.CENTER);
        applyStyle();
        refresh();
        
    }

    private JPanel buildCenterBoard() {
        JPanel wrapper = new JPanel(new BorderLayout(20, 20));
        wrapper.setBackground(style.getBoardBackground());

        bank1Component.setPreferredSize(new Dimension(160, 360));
        bank2Component.setPreferredSize(new Dimension(160, 360));

        JPanel middle = new JPanel(new GridLayout(2, 6, 16, 16));
        middle.setBackground(style.getBoardBackground());

        // top row: B6 B5 B4 B3 B2 B1
        for (int i = 6; i >= 1; i--) {
            middle.add(createPitComponent("B" + i, i, BoardModel.Player.TWO));
        }
        // bottom row: A1 A2 A3 A4 A5 A6
        for (int i = 1; i <= 6; i++) {
            middle.add(createPitComponent("A" + i, i, BoardModel.Player.ONE));
        }

        wrapper.add(bank2Component, BorderLayout.WEST);
        wrapper.add(middle, BorderLayout.CENTER);
        wrapper.add(bank1Component, BorderLayout.EAST);
        return wrapper;
    }

    private PitComponent createPitComponent(String pitName, int pitNum, BoardModel.Player owner) {
        PitComponent pit = new PitComponent(pitName);

        pit.getButton().addActionListener(e -> {
            controller.pitClicked(owner, pitNum);
        });

        pit.applyStyle(style);
        pits.put(pitName, pit);
        return pit;
    }

    private void applyStyle() {
        setBackground(style.getBoardBackground());

        bank1Component.applyStyle(style);
        bank2Component.applyStyle(style);
    }

    public void refresh() {
        BoardModel model = controller.getModel();

        for (int i = 1; i <= 6; i++) {
            pits.get("A" + i).setStoneCount(model.getPit("A" + i).numStones());
            pits.get("B" + i).setStoneCount(model.getPit("B" + i).numStones());
        }

        bank1Component.setCount(model.getBank(BoardModel.Player.ONE).numStones());
        bank2Component.setCount(model.getBank(BoardModel.Player.TWO).numStones());

        repaint();
        revalidate();
    }

    //getters for testing
    public JButton getPitButton(String pitName) {
        return pits.get(pitName).getButton();
    }

    public PitComponent getPitComponent(String pitName) {
        return pits.get(pitName);
    }

    public BankComponent getBank1Component() {
        return bank1Component;
    }

    public BankComponent getBank2Component() {
        return bank2Component;
    }
}
        
