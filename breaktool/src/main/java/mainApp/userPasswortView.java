package mainApp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class userPasswortView {

    private Stage stage = null;
    private boolean benutzerPressed = false;
    private boolean passwordPressed = false;

    public userPasswortView(Stage stage, String useCase) {
        this.stage = stage;
        create(stage, useCase);
    }

    private void create(Stage stage, String useCase) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(5.0);
        javafx.scene.control.Button benutzer = new javafx.scene.control.Button("Benutzername in Zwischenablage");
        javafx.scene.control.Button passwort = new javafx.scene.control.Button("Passwort in Zwischenablage");

        box.getChildren().addAll(benutzer, passwort);
        stage.setScene(new Scene(box, 330, 150));
        stage.show();
        //passwort.setPrefHeight(benutzer.getHeight());
        passwort.setPrefSize(benutzer.getWidth(), passwort.getHeight());
        stage.show();

        switch (useCase) {
            case "testMail":
                benutzer.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection("team52@t-online.de"), null);
                        setBenutzerPressed();
                    }
                });

                passwort.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection("YC2018$tudent!"), null);
                        setPasswortPressed();
                    }
                });

            case "retourenschein":
                benutzer.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection("Kundenservice"), null);
                        setBenutzerPressed();
                    }
                });

                passwort.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection("12345"), null);
                        setPasswortPressed();
                    }
                });
        }


    }

    private void setBenutzerPressed() {
        if (passwordPressed) {
            stage.close();
        } else {
            benutzerPressed = true;
        }
    }

    private void setPasswortPressed() {
        if (benutzerPressed) {
            stage.close();
        } else {
            passwordPressed = true;
        }
    }
}
