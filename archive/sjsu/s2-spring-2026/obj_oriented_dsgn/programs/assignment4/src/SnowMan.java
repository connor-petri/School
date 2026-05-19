import java.awt.*;
import java.awt.geom.*;

/**
 * A composite shape representing a snowman.
 *
 * @see CompositeShape
 * @see ShapeDisplayer
 */
public class SnowMan implements CompositeShape {

    private static final int SNOWMAN_WIDTH  = 40;
    private static final int SNOWMAN_HEIGHT = 60;

    /**
     * Draws a snowman outline at the given position.
     *
     * @param g2 the Graphics2D context to draw on
     * @param x the x-coordinate of the top-left corner
     * @param y the y-coordinate of the top-left corner
     */
    @Override
    public void draw(Graphics2D g2, int x, int y) {
        Ellipse2D.Double lower  = new Ellipse2D.Double(x,      y + 36, 40, 24);
        Ellipse2D.Double middle = new Ellipse2D.Double(x + 5,  y + 18, 30, 22);
        Ellipse2D.Double head   = new Ellipse2D.Double(x + 10, y,      20, 20);
        Ellipse2D.Double leftEye  = new Ellipse2D.Double(x + 14, y + 5, 4, 4);
        Ellipse2D.Double rightEye = new Ellipse2D.Double(x + 22, y + 5, 4, 4);

        g2.draw(lower);
        g2.draw(middle);
        g2.draw(head);
        g2.fill(leftEye);
        g2.fill(rightEye);
    }

    /**
     * Returns the width of the snowman's bounding box.
     *
     * @return the width in pixels
     */
    @Override
    public int getWidth() { return SNOWMAN_WIDTH; }

    /**
     * Returns the height of the snowman's bounding box.
     *
     * @return the height in pixels
     */
    @Override
    public int getHeight() { return SNOWMAN_HEIGHT; }
}
