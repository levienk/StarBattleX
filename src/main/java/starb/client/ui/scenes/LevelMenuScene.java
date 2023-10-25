package starb.client.ui.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import starb.client.domain.json.JSONReader;
import starb.client.ui.StarbClient;
import starb.client.ui.components.UIBar;

import static starb.client.SceneSwitcher.setScene;
import static starb.client.ui.components.ExpandingPaneGenerator.newXPPane;

public class LevelMenuScene extends VBox {

    private final LevelSelector levelSelector;
    public LevelMenuScene() throws Exception {

        levelSelector = new LevelSelector();

        // Set the Style
        this.getStylesheets().add(StarbClient.COMMON_STYLESHEET.
                        toURI().toURL().toString());

        HBox topBar = new UIBar();
        HBox bottomBar = new UIBar();
        bottomBar.setSpacing(10);

        // TODO - backButton should switch to PuzzleScene
        Button backButton = new Button("Back");
        Button goodButton = new Button("Good :D");

        Button neatButton = new Button("Centerpiece");
        neatButton.setMinWidth(200);

        // TODO delete these later. Temporary file.
        Button switchSceneTestButton = new Button("Switch Scene Test");

        switchSceneTestButton.setOnAction(e -> {
            try {
                JSONReader reader = new JSONReader("Assets/Puzzles/temp.txt");

                setScene(PuzzleScene.class,  reader.getBoard(), "Temporary Rank");
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
            setLevelsUnlocked(3, 1);

            // Transparent dark background
            levelSelectionArea.setBackground(new Background(new BackgroundFill(
                    javafx.scene.paint.Color.rgb(0,0,0,0.1),
                    CornerRadii.EMPTY, Insets.EMPTY)));

            VBox.setVgrow(this, Priority.ALWAYS);

            this.getChildren().addAll(levelSelectionArea,prevPageButton,nextPageButton);

        }

        private void setLevelsUnlocked(int levelsUnlocked, int levelPage) {

            if (!(levelsUnlocked >= 0)) {
                throw new IllegalArgumentException("Levels unlocked must be between at least 0.");
            }

            if (!(levelPage >= 1)) {
                throw new IllegalArgumentException("Level page must be at least 1.");
            }

            int levelCounter;
            for (int i = 0; i < 25; i++) {
                levelCounter = (levelPage - 1) * 25 + i;

                Button levelButton = new Button((levelCounter+1) + "");

                if (levelCounter < levelsUnlocked) {
                    levelButton.getStyleClass().add("level-button-completed");
                } else if (levelCounter == levelsUnlocked) {
                    levelButton.getStyleClass().add("level-button-new");
                } else {
                    levelButton.getStyleClass().add("level-button-locked");
                }

                levelButton.setPrefHeight(70);
                levelButton.setPrefWidth(70);

                levelSelectionArea.add(levelButton, (i % 5), (i / 5));

            }
        }


    }

}
