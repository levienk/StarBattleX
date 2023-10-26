package starb.client.ui.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import starb.client.domain.json.JSONReader;
import starb.client.ui.components.UIBar;

import static starb.client.ui.scenes.SceneSwitcher.setScene;
import static starb.client.ui.components.ExpandingPaneGenerator.newXPPane;

public class LevelMenuScene extends VBox {

    private final LevelSelector levelSelector;

    private final LevelPageNumberContainer levelPageNumberContainer;

    private final int MAX_PAGES = 4;

    private int levelPage;

    private int levelsUnlocked;



    public LevelMenuScene() throws Exception {

        levelSelector = new LevelSelector();
        levelPageNumberContainer = new LevelPageNumberContainer();

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


        this.getChildren().addAll(topBar, levelSelector, levelPageNumberContainer, bottomBar);

    }

    private void changePageNumber(int pg) {

            if (!(pg >= 1 && pg <= MAX_PAGES)) {
                return;
            }

            levelPage = pg;
            levelSelector.setLevelsUnlocked(levelsUnlocked, levelPage);
            levelPageNumberContainer.updateLabel(levelPage);
    }

    private int getPageNumber() {

        return levelPage;

    }

    private class LevelPageNumberContainer extends HBox {

        Label levelPageNumberLabel;

        public LevelPageNumberContainer() {

            levelPage = 1;
            levelPageNumberLabel = new Label();
            updateLabel(levelPage);

            levelPageNumberLabel.setPadding(new Insets(10,10,10,10));
            levelPageNumberLabel.setMinWidth(100);
            levelPageNumberLabel.getStyleClass().add("general-label");
            levelPageNumberLabel.setAlignment(Pos.CENTER);

            this.setAlignment(Pos.CENTER);
            this.setPadding(new Insets(0,10,10,10));
            this.getChildren().add(levelPageNumberLabel);

        }

        public void updateLabel(int pg) {

            levelPageNumberLabel.setText(pg + " | " + MAX_PAGES);

        }

    }

    private class LevelSelector extends StackPane {

        GridPane levelSelectionArea;

        public LevelSelector() {

            this.setPadding(new Insets(10,10,10,10));

            Button prevPageButton = new Button("<");
            StackPane.setAlignment(prevPageButton, Pos.CENTER_LEFT);
            prevPageButton.setOnAction(e -> {
                changePageNumber(levelPage - 1);
            });

            Button nextPageButton = new Button(">");
            StackPane.setAlignment(nextPageButton, Pos.CENTER_RIGHT);
            nextPageButton.setOnAction(e -> {
                changePageNumber(levelPage + 1);
            });

            levelSelectionArea = new GridPane();

            levelSelectionArea.setPrefWidth(400);

            GridPane.setRowSpan(levelSelectionArea, 5);
            GridPane.setColumnSpan(levelSelectionArea, 5);
            levelSelectionArea.setHgap(10);
            levelSelectionArea.setVgap(10);
            setLevelsUnlocked(getPageNumber(), 1);

            levelSelectionArea.setAlignment(Pos.CENTER);

            // Transparent dark background
            levelSelectionArea.setBackground(new Background(new BackgroundFill(
                    javafx.scene.paint.Color.rgb(0,0,0,0.1),
                    CornerRadii.EMPTY, Insets.EMPTY)));

            VBox.setVgrow(this, Priority.ALWAYS);

            this.getChildren().addAll(levelSelectionArea,prevPageButton,nextPageButton);

        }

        private void setLevelsUnlocked(int newLevelsUnlocked, int levelPage) {

            if (!(newLevelsUnlocked >= 0)) {
                throw new IllegalArgumentException("Levels unlocked must be between at least 0.");
            }

            if (!(levelPage >= 1)) {
                throw new IllegalArgumentException("Level page must be at least 1.");
            }

            levelsUnlocked = newLevelsUnlocked;

            int levelCounter;
            for (int i = 0; i < 25; i++) {
                levelCounter = (levelPage - 1) * 25 + i;

                Button levelButton = new Button((levelCounter+1) + "");

                if (levelCounter < newLevelsUnlocked) {
                    levelButton.getStyleClass().add("level-button-completed");
                } else if (levelCounter == newLevelsUnlocked) {
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
