package mainApp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

@SuppressWarnings("ALL")
public class RückrufeView {
    static MainGui mainGui;
    private static ListView<String> rueckRufe;

    public RückrufeView(Stage stage, BorderPane backup, MainGui mainGui) {
        RückrufeView.mainGui = mainGui;
        backup.setCenter(createView());
        stage.show();
        backup.setBottom(null);
    }

    public static VBox createView() {
        BorderPane pane = new BorderPane();
        VBox box = new VBox();
        HBox box2 = new HBox();
        box.setSpacing(10.0);
        box.setPadding(new Insets(10.0));
        box.setAlignment(Pos.CENTER);
        Label W00000 = new Label("W00000 +");
        W00000.setTextAlignment(TextAlignment.CENTER);
        Label rueckruf = new Label();
        box2.setAlignment(Pos.CENTER);
        box2.setSpacing(10.0);
        box2.getChildren().addAll(W00000, rueckruf);
        Tooltip f = new Tooltip();
        f.setTextAlignment(TextAlignment.CENTER);
        f.setText("Um einen Ticket-ID hinzuzufügen\nkönnen Sie auch Enter drücken");
        rueckruf.setTooltip(f);
        Button rueckrufHinzufuegen = new Button("Hinzufügen");
        box.getChildren().addAll(box2, rueckrufHinzufuegen);
        box.setAlignment(Pos.CENTER);
        rueckRufe = new ListView<>();
        Tooltip m = new Tooltip();
        m.setTextAlignment(TextAlignment.CENTER);
        m.setText("Um einen Entry zu löschen\nbitte die <- Taste drücken");
        rueckRufe.setTooltip(m);
        ListViewRückrufe();
        //pane.add(rueckRufe, 1, 0);

        rueckrufHinzufuegen.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (!MainGui.getVerwaltung().getRückrufeListe().contains("W00000" + rueckruf.getText())) {
                    MainGui.getVerwaltung().getRückrufeListe().add("W00000" + rueckruf.getText());
                    ListViewRückrufe();
                    MainGui.getVerwaltung().sichern();
                }
                rueckruf.setText("");
                rueckruf.requestFocus();
                mainGui.refreshToolTip();
                MultiWindowsHandler.getOpenStage().close();
                MultiWindowsHandler.remove();

            }
        });

        rueckRufe.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.BACK_SPACE)) {
                    int e = rueckRufe.getSelectionModel().getSelectedIndex();
                    rueckRufe.getItems().remove(e);
                    MainGui.getVerwaltung().getRückrufeListe().remove(e);
                    rueckruf.requestFocus();
                    MainGui.getVerwaltung().sichern();
                    mainGui.refreshToolTip();
                }

            }
        });

        rueckruf.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    rueckrufHinzufuegen.fire();
                }

            }

        });

        return box;

    }

    public static ListView<String> getRueckRufe() {
        return rueckRufe;
    }

    public static void setRueckRufe(ListView<String> rueckRufe) {
        RückrufeView.rueckRufe = rueckRufe;
    }

    public static void ListViewRückrufe() {
        if (!mainGui.getVerwaltung().getRückrufeListe().isEmpty()) {
            RückrufeView.getRueckRufe().getItems().clear();
            for (String e : mainGui.getVerwaltung().getRückrufeListe()) {
                RückrufeView.getRueckRufe().getItems().add(e);
            }
        }

    }
}
