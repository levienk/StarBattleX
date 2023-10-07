package starb.client.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PuzzleTopBar extends HBox{
    // TODO Related to LevelMenuScene TOP_BAR_COLOR. Which class to take constant from?
    final Color INTERFACE_COLOR = Color.web("#707070");
    public PuzzleTopBar() {
        this.setPadding(new Insets(10,10,10,10));

        Background background = new Background(new BackgroundFill(INTERFACE_COLOR,
                CornerRadii.EMPTY, Insets.EMPTY));
        this.setBackground(background);

        this.setAlignment(Pos.CENTER);

        Pane fillerPane = new Pane();
        HBox.setHgrow(fillerPane, Priority.ALWAYS);

        // Label for the current level the user is on. TODO Could not get to work with stylesheet.
        Label levelName = new Label("Level 1");
        levelName.setFont(new Font("Arial", 20));
        levelName.setStyle("-fx-font-weight: bold");
        levelName.setStyle("-fx-text-fill: #ffffff");

        // Level Menu Button
        Button levelMenuButton = new Button("Level Menu");

        this.getChildren().addAll(levelName, fillerPane, levelMenuButton);
    }
}
