package starb.client.ui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;

import static starb.client.SceneSwitcher.setScene;

public class PuzzleTopBar extends UIBar {

    public PuzzleTopBar() {
        super();

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
                setScene(LevelMenuScene.class);
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
