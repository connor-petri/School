import java.awt.*;

public class RetroBoardStyle implements BoardStyle {

    @Override
    public String getStyleName() {
        return "Retro";
    }

    @Override
    public Color getFrameBackground() {
        return new Color(66, 100, 152); // sloe
    }

    @Override
    public Color getBoardBackground() {
        return new Color(45, 159, 118); // spearmint
    }

    @Override
    public Color getPitColor() {
        return new Color(189, 48, 135); // fandango
    }

    @Override
    public Color getMancalaColor() {
        return new Color(210, 97, 168); // super pink
    }

    @Override
    public Color getStoneColor() {
        return new Color(240, 209,78); // elegant yellow
    }

    @Override
    public Color getLabelColor() {
        return new Color(240, 209, 78); // elegant yellow
    }

    @Override
    public Font getLabelFont() {
        return new Font("Monospaced", Font.BOLD, 14);
    }

    @Override
    public Color getTurnLabelColor() {
        return new Color(45, 159, 118); // spearmint
    }

    @Override
    public Font getTurnLabelFont() {
        return new Font("Monospaced", Font.BOLD, 20);
    }

    @Override
    public Color getStatusLabelColor() {
        return new Color(240, 209, 78); // elegant yellow
    }

    @Override
    public Font getStatusLabelFont() {
        return new Font("Monospaced", Font.BOLD, 32);
    }

    @Override 
    public Color getButtonColor() {
        return new Color(240, 209, 78); // elegant yellow
    }

    @Override
    public Color getButtonTextColor() {
        return new Color(189, 48, 135); // fandango
    }

    @Override
    public Color getButtonHoverColor() {
        return new Color(230, 190, 55); // darker yellow for hover
    }
}
