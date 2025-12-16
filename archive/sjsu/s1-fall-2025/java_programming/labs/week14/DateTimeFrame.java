package week14;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.time.*;
import java.time.format.*;

public class DateTimeFrame extends JFrame implements ActionListener {
    private static final int windowWidthPx = 300;
    private static final int windowHeightPx = 300;
    private static final int paddingPx = 10;
    private static final Dimension dateTextDimension = new Dimension(250, 30);
    private static final Dimension timeTextDimension = new Dimension(250, 30);

    private JLabel dateLabel = new JLabel("Date:");
    private JLabel timeLabel = new JLabel("Time:");
    private JTextField dateText = new JTextField();
    private JTextField timeText = new JTextField();
    private JButton getDateTimeButton = new JButton("Get Date and Time");

    public DateTimeFrame() {
        // Set up Frame ==============================================
        
        setLayout(new GridBagLayout());
        setSize(windowWidthPx, windowHeightPx);
        setTitle("The World's Worst Clock App");

        // Set up components ==========================================
        
        dateText.setPreferredSize(dateTextDimension);
        dateText.setEditable(false);

        timeText.setPreferredSize(timeTextDimension);
        timeText.setEditable(false);

        getDateTimeButton.addActionListener(this);

        // Layout ====================================================
        GridBagConstraints layout = new GridBagConstraints();

        // Date Label
        layout.gridx = 0;
        layout.gridy = 0;
        layout.insets = new Insets(paddingPx, paddingPx, paddingPx, paddingPx);
        add(dateLabel, layout);

        // Date TextField
        layout = new GridBagConstraints();
        layout.gridx = 0;
        layout.gridy = 1;
        layout.insets = new Insets(paddingPx, paddingPx, paddingPx, paddingPx);
        add(dateText, layout);

        // Time Label
        layout = new GridBagConstraints();
        layout.gridx = 0;
        layout.gridy = 2;
        layout.insets = new Insets(paddingPx, paddingPx, paddingPx, paddingPx);
        add(timeLabel, layout);

        // Time TextField
        layout = new GridBagConstraints();
        layout.gridx = 0;
        layout.gridy = 3;
        layout.insets = new Insets(paddingPx, paddingPx, paddingPx, paddingPx);
        add(timeText, layout);

        // Button
        layout = new GridBagConstraints();
        layout.gridx = 0;
        layout.gridy = 4;
        layout.insets = new Insets(paddingPx, paddingPx, paddingPx, paddingPx);
        add(getDateTimeButton, layout);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        ZonedDateTime dateTime = ZonedDateTime.now();
        DateTimeFormatter amPmFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a zzz");

        dateText.setText(dateTime.getDayOfWeek() + " " + dateTime.getMonth() + " " + dateTime.getDayOfMonth() + ", " + dateTime.getYear());
        timeText.setText(dateTime.format(amPmFormatter));

        repaint();
    }
}
