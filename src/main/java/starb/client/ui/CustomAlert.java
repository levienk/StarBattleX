package starb.client.ui;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import starb.client.StarbClient;

import java.net.MalformedURLException;

public class CustomAlert extends Stage{
    private Label messageLabel;
    private Button okButton;
    private Button cancelButton;

    public CustomAlert(String title, String message) {
        this.setTitle(title);
        this.initModality(Modality.APPLICATION_MODAL);

        messageLabel = new Label(message);
        okButton = new Button("Ok");
        cancelButton = new Button("Cancel");

        okButton.setOnAction(e -> this.close());
        okButton.setOnAction(e -> this.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(messageLabel, okButton, cancelButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 150);

        try {
            scene.getStylesheets().add(StarbClient.COMMON_STYLESHEET.
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
