package starb.client.ui.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import starb.client.ui.components.CustomAlert;
import starb.client.ui.components.ExpandingPaneGenerator;
import starb.client.ui.components.Title;
import starb.client.ui.components.UIBar;

import static starb.client.ui.StarbClient.switchScene;

public class PuzzleTopBar extends UIBar {

    public PuzzleTopBar() {
        super();

        // Label for the current level the user is on.
        // TODO Implement the puzzle name.
        Label levelName = new Title("Level 1");

        Pane fillerPane = ExpandingPaneGenerator.newXPPane('h');

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
