package starb.client.ui.scenes;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import starb.domain.game.Board;
import starb.client.ui.components.ExpandingPaneGenerator;

public class PuzzleScene extends VBox {

    private PuzzleTopBar topBar;
    private PuzzleUI puzzle;
    private PuzzleBottomBar bottomBar;

    // The Exception is for the .getSytlesheets() method
    public PuzzleScene(Board board, String playerRank) throws Exception{
        // Set the Style
        this.getStylesheets().add(StarbClient.COMMON_STYLESHEET.
                toURI().toURL().toString());

        puzzle = new PuzzleUI(board);
        topBar = new PuzzleTopBar(puzzle, board.getID());

        Pane fillerPane = ExpandingPaneGenerator.newXPPane('v');

        bottomBar = new PuzzleBottomBar(puzzle, playerRank);

        this.getChildren().addAll(topBar, puzzle, fillerPane, bottomBar);



    }


}
