package starb.client.ui.scenes;

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

import static starb.client.SceneSwitcher.setScene;

public class PuzzleTopBar extends UIBar {

    public PuzzleTopBar(String levelName) {
        super();

        // Label for the current level the user is on.
        Label levelNameLabel = new Title(levelName);

        Pane fillerPane = ExpandingPaneGenerator.newXPPane('h');

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
        //TODO - Remove this
        Button showAlertButton = new Button("Show Alert");
        showAlertButton.setOnAction (e -> {
            CustomAlert testAlert = new CustomAlert("Test Alert", "this is a test!");
            testAlert.showAndWait();
        });

        this.getChildren().addAll(levelNameLabel, fillerPane, showAlertButton, levelMenuButton);
    }
}
