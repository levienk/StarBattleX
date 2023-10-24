package starb.client.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import starb.client.EventListener;
import starb.client.ui.scenes.PuzzleScene;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static starb.client.SceneSwitcher.setScene;
import static starb.client.SceneSwitcher.setStage;

/**
 * Creates a single window as an example of a Java GUI with a component
 * for drawing.
 */

public class StarbClient extends Application {

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
    private final int WINDOW_WIDTH = 1200;

    @SuppressWarnings("FieldCanBeLocal")

    private final int WINDOW_HEIGHT = 600;

    @SuppressWarnings("FieldCanBeLocal")
    private final String WINDOW_TITLE = "Star Game X";

    public static final Color TEMPLATE_BAR_COLOR = Color.web("#707070");

    private static List<EventListener> eventListeners;

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

        eventListeners = new ArrayList<>();

        // TODO - Randy, we need a way to initialize the parameters for when PuzzleScene
        // TODO - is first loaded using SceneSwitcher
        setStage(primaryStage);

        PuzzleScene puzzleScene = new PuzzleScene("Temporary Rank");
        Scene primaryScene = new Scene(puzzleScene);

        //setScene(primaryStage)
        primaryStage.setScene(primaryScene);
        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);
        primaryStage.setTitle(WINDOW_TITLE);

        // Minimum size for PuzzleScene to load properly (don't go smaller than this)
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(600);

        primaryStage.getIcons().add(new Image(APPLICATION_ICON.toURI().toURL().toString()));
        primaryStage.show();

        //TODO delete this later.
        /*
         *  This is a test to see if the event system works.
         *  The code creates a new thread which runs separately from the application.
         *  It will wait for some time before calling the runLater method which
         *  is necessary if we want to dynamically update the GUI.
         *
         *  To update the GUI, we need to call the publishEvent method which will
         *  call the onEvent method of all the event listeners.
         *
         *  The event listeners will then do whatever they need to do.
         */
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(3000);
                Platform.runLater(() -> publishEvent("setLevelsUnlocked", 17));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t.start();
    }

    public static void addEventListener(EventListener listener) {
        eventListeners.add(listener);
    }

    public void publishEvent(String event, Object... args) {
        for(EventListener listener : eventListeners) {
            listener.onEvent(event, args);
        }
    }
}
