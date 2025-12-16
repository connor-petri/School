package week16;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.scene.canvas.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.paint.*;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    private static final int canvasWidth = 640;
    private static final int canvasHeight = 480;

    private static final Color skyColor = Color.rgb(0, 110, 255);

    private static final double grassY = canvasHeight * .66;
    private static final Color grassColor = Color.rgb(15, 99, 27);

    private static final int baseX = 280;
    private static final int baseY = 190;
    private static final int baseSize = 150;
    private static final Color baseClr = Color.rgb(138, 0, 0);

    private static final int chimnyWidth = 25;
    private static final int chimnyHeight = 75;
    private static final int roofHeight = 75;
    private static final Color roofColor = Color.rgb(80, 80, 80);

    private static final int doorWidth = 25;
    private static final int doorHeight = 50;
    private static final Color doorColor = Color.rgb(71, 44, 9);
    private static final int knobRadius = 3;
    private static final Color knobColor = Color.rgb(255, 230, 0);

    private static final int windowSize = 25;
    private static final Color windowColor = Color.rgb(133, 216, 255);

    @Override
    public void start(Stage stage) throws IOException {
        Pane pane = new Pane();
        scene = new Scene(pane);
        stage.setScene(scene);

        Canvas canvas = new Canvas(canvasWidth, canvasHeight);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Sky
        gc.setFill(skyColor);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);

        // Grass
        gc.setFill(grassColor);
        gc.fillRect(0, grassY, canvasWidth, grassY);

        // Base
        gc.setFill(baseClr);
        gc.fillRect(baseX, baseY, baseSize, baseSize);

        // Chimny
        gc.setFill(baseClr);
        gc.fillRect(baseX + baseSize * .25 - chimnyWidth * .5,
                    baseY - chimnyHeight,
                    chimnyWidth, chimnyHeight
        );

        // Roof
        gc.setFill(roofColor);
        double[] xPointsRoof = { baseX, baseX + baseSize / 2.0, baseX + baseSize };
        double[] yPointsRoof = { baseY, baseY - roofHeight, baseY };
        gc.fillPolygon(xPointsRoof, yPointsRoof, 3);

        // Door
        gc.setFill(doorColor);
        double doorX = baseX + (baseSize * .8) - (doorWidth / 2.0);
        double doorY = baseY + baseSize - doorHeight;
        gc.fillRect(doorX, doorY, doorWidth, doorHeight);

        gc.setFill(knobColor);
        gc.fillOval(doorX + .75 * doorWidth - knobRadius, doorY + .66 * doorHeight - knobRadius, 2*knobRadius, 2*knobRadius);

        // Windows
        gc.setFill(windowColor);
        for (int i = 1; i < 3; i++) {
            for (int j = 1; j < 3; j++) {
            
                double windowX = baseX + baseSize * .25 * i - windowSize * .5;
                double windowY = baseY + baseSize * .2 * j - windowSize * .5 + 50;
                gc.fillRect(windowX, windowY, windowSize, windowSize);

                gc.setStroke(Color.rgb(0, 0, 0));
                gc.strokeRect(windowX, windowY, windowSize, windowSize);
                gc.strokeLine(windowX + windowSize * .5, windowY, windowX + windowSize * .5, windowY + windowSize);
                gc.strokeLine(windowX, windowY + windowSize * .5, windowX + windowSize, windowY + windowSize * .5);
            }
        }

        pane.getChildren().add(canvas);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}