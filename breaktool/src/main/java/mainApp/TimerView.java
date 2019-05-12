package mainApp;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import mainApp.toolClasses.Entry;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings("ALL")
class TimerView implements Initializable {
    private Button ende;
    private Node n;
    private Timeline timeline;
    private int seconds = 0;

    @FXML
    private Label grundLabel;
    @FXML
    private TextField grundField;
    @FXML
    private Button okButton;

    private Label display = new Label(String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60));
    private Entry entry;

    TimerView(Stage stage, Entry entry, BorderPane pane, MainGui mainGui, Node centerPane) {
        this.entry = entry;
        n = pane.getTop();
        //Text beschreibung = new Text(entry.getZustand()+": "+entry.getNachricht());
        //beschreibung.setStyle("-fx-font-weight: regular");
        //HBox box = new HBox(beschreibung);
        //box.setAlignment(Pos.CENTER);
        //box.setPadding(new Insets(5.0));
        pane.setTop(null);
        BorderPane t = new BorderPane();
        t.setCenter(createView());
        pane.setCenter(t);
        stage.show();
        timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> up()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        ende.setOnAction(event -> {
            stop();
            entry.setEndzeit();
            if (entry.getNachricht().contains(": ")) {
                try {
                    FXMLLoader loader = new FXMLLoader(new File("breaktool/Fxml/reasonView.fxml").toURI().toURL());
                    loader.setController(this);
                    Pane p = loader.load();
                    pane.setCenter(p);
                    okButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            entry.addNachricht(grundField.getText());
                            MainGui.getVerwaltung().getPausensituationen().add(entry);
                            MainGui.getVerwaltung().sichern();
                            stage.close();
                            mainGui.refreshToolTip();
                        }
                    });
                    return;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            pane.setTop(n);
            pane.setCenter(centerPane);
            stage.show();
            //stage.setTitle("Pausenprogramm");
            MainGui.getVerwaltung().getPausensituationen().add(entry);
            MainGui.getVerwaltung().sichern();
            mainGui.getStage().close();
            mainGui.refreshToolTip();
            MultiWindowsHandler.getOpenStage().close();
            MultiWindowsHandler.remove();


        });

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                ende.fire();
            }
        });

    }

    private VBox createView() {
        VBox box = new VBox();
        box.setSpacing(10.0);
        Tooltip t = new Tooltip();
        t.setText("So lange sind Sie schon im Zustand:\n" + entry.getNachricht() + " (" + entry.getZustand() + ")");
        t.setTextAlignment(TextAlignment.CENTER);
        display.setTooltip(t);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10.0));
        ende = new Button("Ende");
        box.getChildren().addAll(display, ende);

        return box;
    }

    private void Label() {
        display.setText(String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60));
    }

    private void stop() {
        timeline.stop();
    }

    private void up() {
        seconds++;
        Label();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
