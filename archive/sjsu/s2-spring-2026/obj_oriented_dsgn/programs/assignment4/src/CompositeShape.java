import java.awt.*;

/**
 * Interface representing a composite shape made of primitive geometric components.
 *
 * @see CarShape
 * @see SnowMan
 * @see HouseShape
 * @see ShapeIcon
 */
public interface CompositeShape {

    /**
     * Draws this shape at the given position.
     *
     * @param g2 the Graphics2D context to draw on
     * @param x the x-coordinate of the top-left corner
     * @param y the y-coordinate of the top-left corner
     */
    void draw(Graphics2D g2, int x, int y);

    /**
     * Returns the width of this shape's bounding box.
     *
     * @return the width in pixels
     */
    int getWidth();

    /**
     * Returns the height of this shape's bounding box.
     *
     * @return the height in pixels
     */
    int getHeight();
}
