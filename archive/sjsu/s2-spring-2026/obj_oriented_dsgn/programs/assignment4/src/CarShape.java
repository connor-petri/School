import java.awt.*;
import java.awt.geom.*;

/**
 * A composite shape representing a car.
 *
 * @see CompositeShape
 * @see ShapeDisplayer
 */
public class CarShape implements CompositeShape {

    private static final int CAR_WIDTH  = 60;
    private static final int CAR_HEIGHT = 30;

    /**
     * Draws a car outline at the given position.
     *
     * @param g2 the Graphics2D context to draw on
     * @param x the x-coordinate of the top-left corner
     * @param y the y-coordinate of the top-left corner
     */
    @Override
    public void draw(Graphics2D g2, int x, int y) {
        Rectangle2D.Double body = new Rectangle2D.Double(x, y + 10, 60, 10);
        Ellipse2D.Double frontTire = new Ellipse2D.Double(x + 10, y + 20, 10, 10);
        Ellipse2D.Double rearTire  = new Ellipse2D.Double(x + 40, y + 20, 10, 10);

        Point2D.Double r1 = new Point2D.Double(x + 10, y + 10);
        Point2D.Double r2 = new Point2D.Double(x + 20, y);
        Point2D.Double r3 = new Point2D.Double(x + 40, y);
        Point2D.Double r4 = new Point2D.Double(x + 50, y + 10);

        GeneralPath cab = new GeneralPath();
        cab.moveTo((float) r1.getX(), (float) r1.getY());
        cab.lineTo((float) r2.getX(), (float) r2.getY());
        cab.lineTo((float) r3.getX(), (float) r3.getY());
        cab.lineTo((float) r4.getX(), (float) r4.getY());
        cab.closePath();

        g2.draw(body);
        g2.draw(frontTire);
        g2.draw(rearTire);
        g2.draw(cab);
    }

    /**
     * Returns the width of the car's bounding box.
     *
     * @return the width in pixels
     */
    @Override
    public int getWidth() { return CAR_WIDTH; }

    /**
     * Returns the height of the car's bounding box.
     *
     * @return the height in pixels
     */
    @Override
    public int getHeight() { return CAR_HEIGHT; }
}
