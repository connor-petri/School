import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PitComponent extends JPanel {
    private final String pitName;
    private final JButton button;
    private final JLabel stoneLabel;
    private BoardStyle currentStyle;

    public PitComponent(String pitName) {
        this.pitName = pitName;

        setLayout(new BorderLayout());
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setOpaque(false);

        button = new JButton(pitName);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);

        stoneLabel = new JLabel("0", SwingConstants.CENTER);
        stoneLabel.setBorder(BorderFactory.createEmptyBorder(2, 0, 4, 0));

        add(button, BorderLayout.CENTER);
        add(stoneLabel, BorderLayout.SOUTH);

        addHoverEffect();
    }

    public void applyStyle(BoardStyle style) {
        this.currentStyle = style;

        button.setBackground(style.getPitColor());
        button.setForeground(style.getLabelColor());
        button.setFont(style.getLabelFont());
        button.setBorder(BorderFactory.createLineBorder(style.getLabelColor(), 2));

        stoneLabel.setForeground(style.getStoneColor());
        stoneLabel.setFont(style.getLabelFont().deriveFont(Font.BOLD, 18f));
    }
    private void addHoverEffect() {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(currentStyle != null) {
                    button.setBackground(currentStyle.getPitColor().darker());
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(currentStyle != null) {
                    button.setBackground(currentStyle.getPitColor());
                }
            }
        });
    }

    public JButton getButton() {
        return button;
    }

    public String getPitName() {
        return pitName;
    }

    public String getStoneText() {
        return stoneLabel.getText();
    }

    public void setStoneCount(int stones) {
        stoneLabel.setText(String.valueOf(stones));
    }
}
