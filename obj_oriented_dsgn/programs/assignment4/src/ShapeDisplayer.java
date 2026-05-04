import java.awt.*;
import javax.swing.*;

/**
 * Entry point for the Shape Displayer application.
 *
 * @see CompositeShape
 */
public class ShapeDisplayer {
    private static final int FRAME_WIDTH  = 600;
    private static final int FRAME_HEIGHT = 500;
    private static final int ICON_SIZE    = 70;

    private static final CompositeShape carShape   = new CarShape();
    private static final CompositeShape snowMan    = new SnowMan();
    private static final CompositeShape houseShape = new HouseShape();

    private static final DrawingPanel drawingPanel = new DrawingPanel();

    /**
     * Launches the Shape Displayer application.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setTitle("Shape Displayer");
        f.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        f.setLayout(new BorderLayout());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton carButton = new JButton(new ShapeIcon(carShape, ICON_SIZE, ICON_SIZE));
        JButton snowButton = new JButton(new ShapeIcon(snowMan, ICON_SIZE, ICON_SIZE));;
        JButton houseButton = new JButton(new ShapeIcon(houseShape, ICON_SIZE, ICON_SIZE));

        carButton.addActionListener(e -> drawingPanel.setShape(carShape));
        snowButton.addActionListener(e -> drawingPanel.setShape(snowMan));
        houseButton.addActionListener(e -> drawingPanel.setShape(houseShape));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(carButton);
        buttonPanel.add(snowButton);
        buttonPanel.add(houseButton);

        f.add(buttonPanel,  BorderLayout.NORTH);
        f.add(drawingPanel, BorderLayout.CENTER);

        // Select the car by default
        drawingPanel.setShape(carShape);

        f.setVisible(true);
    }
}
