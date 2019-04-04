package mainApp.toolClasses;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class WindowManager {

    private static Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

    public static void setWindowWidth(Stage currentStage) {
        if (System.getProperty("os.name").startsWith("Windows")) {
            currentStage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 350);

        } else {
            currentStage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 340);

        }
    }

    public static void setWindowHeight(Stage currentStage) {
        currentStage.setY(primaryScreenBounds.getMinX() + 10);
    }
}
