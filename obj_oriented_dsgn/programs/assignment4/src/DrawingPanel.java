import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * A JPanel that draws the current shape wherever the user clicks.
 *
 * @see CompositeShape
 */
public class DrawingPanel extends JPanel {

    private final ArrayList<CompositeShape> shapes = new ArrayList<>();
    private final ArrayList<Point> points = new ArrayList<>();
    private CompositeShape currentShape;

    /**
     * Constructs the DrawingPanel and registers a mouse listener.
     */
    public DrawingPanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (currentShape == null) { return; }
                shapes.add(currentShape);
                points.add(e.getPoint());
                repaint();
            }
        });
    }

    /**
     * Sets the current shape to use
     * 
     * @param shape The CompositeShape object to paint
     */
    public void setShape(CompositeShape shape) {
        currentShape = shape;
    }

    /**
     * Paints each recorded shape at its stored click position.
     *
     * @param g the Graphics context provided by Swing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < shapes.size(); i++) {
            shapes.get(i).draw(g2, points.get(i).x, points.get(i).y);
        }
    }
}
