package HauptProgramm;

import HauptProgramm.Hilfsklassen.Eintrag;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ReasoncodeView {
    Button ok;
    ComboBox<String> b;
    MainGui mainGui;
    Scene backup;

    public ReasoncodeView(Stage stage, BorderPane pane, MainGui mainGui) {
        this.mainGui = mainGui;
        backup = stage.getScene();
        ok = new Button("OK");
        b = new ComboBox<>();
        b.getItems().addAll("Bitte auswählen:", "Emails lesen", "Frage an SuSa", "Frage an Teamleiter", "Coaching Gespräch", "Teamleiterbesprechung", "Andere");
        b.getSelectionModel().selectFirst();
        HBox box2 = new HBox();
        box2.getChildren().addAll(b, ok);
        box2.setAlignment(Pos.CENTER);
        box2.setSpacing(10.0);
        box2.setPadding(new Insets(10.0));
        pane.setCenter(box2);
        stage.show();

        ok.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (b.getSelectionModel().getSelectedItem().toString().equals("Emails lesen")) {
                    Eintrag eintrag = new Eintrag("Qualifikation", "Emails lesen");
                    new TimerView(stage, eintrag, pane, mainGui, pane.getCenter());

                } else if (b.getSelectionModel().getSelectedItem().toString().equals("Frage an SuSa")) {
                    Eintrag eintrag = new Eintrag("Qualifikation", "Frage SuSa");
                    new TimerView(stage, eintrag, pane, mainGui, pane.getCenter());

                } else if (b.getSelectionModel().getSelectedItem().toString().equals("Frage an Teamleiter")) {
                    Eintrag eintrag = new Eintrag("Qualifikation", "Frage Teamleiter");
                    new TimerView(stage, eintrag, pane, mainGui, pane.getCenter());

                } else if (b.getSelectionModel().getSelectedItem().toString().equals("Coaching Gespräch")) {
                    Eintrag eintrag = new Eintrag("Qualifikation", "Besprechung Coaching");
                    new TimerView(stage, eintrag, pane, mainGui, pane.getCenter());

                } else if (b.getSelectionModel().getSelectedItem().toString().equals("Teamleiterbesprechung")) {
                    Eintrag eintrag = new Eintrag("Qualifikation", "Teamleiterbesprechung");
                    new TimerView(stage, eintrag, pane, mainGui, pane.getCenter());

                } else if (b.getSelectionModel().getSelectedItem().toString().equals("Andere")) {
                    Node centerPane = pane.getCenter();
                    GridPane Gpane = new GridPane();
                    Gpane.setAlignment(Pos.CENTER);
                    Gpane.setHgap(10.0);
                    Gpane.setVgap(5.0);
                    Label zustandLabel = new Label("Reasoncode:");
                    Label nachrichtLabel = new Label("Aktion:");
                    ComboBox<String> zustandField = new ComboBox();
                    zustandField.getItems().addAll("Qualifikation", "Sonstige Sekundärzeiten", "Pause", "Bildschirmerholzeit");
                    TextField nachrichtFiled = new TextField();
                    Button ok = new Button("OK");
                    Button abbrechen = new Button("Abbrechen");
                    Gpane.add(zustandLabel, 0, 0);
                    Gpane.add(nachrichtLabel, 0, 1);
                    Gpane.add(zustandField, 1, 0);
                    Gpane.add(nachrichtFiled, 1, 1);
                    HBox okBox = new HBox();
                    okBox.setSpacing(5.0);
                    okBox.getChildren().addAll(abbrechen, ok);
                    okBox.setAlignment(Pos.CENTER);
                    Gpane.add(okBox, 0, 2, 3, 1);
                    pane.setCenter(Gpane);
                    mainGui.show();
                    abbrechen.setPrefWidth(90);
                    ok.setPrefWidth(90);
                    ok.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            String zustand = null;
                            String nachricht = null;
                            if (zustandField != null & nachrichtFiled != null) {
                                zustand = zustandField.getSelectionModel().getSelectedItem().toString();
                                nachricht = nachrichtFiled.getText();
                            }
                            if (!zustand.equals("") & !nachricht.equals("")) {
                                Eintrag eintrag = new Eintrag(zustand, nachricht);
                                new TimerView(stage, eintrag, pane, mainGui, centerPane);
                            }


                        }
                    });

                    abbrechen.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            pane.setCenter(box2);

                        }
                    });

                }

            }
        });
		
		
		/*emailsChecken.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Eintrag eintrag = new Eintrag("Qualifikation", "Emails lesen");
				new TimerView(stage, backup, eintrag);
				
			}
		});
		
		frageSuSa.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Eintrag eintrag = new Eintrag("Qualifikation", "Frage SuSa");
				new TimerView(stage, backup, eintrag);
				
			}
		});
		
		frageTeamleiter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Eintrag eintrag = new Eintrag("Qualifikation", "Frage Teamleiter");
				new TimerView(stage, backup, eintrag);
				
			}
		});
		
		Coaching.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Eintrag eintrag = new Eintrag("Qualifikation", "Besprechung Coaching");
				new TimerView(stage, backup, eintrag);
				
			}
		});
		
		teamleiterBesprechung.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Eintrag eintrag = new Eintrag("Qualifikation", "Teamleiterbesprechung");
				new TimerView(stage, backup, eintrag);
				
			}
		});*/
    }
}
