package starb.client.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import starb.client.StarbClient;

import static starb.client.ExpandingPaneGenerator.newXPPane;

public class LevelMenuScene extends VBox {

    public LevelMenuScene() throws Exception {

        // Set the Style
        this.getStylesheets().add(StarbClient.COMMON_STYLESHEET.
                        toURI().toURL().toString());

        HBox topBar = new UIBar();
        HBox bottomBar = new UIBar();
        bottomBar.setSpacing(10);

        Button backButton = new Button("Back");
        Button goodButton = new Button("Good :D");

        Button neatButton = new Button("Centerpiece");
        neatButton.setMinWidth(200);

        // TODO delete these later
        Button switchSceneTestButton = new Button("Switch Scene Test");

        switchSceneTestButton.setOnAction(e -> {
            try {
                StarbClient.switchScene(new Scene(new PuzzleScene()));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        topBar.getChildren().addAll(newXPPane('h'), backButton);
        bottomBar.getChildren().addAll(goodButton, switchSceneTestButton, newXPPane('h'),
                neatButton);


        this.getChildren().addAll(topBar, new LevelSelector(), bottomBar);

    }



}
