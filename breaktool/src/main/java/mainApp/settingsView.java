package mainApp;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class settingsView {
    public settingsView() {

    }

    private void createView() {
        Stage stage = new Stage();
        BorderPane pane = new BorderPane();
        HBox textfileds = new HBox();
        HBox labels = new HBox();
        TextField sksWmsField = new TextField();
        TextField svtField = new TextField();
        TextField irrpField = new TextField();
        TextField onlineStatistikenField = new TextField();
        TextField winsField = new TextField();
        TextField csp3Field = new TextField();

        Label sksWmsFieldLabel = new Label();
        Label svtFieldLabel = new Label();
        Label irrpFieldLabel = new Label();
        Label onlineStatistikenFieldLabel = new Label();
        Label winsFieldLabel = new Label();
        Label csp3FieldLabel = new Label();


        labels.getChildren().addAll(sksWmsFieldLabel, svtFieldLabel,
                irrpFieldLabel, onlineStatistikenFieldLabel,
                winsFieldLabel, csp3FieldLabel);

        textfileds.getChildren().addAll(sksWmsField, svtField,
                irrpField, onlineStatistikenField, winsField,
                winsField, csp3Field);

        //TODO Complete the Settingsmenu


    }

}
