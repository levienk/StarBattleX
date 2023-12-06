package starbattle.client.ui.scenes;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import starbattle.client.StarBattleClient;
import starbattle.domain.game.Board;
import starbattle.client.ui.components.ExpandingPaneGenerator;
import starbattle.domain.DatabaseLoader;
import starbattle.domain.user.User;

public class PuzzleScene extends VBox {

    private PuzzleTopBar topBar;
    private PuzzleUI puzzle;
    private PuzzleBottomBar bottomBar;

    // The Exception is for the .getSytlesheets() method
    public PuzzleScene(Board board, String playerRank) throws Exception{
        // Set the Style
        this.getStylesheets().add(StarBattleClient.COMMON_STYLESHEET.
                toURI().toURL().toString());

        puzzle = new PuzzleUI(board);
        topBar = new PuzzleTopBar(puzzle, board.getID());

        Pane fillerPane = ExpandingPaneGenerator.newXPPane('v');

        bottomBar = new PuzzleBottomBar(puzzle, playerRank);

        this.getChildren().addAll(topBar, puzzle, fillerPane, bottomBar);
        User user = DatabaseLoader.getUser();
    }


}
