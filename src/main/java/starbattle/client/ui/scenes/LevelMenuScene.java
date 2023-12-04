package starbattle.client.ui.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import starbattle.client.ui.components.CustomAlert;
import starbattle.client.ui.components.UIBar;
import starbattle.domain.DatabaseLoader;

import java.io.File;

import static starbattle.client.ui.components.ExpandingPaneGenerator.newXPPane;
import static starbattle.client.ui.scenes.SceneSwitcher.setNewScene;
import static starbattle.client.ui.scenes.SceneSwitcher.setScene;

public class LevelMenuScene extends VBox {

    private static final File SETTINGS_ICON_FILE = new File("Assets/Images/settingsGearIcon.png");

    private final LevelSelector levelSelector;

    private final LevelPageNumberContainer levelPageNumberContainer;

    private final int maxPages;
    private final int maxLevels;

    private int levelPage;

    private int levelsUnlocked;



    public LevelMenuScene() throws Exception {

        levelSelector = new LevelSelector();

        // Sets the maximum number of pages based on number of levels in the
        // database.
        maxLevels = getTotalLevels();
        maxPages = (int) Math.ceil(maxLevels / 25.0);

        System.out.println(DatabaseLoader.getUser().getCompleted().size());

        levelPageNumberContainer = new LevelPageNumberContainer();

        // Add this scene to the event listener.
        updateValuesFromDatabase();

        //getValuesFromDatabase();

        // Set the Style
        this.getStylesheets().add(StarBattleClient.COMMON_STYLESHEET.
                        toURI().toURL().toString());

        HBox topBar = new UIBar();
        HBox bottomBar = new UIBar();
        bottomBar.setSpacing(10);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            try {
                setScene(PuzzleScene.class);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        Button settingsButton = new Button();
        ImageView settingsIcon = new ImageView(SETTINGS_ICON_FILE.toURI().toURL().toString());
        settingsIcon.setFitHeight(30);
        settingsIcon.setFitWidth(30);
        settingsButton.setGraphic(settingsIcon);

        settingsButton.setOnAction(e -> openSettings());


        topBar.getChildren().addAll(newXPPane('h'), backButton);
        bottomBar.getChildren().addAll(newXPPane('h'),
                settingsButton);


        this.getChildren().addAll(topBar, levelSelector, levelPageNumberContainer, bottomBar);

    }

    private void openSettings() {
        CustomAlert popup = new CustomAlert("Settings", "Settings are not yet implemented.");
        popup.show();

    }

    private void changePageNumber(int pg) {

            if (!(pg >= 1 && pg <= maxPages)) {
                return;
            }

            levelPage = pg;
            levelSelector.setLevelsUnlocked(levelsUnlocked, levelPage);
            levelPageNumberContainer.updateLabel(levelPage);
    }

    public void updateValuesFromDatabase() throws Exception {
        levelsUnlocked = DatabaseLoader.getUser().getCompleted().size();

        levelSelector.setLevelsUnlocked(levelsUnlocked, getPageNumber());
    }

    private int getPageNumber() {

        return levelPage;

    }

    private int getTotalLevels() {

        try {
            return DatabaseLoader.getUser().getCompleted().size() +
                    DatabaseLoader.getUser().getInaccessible().size();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

            levelPageNumberLabel.setText(pg + " | " + maxPages);

        }

    }

    private class LevelSelector extends StackPane {

        GridPane levelSelectionArea;

        public LevelSelector() {

            this.setPadding(new Insets(10,10,10,10));

            Button prevPageButton = new Button("<");
            StackPane.setAlignment(prevPageButton, Pos.CENTER_LEFT);
            prevPageButton.setOnAction(e -> changePageNumber(levelPage - 1));

            Button nextPageButton = new Button(">");
            StackPane.setAlignment(nextPageButton, Pos.CENTER_RIGHT);
            nextPageButton.setOnAction(e -> changePageNumber(levelPage + 1));

            levelSelectionArea = new GridPane();

            levelSelectionArea.setPrefWidth(400);

            GridPane.setRowSpan(levelSelectionArea, 5);
            GridPane.setColumnSpan(levelSelectionArea, 5);
            levelSelectionArea.setHgap(10);
            levelSelectionArea.setVgap(10);
            setLevelsUnlocked(1, 1);

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
                if (levelCounter > maxLevels) {
                    break;
                }

                Button levelButton = new Button((levelCounter+1) + "");
                int finalLevelCounter = levelCounter + 1;
                levelButton.setOnAction(e -> {
                   try {
                       setNewScene(PuzzleScene.class,

                   DatabaseLoader.getBoard(finalLevelCounter),
                               DatabaseLoader.getUser().getPlayerRank());
                   } catch (Exception ex) {
                       throw new RuntimeException(ex);
                   }
                });

                if (levelCounter < newLevelsUnlocked) {
                    levelButton.getStyleClass().add("level-button-completed");
                } else if (levelCounter == newLevelsUnlocked) {
                    levelButton.getStyleClass().add("level-button-new");
                } else {
                    levelButton.setOnAction(e -> {});
                    levelButton.getStyleClass().add("level-button-locked");
                }

                levelButton.setPrefHeight(70);
                levelButton.setPrefWidth(70);

                levelSelectionArea.add(levelButton, (i % 5), (i / 5));

            }
        }


    }

}
