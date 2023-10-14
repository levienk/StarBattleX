package starb.client.ui.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import starb.client.ui.components.ExpandingPaneGenerator;
import starb.client.ui.components.Title;
import starb.client.ui.components.UIBar;

import java.io.File;

public class PuzzleBottomBar extends UIBar {

    private static final File STAR_IMAGE_FILE = new File("image/star_white.png");

    private static final File DOT_IMAGE_FILE = new File("image/dot_white.png");

    public PuzzleBottomBar() {

        super();

        this.setAlignment(Pos.CENTER);

        // Create the star and dot images
        ImageView starImage;
        ImageView dotImage;
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
        // TODO Implement rank system.
        Label playerRank = new Title("Rank: Beginner");

        Pane fillerPane = ExpandingPaneGenerator.newXPPane('h');

        // Create star button
        starImage.setFitHeight(30);
        starImage.setFitWidth(30);
        Button starButton = new Button();
        starButton.setGraphic(starImage);
        starButton.setAlignment(Pos.CENTER);

        // Create dot button
        dotImage.setFitHeight(30);
        dotImage.setFitWidth(30);
        Button dotButton = new Button();
        dotButton.setGraphic(dotImage);
        dotButton.setAlignment(Pos.CENTER);

        this.getChildren().addAll(playerRank, fillerPane, starButton, dotButton);
    }
}
