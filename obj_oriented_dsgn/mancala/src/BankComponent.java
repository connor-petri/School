import java.awt.*;
import javax.swing.*;

public class BankComponent extends JPanel {
    private final JLabel nameLabel;
    private final JLabel countLabel;

    public BankComponent(String name) {
        setLayout(new GridBagLayout());

        nameLabel = new JLabel(name, SwingConstants.CENTER);
        countLabel = new JLabel("0", SwingConstants.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(8, 8, 8, 8);

        gbc.gridy = 0;
        add(nameLabel, gbc);

        gbc.gridy = 1;
        add(countLabel, gbc);
    }

    public void applyStyle(BoardStyle style) {
        setBackground(style.getMancalaColor());
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(style.getLabelColor(), 3));

        nameLabel.setFont(style.getLabelFont().deriveFont(Font.BOLD, 18f));
        nameLabel.setForeground(style.getLabelColor());

        countLabel.setFont(style.getLabelFont().deriveFont(Font.BOLD, 28f));
        countLabel.setForeground(style.getStoneColor());
    }

    public void setCount(int count) {
        countLabel.setText(String.valueOf(count));
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public JLabel getCountLabel() {
        return countLabel;
    }
}
