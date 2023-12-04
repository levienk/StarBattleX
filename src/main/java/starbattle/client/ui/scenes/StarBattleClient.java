package starbattle.client.ui.scenes;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import starbattle.domain.DatabaseLoader;
import starbattle.domain.user.User;

import java.io.File;

import static starbattle.client.ui.scenes.SceneSwitcher.setScene;
import static starbattle.client.ui.scenes.SceneSwitcher.setStage;

/**
 * Creates a single window as an example of a Java GUI with a component
 * for drawing.
 */

public class StarBattleClient extends Application {

    /**
     *  GENERAL GUIDELINES FOR THIS PROJECT:
     * <p>
     *  ALl adjustable values must be derived from here.
     *  All assets should be stored the Assets folder.
     *  Assets include:
     *  - Images
     *  - CSS Stylesheets
     *  - Fonts
     *  - Sound Effects
     *  - Anything that isn't code basically.
     * <p>
     *  This can be changed should there need to be.
     */
    private final static File APPLICATION_ICON = new File
            ("./Assets/Images/StarIcon.png");

    public final static File COMMON_STYLESHEET = new File
            ("./Assets/Stylesheets/commonStyle.css");

    @SuppressWarnings("FieldCanBeLocal")
    private final int WINDOW_WIDTH = 650;

    @SuppressWarnings("FieldCanBeLocal")

    private final int WINDOW_HEIGHT = 650;

    @SuppressWarnings("FieldCanBeLocal")
    private final String WINDOW_TITLE = "StarBattle X";

    public static final Color TEMPLATE_BAR_COLOR = Color.web("#707070");

    /**
     *
     * @param args the command line arguments
     */
    public static void main( String[] args ) {
        // Start the GUI

        launch();
    }

    /**
     *
     *  Initialize the GUI and its assets.
     *
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     *
     * @throws Exception if something goes wrong.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        setStage(primaryStage);

        // Load the initial user
        User user = DatabaseLoader.getUser();

        // Load the next puzzle
        int nextLevel = user.getNextPuzzle();
        if (nextLevel == -1) nextLevel = 1; // Wrap to board 1 if all boards are complete
        setScene(PuzzleScene.class, DatabaseLoader.getBoard(nextLevel), user.getPlayerRank());

        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);
        primaryStage.setTitle(WINDOW_TITLE);

        // Minimum size for PuzzleScene to load properly (don't go smaller than this)
        primaryStage.setMinHeight(650);
        primaryStage.setMinWidth(650);

        primaryStage.getIcons().add(new Image(APPLICATION_ICON.toURI().toURL().toString()));
        primaryStage.show();
    }

}
