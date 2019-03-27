package HauptProgramm;

import HauptProgramm.Hilfsklassen.Eintrag;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

class TimerView {
    private Button ende;
    private Node n;
    private Timeline timeline;
    private int seconds = 0;

    private Label display = new Label(String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60));
    private Eintrag eintrag;

    TimerView(Stage stage, Eintrag eintrag, BorderPane pane, MainGui mainGui, Node centerPane) {
        this.eintrag = eintrag;
        n = pane.getTop();
        //Text beschreibung = new Text(eintrag.getZustand()+": "+eintrag.getNachricht());
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
            pane.setTop(n);
            pane.setCenter(centerPane);
            stage.show();
            //stage.setTitle("Pausenprogramm");
            eintrag.setEndzeit();
           MainGui.getVerwaltung().getPausensituationen().add(eintrag);
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
        t.setText("So lange sind Sie schon im Zustand:\n" + eintrag.getNachricht() + " (" + eintrag.getZustand() + ")");
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


}
