package starbattle.client.ui.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import starbattle.client.ui.components.ExpandingPaneGenerator;
import starbattle.client.ui.components.Title;
import starbattle.client.ui.components.UIBar;

import java.io.File;

public class PuzzleBottomBar extends UIBar {

    private static final File STAR_IMAGE_FILE = new File("Assets/Images/star_white.png");

    private static final File DOT_IMAGE_FILE = new File("Assets/Images/dot_white.png");

    public PuzzleBottomBar(PuzzleUI ui, String playerRank) {

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
        Label playerRankLabel = new Title("Rank: " + playerRank);

        Pane fillerPane = ExpandingPaneGenerator.newXPPane('h');

        // Group buttons so only one can be selected at a time
        ToggleGroup buttonGroup = new ToggleGroup();

        // Create star button
        starImage.setFitHeight(30);
        starImage.setFitWidth(30);
        RadioButton starButton = new RadioButton();
        starButton.getStyleClass().remove("radio-button");
        starButton.getStyleClass().add("toggle-button");
        starButton.setGraphic(starImage);
        starButton.setAlignment(Pos.CENTER);
        starButton.setSelected(true);
        starButton.setToggleGroup(buttonGroup);
        starButton.setOnAction( e -> {ui.setSelectionType("star");});

        // Create dot button
        dotImage.setFitHeight(30);
        dotImage.setFitWidth(30);
        RadioButton dotButton = new RadioButton();
        dotButton.getStyleClass().remove("radio-button");
        dotButton.getStyleClass().add("toggle-button");
        dotButton.setGraphic(dotImage);
        dotButton.setAlignment(Pos.CENTER);
        dotButton.setToggleGroup(buttonGroup);
        dotButton.setOnAction( e -> {ui.setSelectionType("dot");});

        // Create remove button
        RadioButton removeButton = new RadioButton("Remove");
        removeButton.getStyleClass().remove("radio-button");
        removeButton.getStyleClass().add("toggle-button");
        removeButton.setToggleGroup(buttonGroup);
        removeButton.setOnAction( e -> {ui.setSelectionType("");});
        removeButton.setMinWidth(102); // This may need adjusting based on font

        this.getChildren().addAll(playerRankLabel, fillerPane, starButton, dotButton, removeButton);
    }
}
