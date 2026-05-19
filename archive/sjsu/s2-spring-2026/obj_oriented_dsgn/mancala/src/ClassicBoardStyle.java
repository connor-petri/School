import java.awt.*;

public class ClassicBoardStyle implements BoardStyle {

    @Override
    public String getStyleName() {
        return "Classic";
    }

    @Override
    public Color getFrameBackground() {
        return new Color(100, 59, 32); // perma-brown
    }

    @Override
    public Color getBoardBackground() {
        return new Color(153, 87, 54); // swiss chocolate
    }

    @Override
    public Color getPitColor() {
        return new Color(213, 177, 146); // toned skin
    }

    @Override
    public Color getMancalaColor() {
        return new Color(231, 200, 165); // matte peach
    }

    @Override
    public Color getStoneColor() {
        return new Color(241, 221, 206); // ivory satin
    }

    @Override
    public Color getLabelColor() {
        return new Color(100, 59, 32); // perma-brown
    }

    @Override
    public Font getLabelFont() {
        return new Font("Serif", Font.BOLD, 16);
    }

    @Override
    public Color getTurnLabelColor() {
        return new Color(241, 221, 206); // ivory satin
    }

    @Override
    public Font getTurnLabelFont() {
        return new Font("Serif", Font.BOLD, 18);
    }

    @Override
    public Color getStatusLabelColor() {
        return new Color(241, 221, 206); // ivory satin
    }

    @Override
    public Font getStatusLabelFont() {
        return new Font("Serif", Font.BOLD, 34);
    }

    @Override
    public Color getButtonColor() {
        return new Color(213 , 177, 146); // toned skin 
    }

    @Override
    public Color getButtonTextColor() {
        return new Color(100, 59, 32); // perma-brown
    }

    @Override
    public Color getButtonHoverColor() {
        return new Color(190, 150, 120); // dark toned skin
    }
}
