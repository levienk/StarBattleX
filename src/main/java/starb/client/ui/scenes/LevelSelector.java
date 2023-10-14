package starb.client.ui.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

public class LevelSelector extends StackPane {

    public LevelSelector() {

        this.setPadding(new Insets(10,10,10,10));

        Button prevPageButton = new Button("<");
        StackPane.setAlignment(prevPageButton, Pos.CENTER_LEFT);

        Button nextPageButton = new Button(">");
        StackPane.setAlignment(nextPageButton, Pos.CENTER_RIGHT);

        GridPane levelSelectionArea = new GridPane();

        levelSelectionArea.setPrefWidth(400);

        GridPane.setRowSpan(levelSelectionArea, 5);
        GridPane.setColumnSpan(levelSelectionArea, 5);
        levelSelectionArea.setHgap(10);
        levelSelectionArea.setVgap(10);


        levelSelectionArea.setAlignment(Pos.CENTER);

        for (int i = 0; i < 25; i++) {

            Button levelButton = new Button((i+1) + "");

            if (i < 3) {
                levelButton.getStyleClass().add("level-button-completed");
            } else if (i == 3) {
                levelButton.getStyleClass().add("level-button-new");
            } else {
                levelButton.getStyleClass().add("level-button-locked");
            }

            levelButton.setPrefHeight(70);
            levelButton.setPrefWidth(70);

            levelSelectionArea.add(levelButton, i % 5, i / 5);

        }

        // Transparent dark background
        levelSelectionArea.setBackground(new Background(new BackgroundFill(
                javafx.scene.paint.Color.rgb(0,0,0,0.1),
                CornerRadii.EMPTY, Insets.EMPTY)));

        VBox.setVgrow(this, Priority.ALWAYS);

        this.getChildren().addAll(levelSelectionArea,prevPageButton,nextPageButton);



    }


}
