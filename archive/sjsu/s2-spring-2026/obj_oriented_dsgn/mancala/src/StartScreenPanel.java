import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartScreenPanel extends JPanel {
    private BoardStyle selectedStyle = new ClassicBoardStyle();
    private int selectedStones = 4;

    private final JToggleButton classicButton = new JToggleButton("Classic");
    private final JToggleButton retroButton = new JToggleButton("Retro");

    private final JToggleButton threeStones = new JToggleButton("3 Stones");
    private final JToggleButton fourStones = new JToggleButton("4 Stones");

    private final JButton startButton = new JButton("Start Game");

    public StartScreenPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(60, 40, 60, 40));

        setBackground(new Color(151, 86, 44)); // sienna
        setupButtonGroups();

        add(buildTitle());
        add(Box.createVerticalStrut(50));
        add(buildStyleSelection());
        add(Box.createVerticalStrut(8));
        add(buildStoneSelection());
        add(Box.createVerticalStrut(40));
        add(buildStartButton());

    }

    private void setupButtonGroups() {
        ButtonGroup styleGroup = new ButtonGroup();
        styleGroup.add(classicButton);
        styleGroup.add(retroButton);

        ButtonGroup stoneGroup = new ButtonGroup();
        stoneGroup.add(threeStones);
        stoneGroup.add(fourStones);
    }

    private Component buildTitle() {
        JLabel title = new JLabel("Welcome to Mancala!", SwingConstants.CENTER);

        title.setFont(new Font("Serif", Font.BOLD, 48));

        title.setForeground(new Color(241, 221, 206)); // ivory satin
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        return title;
    }

    private Component buildStyleSelection() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setOpaque(false);

        JLabel label = new JLabel("Select Board Style:");
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Serif", Font.BOLD, 20));
        label.setForeground(new Color(241, 221, 206)); 

        styleToggle(classicButton);
        styleToggle(retroButton);

        classicButton.setSelected(true);

        classicButton.addActionListener(e -> selectedStyle = new ClassicBoardStyle());
        retroButton.addActionListener(e -> selectedStyle = new RetroBoardStyle());  

        panel.add(label);
        panel.add(classicButton);
        panel.add(retroButton);

        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, panel.getPreferredSize().height));

        return panel;
    }

    private Component buildStoneSelection() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 5));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setOpaque(false);

        JLabel label = new JLabel("Select Starting Stones:");
        label.setFont(new Font("Serif", Font.BOLD, 20));
        label.setForeground(new Color(241, 221, 206));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        styleToggle(threeStones);
        styleToggle(fourStones);

        fourStones.setSelected(true);

        threeStones.addActionListener(e -> selectedStones = 3);
        fourStones.addActionListener(e -> selectedStones = 4);  

        panel.add(label);
        panel.add(threeStones);
        panel.add(fourStones);

        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, panel.getPreferredSize().height));

        return panel;
    }

    private Component buildStartButton() {
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setFont(new Font("Serif", Font.BOLD, 36));
        startButton.setPreferredSize(new Dimension(320, 90));
        startButton.setMaximumSize(new Dimension(320, 90));

        startButton.setBackground(new Color(251, 196, 144)); // clear orange
        startButton.setForeground(new Color(151, 86, 44));   // sienna

        startButton.setFocusPainted(false);
        startButton.setOpaque(true);
        startButton.setContentAreaFilled(true);

        startButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 59, 32), 3),
            BorderFactory.createEmptyBorder(12, 36, 12, 36)
        ));

        startButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                startButton.setBackground(new Color(195, 130, 88)); // mocha icing

                startButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 6, 6, new Color(70, 40, 20)),
                    BorderFactory.createLineBorder(new Color(100, 59, 32), 3)
                ));
            }

            public void mouseExited(MouseEvent e) {
                startButton.setBackground(new Color(251, 196, 144)); // clear orange

                startButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(100, 59, 32), 3),
                    BorderFactory.createEmptyBorder(12, 36, 12, 36)
                ));
            }
        });

        return startButton;
    }

    private void styleToggle(JToggleButton button) {
        Color normal = new Color(213, 155, 109); // whiskey
        Color selected = new Color(215, 196, 144); // clear orange
        Color hover = new Color(231, 200, 165); // light peach
        Color text = new Color(100, 59, 32); // perma-brown
        Color shadow = new Color (70, 40, 20); // dark brown shadow

        button.setFont(new Font("Serif", Font.BOLD, 18));
        button.setForeground(text); 
        button.setBackground(normal); 

        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setPreferredSize(new Dimension(120, 36));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!button.isSelected()) {
                    button.setBackground(hover);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!button.isSelected()) {
                    button.setBackground(normal);
                }
            }
        });

        button.setBorder(BorderFactory.createLineBorder(new Color(100, 59, 32), 2));
        button.setAlignmentY(Component.CENTER_ALIGNMENT);

        button.addChangeListener(e -> {
            if (button.isSelected()) {
                button.setBackground(selected); 
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 5, 5, shadow),
                    BorderFactory.createLineBorder(text, 3)
                ));
            } else {
                button.setBackground(normal);
                button.setBorder(BorderFactory.createLineBorder(text, 2));
            }
        });
    }

    // getters
    public BoardStyle getSelectedStyle() {
        return selectedStyle;
    }

    public int getSelectedStones() {
        return selectedStones;
    }

    public JButton getStartButton() {
        return startButton;
    }
}
