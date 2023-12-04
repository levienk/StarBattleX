package starbattle.client.ui.scenes;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import starbattle.client.ui.components.ExpandingPaneGenerator;
import starbattle.client.ui.components.Title;
import starbattle.client.ui.components.UIBar;

import java.net.MalformedURLException;

import static starbattle.client.ui.scenes.SceneSwitcher.setNewScene;

public class PuzzleTopBar extends UIBar {

    public PuzzleTopBar(PuzzleUI ui, int levelNumber) throws MalformedURLException {
        super();

        // Label for the current level the user is on.
        Label levelNameLabel = new Title("Level " + levelNumber);

        Pane fillerPane = ExpandingPaneGenerator.newXPPane('h');

        // Clear Board button
        Button clearBoardButton = new Button("Clear Board");
        clearBoardButton.getStyleClass().add("clear-button");
        clearBoardButton.setOnAction(e -> {
            ui.clear();
        });

        // Level Menu button
        Button levelMenuButton = new Button("Level Menu");
        levelMenuButton.setOnAction(e -> {
            try {
                setNewScene(LevelMenuScene.class);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        this.getChildren().addAll(levelNameLabel, fillerPane, clearBoardButton, levelMenuButton);
    }
}
