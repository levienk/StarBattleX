package starb.client.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PuzzleBottomBar extends HBox{
    // TODO Related to LevelMenuScene TOP_BAR_COLOR. Which class to take constant from?
    private static final Color INTERFACE_COLOR = Color.web("#707070");
    public PuzzleBottomBar() {
        this.setPadding(new Insets(10,10,10,10));

        Background background = new Background(new BackgroundFill(INTERFACE_COLOR,
                CornerRadii.EMPTY, Insets.EMPTY));
        this.setBackground(background);

        this.setAlignment(Pos.CENTER);

        // Label for the current player rank.
        // TODO Could not get to work with stylesheet.
        // TODO Implement rank system.
        Label playerRank = new Label("Rank: Beginner");
        playerRank.setFont(new Font("Arial", 20));
        playerRank.setStyle("-fx-font-weight: bold");
        playerRank.setStyle("-fx-text-fill: #ffffff");

        this.getChildren().addAll(playerRank);
    }
}
