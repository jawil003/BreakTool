package mainApp;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import mainApp.toolClasses.WindowManager;

public class cancelView {

    public cancelView() {
        createView();
    }

    public void createView() {
        Stage stage = new Stage();
        BorderPane pane = new BorderPane();
        Button cancelButton = new Button();
        cancelButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/error.png"))));
        pane.setCenter(cancelButton);

        WindowManager.setWindowWidth(stage);
        WindowManager.setWindowHeight(stage);

        stage.setScene(new Scene(pane));
        stage.show();
    }

}
