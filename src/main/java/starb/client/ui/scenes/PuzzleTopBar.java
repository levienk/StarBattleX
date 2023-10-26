package starb.client.ui.scenes;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import starb.client.ui.components.CustomAlert;
import starb.client.ui.components.ExpandingPaneGenerator;
import starb.client.ui.components.Title;
import starb.client.ui.components.UIBar;

import java.io.File;
import java.net.MalformedURLException;

import static starb.client.ui.scenes.SceneSwitcher.setScene;

public class PuzzleTopBar extends UIBar {

    public PuzzleTopBar(PuzzleUI ui, String levelName) throws MalformedURLException {
        super();

        // Label for the current level the user is on.
        Label levelNameLabel = new Title(levelName);

        Pane fillerPane = ExpandingPaneGenerator.newXPPane('h');

        // Clear Board button
        Button clearBoardButton = new Button("Clear Board");
        clearBoardButton.getStylesheets().add(new File("Assets/Stylesheets/clearBoardButtonStyle.css").
                toURI().toURL().toString());
        clearBoardButton.setOnAction(e -> {
            ui.clear();
        });

        // Level Menu button
        Button levelMenuButton = new Button("Level Menu");
        levelMenuButton.setOnAction(e -> {
            try {
                setScene(LevelMenuScene.class);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        this.getChildren().addAll(levelNameLabel, fillerPane, clearBoardButton, levelMenuButton);
    }
}
