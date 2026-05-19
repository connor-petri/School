import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 

public class ControlPanel extends JPanel {
    private final JButton undoButton = new JButton("Undo");
    private final JButton newGame3Button = new JButton("New Game (3)");
    private final JButton newGame4Button = new JButton("New Game (4)");

    private BoardStyle currentStyle;

    public ControlPanel() {
        setLayout(new GridBagLayout());

        JPanel innerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 12));
        innerPanel.add(newGame3Button);
        innerPanel.add(newGame4Button);
        innerPanel.add(undoButton);

        add(innerPanel);

        addHoverEffect(newGame3Button);
        addHoverEffect(newGame4Button);
        addHoverEffect(undoButton);
    }

    public void applyStyle(BoardStyle style) {
        this.currentStyle = style;

        setOpaque(true);
        setBackground(style.getBoardBackground());
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(style.getLabelColor(), 4),
            BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));

        JPanel innerPanel = (JPanel) getComponent(0);
        innerPanel.setOpaque(true);
        innerPanel.setBackground(style.getMancalaColor());
        innerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(style.getLabelColor(), 3),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JButton[] buttons = {newGame3Button, newGame4Button, undoButton};
        
        for (JButton button : buttons) {
            button.setFont(style.getLabelFont().deriveFont(Font.BOLD, 18f));
            button.setBackground(style.getButtonColor());
            button.setForeground(style.getButtonTextColor());

            button.setOpaque(true);
            button.setContentAreaFilled(true);
            button.setFocusPainted(false);

            button.setPreferredSize(new Dimension(200, 50));
            button.setBorder(BorderFactory.createLineBorder(style.getButtonTextColor(), 2));
        }
    }

    private void addHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (currentStyle != null) {
                    button.setBackground(currentStyle.getButtonHoverColor());
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (currentStyle != null) {
                    button.setBackground(currentStyle.getButtonColor());
                }
            }
        });
    }

    public JButton getUndoButton() {
        return undoButton;
    }

    public JButton getNewGame3Button() {
        return newGame3Button;
    }

    public JButton getNewGame4Button() {
        return newGame4Button;
    }
    
}
