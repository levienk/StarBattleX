package starb.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import starb.client.ui.PuzzleScene;

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
    private final int WINDOW_WIDTH = 1280;

    @SuppressWarnings("FieldCanBeLocal")

    private final int WINDOW_HEIGHT =720;

    @SuppressWarnings("FieldCanBeLocal")
    private final String WINDOW_TITLE = "Star Game X";

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
        primaryStage.setScene( new Scene( new PuzzleScene() ) );
        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);
        primaryStage.setTitle(WINDOW_TITLE);

        primaryStage.setMinHeight(360);
        primaryStage.setMinWidth(480);

        primaryStage.getIcons().add(new Image(APPLICATION_ICON.toURI().toURL().toString()));
        primaryStage.show();
    }

    public void switchScene(Scene scene) {
        Stage stage = (Stage) scene.getWindow();
        stage.setScene(scene);
    }
}
