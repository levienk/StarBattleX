package starbattle.client.ui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import starbattle.client.ui.scenes.StarBattleClient;

import java.net.MalformedURLException;

public class CustomAlert extends Stage {

    private HBox topBar;

    private HBox bottomBar;
    private Label messageLabel;
    private Button okButton;
    private Button cancelButton;

    public CustomAlert(String title, String message) {
        this.setTitle(title);
        this.initModality(Modality.APPLICATION_MODAL);
        this.initStyle(StageStyle.UNDECORATED);

        topBar = new UIBar();
        messageLabel = new Label(message);
        okButton = new Button("Ok");
        cancelButton = new Button("Cancel");
        bottomBar = new UIBar();

        messageLabel.setPrefHeight(180);
        messageLabel.setAlignment(Pos.CENTER);
        messageLabel.setWrapText(true);
        messageLabel.setTextAlignment(TextAlignment.CENTER);
        messageLabel.setBackground(new Background(new BackgroundFill(
                javafx.scene.paint.Color.rgb(0,0,0,0.1),
                CornerRadii.EMPTY, Insets.EMPTY)));

        Label topBarTitle = new Label(title);

        topBar.getChildren().add(topBarTitle);

        okButton.setOnAction(e -> this.close());
        cancelButton.setOnAction(e -> this.close());

        this.setWidth(300);
        this.setHeight(250);

        this.setResizable(false);

        VBox layout = new VBox(10);
        bottomBar.setAlignment(Pos.TOP_CENTER);

        bottomBar.getChildren().addAll(okButton, cancelButton);
        layout.getChildren().addAll(topBar, messageLabel, bottomBar);
        layout.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(layout, 300, 150);

        try {
            scene.getStylesheets().add(StarBattleClient.COMMON_STYLESHEET.
                    toURI().toURL().toString());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        this.setScene(scene);
    }

    public void setMessage(String message) {
        this.messageLabel.setText(message);
    }

    public Button getOkButton() {
        return okButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

}
