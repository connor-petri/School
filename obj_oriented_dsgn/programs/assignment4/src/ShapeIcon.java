import java.awt.*;
import javax.swing.*;

/**
 * An Icon that renders a CompositeShape centered within a fixed bounding box.
 * Used to display shape outlines on the selector buttons.
 *
 * @see CompositeShape
 * @see ShapeDisplayer
 */
public class ShapeIcon implements Icon {

    private final CompositeShape shape;
    private final int width;
    private final int height;

    /**
     * Constructs a ShapeIcon with the given CompositeShape.
     *
     * @param shape the CompositeShape to render
     * @param width the icon width in pixels
     * @param height the icon height in pixels
     */
    public ShapeIcon(CompositeShape shape, int width, int height) {
        this.shape = shape;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the icon width.
     *
     * @return the width in pixels
     */
    @Override
    public int getIconWidth() { return width; }

    /**
     * Returns the icon height.
     *
     * @return the height in pixels
     */
    @Override
    public int getIconHeight() { return height; }

    /**
     * Paints the shape centered within the icon bounds.
     *
     * @param c the component this icon is painted on
     * @param g the Graphics context provided by Swing
     * @param x the x-coordinate of the icon's top-left corner
     * @param y the y-coordinate of the icon's top-left corner
     */
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        int offsetX = x + (width - shape.getWidth()) / 2;
        int offsetY = y + (height - shape.getHeight()) / 2;
        shape.draw(g2, offsetX, offsetY);
        g2.dispose();
    }
}
