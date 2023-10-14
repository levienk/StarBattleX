package starb.client.ui.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import starb.client.EventListener;
import starb.client.ui.StarbClient;
import starb.client.ui.components.UIBar;

import static starb.client.ui.components.ExpandingPaneGenerator.newXPPane;
import static starb.client.SceneSwitcher.*;

public class LevelMenuScene extends VBox implements EventListener {

    private final LevelSelector levelSelector;
    public LevelMenuScene() throws Exception {

        levelSelector = new LevelSelector();

        // Register this class as an event listener
        StarbClient.addEventListener(this);

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
                setScene(PuzzleScene.class);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        topBar.getChildren().addAll(newXPPane('h'), backButton);
        bottomBar.getChildren().addAll(goodButton, switchSceneTestButton, newXPPane('h'),
                neatButton);


        this.getChildren().addAll(topBar, levelSelector, bottomBar);

    }

    private static class LevelSelector extends StackPane {

        GridPane levelSelectionArea;

        public LevelSelector() {

            this.setPadding(new Insets(10,10,10,10));

            Button prevPageButton = new Button("<");
            StackPane.setAlignment(prevPageButton, Pos.CENTER_LEFT);

            Button nextPageButton = new Button(">");
            StackPane.setAlignment(nextPageButton, Pos.CENTER_RIGHT);

            levelSelectionArea = new GridPane();

            levelSelectionArea.setPrefWidth(400);

            GridPane.setRowSpan(levelSelectionArea, 5);
            GridPane.setColumnSpan(levelSelectionArea, 5);
            levelSelectionArea.setHgap(10);
            levelSelectionArea.setVgap(10);


            levelSelectionArea.setAlignment(Pos.CENTER);

            // Reset this later.
            setLevelsUnlocked(3);

            // Transparent dark background
            levelSelectionArea.setBackground(new Background(new BackgroundFill(
                    javafx.scene.paint.Color.rgb(0,0,0,0.1),
                    CornerRadii.EMPTY, Insets.EMPTY)));

            VBox.setVgrow(this, Priority.ALWAYS);

            this.getChildren().addAll(levelSelectionArea,prevPageButton,nextPageButton);

        }

        private void setLevelsUnlocked(int levelsUnlocked) {

            if (levelsUnlocked < 0 || levelsUnlocked > 25) {
                throw new IllegalArgumentException("Levels unlocked must be between 0 and 25");
            }


            for (int i = 0; i < 25; i++) {

                Button levelButton = new Button((i+1) + "");

                if (i < levelsUnlocked) {
                    levelButton.getStyleClass().add("level-button-completed");
                } else if (i == levelsUnlocked) {
                    levelButton.getStyleClass().add("level-button-new");
                } else {
                    levelButton.getStyleClass().add("level-button-locked");
                }

                levelButton.setPrefHeight(70);
                levelButton.setPrefWidth(70);

                levelSelectionArea.add(levelButton, i % 5, i / 5);

            }
        }


    }


    @Override
    public void onEvent(String event, Object... args) {

        if (event.equals("setLevelsUnlocked")) {
            // TODO
            if (args[0] instanceof Integer) {
                int levelsUnlocked = (int) args[0];
                System.out.println("Levels unlocked: " + levelsUnlocked);
                levelSelector.setLevelsUnlocked(levelsUnlocked);
            }
        }

    }
}
