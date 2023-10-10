package starb.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import starb.client.ui.LevelMenuScene;

import java.io.File;

/**
 * Creates a single window as an example of a Java GUI with a component
 * for drawing.
 */

public class StarbClient extends Application {

    private final static File APPLICATION_ICON = new File
            ("./Assets/Images/StarIcon.png");

    public final static File COMMON_STYLESHEET = new File
            ("./Assets/Stylesheets/commonStyle.css");

    @SuppressWarnings("FieldCanBeLocal")
    private final int WINDOW_WIDTH = 1200;

    @SuppressWarnings("FieldCanBeLocal")

    private final int WINDOW_HEIGHT = 600;

    @SuppressWarnings("FieldCanBeLocal")
    private final String WINDOW_TITLE = "Star Game X";

    public static final Color TEMPLATE_BAR_COLOR = Color.web("#707070");

    /**
     *
     * @param args the command line arguments
     */
    public static void main( String[] args ) {
        // Start the GUI

        launch();
    }

    /** This is very scuffed, apparently I can't just put a 'final' without initializing the damn
     *  thing. I'm not sure how to fix this, but I'll look into it later.
     *
     */
    static Stage mainStage;


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
        primaryStage.setScene( new Scene( new LevelMenuScene() ) );
        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);
        primaryStage.setTitle(WINDOW_TITLE);

        // Minimum size for PuzzleScene to load properly (don't go smaller than this)
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(450);

        primaryStage.getIcons().add(new Image(APPLICATION_ICON.toURI().toURL().toString()));
        primaryStage.show();

        mainStage = primaryStage;

    }

    public static void switchScene(Scene scene) {

        mainStage.setScene(scene);
    }
}
