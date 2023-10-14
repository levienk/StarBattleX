package starb.client.ui.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import starb.client.ui.components.UIBar;

import java.io.File;

public class PuzzleBottomBar extends UIBar {

    private static final File STAR_IMAGE_FILE = new File("image/star_white.png");
    private final ImageView starImage;

    private static final File DOT_IMAGE_FILE = new File("image/dot_white.png");
    private final ImageView dotImage;

    public PuzzleBottomBar() {

        super();

        this.setAlignment(Pos.CENTER);

        // Create the star and dot images
        try {
            starImage = new ImageView(STAR_IMAGE_FILE.toURI().toURL().toString());
            dotImage = new ImageView(DOT_IMAGE_FILE.toURI().toURL().toString());
        } catch(Exception e) {
            String message = "Unable to load image: " + STAR_IMAGE_FILE;
            System.err.println(message);
            System.err.println(e.getMessage());
            throw new RuntimeException(message);
        }

        // Label for the current player rank.
        // TODO Could not get to work with stylesheet.
        // TODO Implement rank system.
        Label playerRank = new Label("Rank: Beginner");
        playerRank.setFont(new Font("Arial", 20));
        playerRank.setStyle("-fx-font-weight: bold");
        playerRank.setStyle("-fx-text-fill: #ffffff");

        Pane fillerPane = new Pane();
        HBox.setHgrow(fillerPane, Priority.ALWAYS);

        starImage.setFitHeight(30);
        starImage.setFitWidth(30);
        Button starButton = new Button();
        starButton.setGraphic(starImage);
        starButton.setAlignment(Pos.CENTER);

        dotImage.setFitHeight(30);
        dotImage.setFitWidth(30);
        Button dotButton = new Button();
        dotButton.setGraphic(dotImage);
        dotButton.setAlignment(Pos.CENTER);

        this.getChildren().addAll(playerRank, fillerPane, starButton, dotButton);
    }
}
