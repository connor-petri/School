import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {
    private final JLabel statusLabel = new JLabel("Welcome to Mancala!", SwingConstants.CENTER);
    private final JLabel turnLabel = new JLabel();
    private final JLabel rightSpacer = new JLabel(" ") {
        @Override
        public Dimension getPreferredSize() {
            return turnLabel.getPreferredSize();
        }
    }; // empty label to balance layout

    public StatusPanel() {
        setLayout(new BorderLayout());

        turnLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        rightSpacer.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // use intrinsic pref size so text isn't cut off
        add(turnLabel, BorderLayout.WEST);
        add(statusLabel, BorderLayout.CENTER);
        add(rightSpacer, BorderLayout.EAST);
    }

    public void applyStyle(BoardStyle style) {
        setBackground(style.getFrameBackground());

        statusLabel.setForeground(style.getStatusLabelColor());
        statusLabel.setFont(style.getStatusLabelFont());
        turnLabel.setForeground(style.getTurnLabelColor());
        turnLabel.setFont(style.getTurnLabelFont());
        rightSpacer.setForeground(style.getStatusLabelColor());
        rightSpacer.setFont(style.getStatusLabelFont());
    }

    public void setTurnText(String text) {
        turnLabel.setText(text);
    }

    public void setStatusText(String text) {
        statusLabel.setText(text);
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public JLabel getTurnLabel() {
        return turnLabel;
    }

}
