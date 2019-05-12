package mainApp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mainApp.toolClasses.Entry;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SusaInformationView implements Initializable {

    Node n;
    Button ok;
    Stage s;
    BorderPane pane;
    MainGui gui;
    Node centerView;
    FXMLLoader loader;
    @FXML
    private Label nameLabel;
    @FXML
    private Label grundLabel;
    @FXML
    private TextField nameField;
    @FXML
    private TextField grundField;
    @FXML
    private Button okButton;
    @FXML
    private Button abbrechenButton;

    public SusaInformationView(@org.jetbrains.annotations.NotNull Stage stage, BorderPane pane, MainGui mainGui, Node centerView, FXMLLoader loader) throws IOException {
        n = pane.getTop();
        n = null;
        this.loader = loader;
        pane.setCenter(createView());
        stage.show();
        s = stage;
        this.pane = pane;
        this.gui = mainGui;
        this.centerView = centerView;

        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Entry entry = new Entry("Sonstige Sekundärzeiten", nameField.getText() + ": " + grundField.getText());
                new TimerView(s, entry, pane, gui, centerView);
            }
        });

        abbrechenButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                pane.setCenter(centerView);

            }
        });


    }


    private VBox createView() throws IOException {

        loader.setController(this);
        return loader.load();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init() {


    }
}
