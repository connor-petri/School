import java.awt.*;

public interface BoardStyle {
    String getStyleName();

    Color getFrameBackground();
    Color getBoardBackground();

    Color getPitColor();
    Color getMancalaColor();
    Color getStoneColor();

    Color getLabelColor();
    Font getLabelFont();

    Color getTurnLabelColor();
    Font getTurnLabelFont();

    Color getStatusLabelColor();
    Font getStatusLabelFont();

    Color getButtonColor();
    Color getButtonTextColor();
    Color getButtonHoverColor();
}
