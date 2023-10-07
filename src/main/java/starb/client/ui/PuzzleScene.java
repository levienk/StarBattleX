package starb.client.ui;

import javafx.scene.layout.VBox;
import starb.client.StarbClient;

public class PuzzleScene extends VBox {

    private PuzzleTopBar topBar;

    // The Exception is for the .getSytlesheets() method
    public PuzzleScene() throws Exception{
        // Set the Style
        this.getStylesheets().add(StarbClient.COMMON_STYLESHEET.
                toURI().toURL().toString());

        topBar = new PuzzleTopBar();

        this.getChildren().addAll(topBar);



    }


}
