package mainApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

@SuppressWarnings("ALL")
class anrufeView {
    private TextField reasonField;
    private Button hinzufügen;

    anrufeView(Stage stage, BorderPane pane) {
        pane.setCenter(createView());
        stage.show();
        //Label();

        hinzufügen.setOnAction(event -> {
            if (!MainGui.getVerwaltung().getReasonString().contains(reasonField.getText()) & !reasonField.getText().equals("")) {
                MainGui.getVerwaltung().getReasonString().add(reasonField.getText() + ".\n");
            } else {
                return;
            }
            reasonField.setText("");
            reasonField.requestFocus();
            MainGui.getVerwaltung().sichern();
            MultiWindowsHandler.getOpenStage().close();
            MultiWindowsHandler.remove();
        });
        reasonField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                hinzufügen.fire();
            }


        });
    }

    private VBox createView() {

        HBox box2 = new HBox();
        VBox mainBox = new VBox();
        box2.setSpacing(10.0);
        box2.setAlignment(Pos.CENTER);
        Label begründung = new Label("Begrundung:");
        hinzufügen = new Button("Hinzufügen");
        mainBox.setSpacing(10.0);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(10.0));
        Tooltip t = new Tooltip();
        t.setTextAlignment(TextAlignment.CENTER);
        t.setText("Um eine Begründung zu\nspeichern die Entertaste drücken");
        reasonField = new TextField();
        reasonField.setTooltip(t);
        box2.getChildren().addAll(begründung, reasonField);
        mainBox.getChildren().addAll(/*box,*/ box2, hinzufügen);
        return mainBox;

    }


}
