package starb.client.ui.scenes;

import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import starb.client.ui.StarbClient;
import starb.client.ui.components.ExpandingPaneGenerator;

public class PuzzleScene extends VBox {

    private PuzzleTopBar topBar;
    private PuzzleUI puzzle;
    private PuzzleBottomBar bottomBar;

    // The Exception is for the .getSytlesheets() method
    public PuzzleScene() throws Exception{
        // Set the Style
        this.getStylesheets().add(StarbClient.COMMON_STYLESHEET.
                toURI().toURL().toString());

        topBar = new PuzzleTopBar();
        puzzle = new PuzzleUI();

        Pane fillerPane = ExpandingPaneGenerator.newXPPane('v');

        bottomBar = new PuzzleBottomBar(puzzle);

        this.getChildren().addAll(topBar, puzzle, fillerPane, bottomBar);



    }


}
