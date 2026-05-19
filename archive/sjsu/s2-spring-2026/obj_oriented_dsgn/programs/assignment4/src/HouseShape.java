import java.awt.*;
import java.awt.geom.*;

/**
 * A composite shape representing a house.
 *
 * @see CompositeShape
 * @see ShapeDisplayer
 */
public class HouseShape implements CompositeShape {

    private static final int HOUSE_WIDTH  = 60;
    private static final int HOUSE_HEIGHT = 60;

    /**
     * Draws a house outline at the given position.
     *
     * @param g2 the Graphics2D context to draw on
     * @param x the x-coordinate of the top-left corner
     * @param y the y-coordinate of the top-left corner
     */
    @Override
    public void draw(Graphics2D g2, int x, int y) {
        Rectangle2D.Double body   = new Rectangle2D.Double(x,      y + 25, 60, 35);
        Rectangle2D.Double door   = new Rectangle2D.Double(x + 22, y + 43, 16, 17);
        Rectangle2D.Double window = new Rectangle2D.Double(x + 8,  y + 31, 14, 12);

        int[] roofX = { x, x + 30, x + 60 };
        int[] roofY = { y + 25, y, y + 25 };
        Polygon roof = new Polygon(roofX, roofY, 3);

        g2.draw(body);
        g2.draw(door);
        g2.draw(window);
        g2.draw(roof);
    }

    /**
     * Returns the width of the house's bounding box.
     *
     * @return the width in pixels
     */
    @Override
    public int getWidth() { return HOUSE_WIDTH; }

    /**
     * Returns the height of the house's bounding box.
     *
     * @return the height in pixels
     */
    @Override
    public int getHeight() { return HOUSE_HEIGHT; }
}
