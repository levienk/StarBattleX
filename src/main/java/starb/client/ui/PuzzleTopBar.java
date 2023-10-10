package starb.client.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import static starb.client.StarbClient.TEMPLATE_BAR_COLOR;
import static starb.client.StarbClient.switchScene;

public class PuzzleTopBar extends HBox{

    public PuzzleTopBar() {
        this.setPadding(new Insets(10,10,10,10));

        Background background = new Background(new BackgroundFill(TEMPLATE_BAR_COLOR,
                CornerRadii.EMPTY, Insets.EMPTY));
        this.setBackground(background);

        this.setAlignment(Pos.CENTER);

        Pane fillerPane = new Pane();
        HBox.setHgrow(fillerPane, Priority.ALWAYS);

        // Label for the current level the user is on.
        // TODO Could not get to work with stylesheet.
        // TODO Implement the puzzle name.
        Label levelName = new Label("Level 1");
        levelName.setFont(new Font("Arial", 20));
        levelName.setStyle("-fx-font-weight: bold");
        levelName.setStyle("-fx-text-fill: #ffffff");

        // Level Menu Button
        Button levelMenuButton = new Button("Level Menu");
        levelMenuButton.setOnAction(e -> {
            try {
                switchScene(new Scene(new LevelMenuScene()));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        //Alert button
        Button showAlertButton = new Button("Show Alert");
        showAlertButton.setOnAction (e -> {
            CustomAlert testAlert = new CustomAlert("Test Alert", "this is a test!");
            testAlert.showAndWait();
        });

        this.getChildren().addAll(levelName, fillerPane, showAlertButton, levelMenuButton);
    }
}
