package starbattle.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.data.annotation.Transient;
import starbattle.client.ui.components.CustomAlert;
import starbattle.client.ui.scenes.PuzzleScene;
import starbattle.domain.DatabaseLoader;
import starbattle.domain.user.User;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;

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
    public final static File APPLICATION_ICON = new File
            ("./Assets/Images/StarIcon.png");

    public final static File COMMON_STYLESHEET = new File
            ("./Assets/Stylesheets/commonStyle.css");

    private static final String STATS_FILE_PATH = "Assets/User/stats.json";

    @SuppressWarnings("FieldCanBeLocal")
    private final int WINDOW_WIDTH = 650;

    @SuppressWarnings("FieldCanBeLocal")

    private final int WINDOW_HEIGHT = 650;

    @SuppressWarnings("FieldCanBeLocal")
    private final String WINDOW_TITLE = "StarBattle X";

    public static final Color TEMPLATE_BAR_COLOR = Color.web("#707070");

    public static GameStatistics gameStatistics;
    private static Thread gameStatisticsThread;

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
        // The Application should not start if the user cannot be loaded.
        // for whatever reason.

        User user = null;
        try {
            user = DatabaseLoader.getUser();
        } catch (Exception e) {


            primaryStage = new CustomAlert("Server Connection Error",
                    "Database not connected.\nIs it running?");

            primaryStage.showAndWait();

            System.exit(-1);
        }

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

        gameStatistics = GameStatistics.load();
        gameStatisticsThread = new Thread(gameStatistics);

        gameStatisticsThread.start();

        primaryStage.show();
    }

    @Override
    public void stop() throws IOException {

        gameStatisticsThread.interrupt();
        gameStatistics.save();
    }

    public static Image getGameIcon() {
        try {
            return new Image(APPLICATION_ICON.toURI().toURL().toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class GameStatistics implements Runnable {

        @Transient
        private static ObjectMapper mapper = new ObjectMapper();

        private int timePlayed; // 1 = 0.1 seconds
        private int timesOpened;

        private GameStatistics() {
            timePlayed = 0;
            timesOpened = 0;
        }

        public static GameStatistics load() throws Exception {

            File file = new File(STATS_FILE_PATH);

            if (file.exists() && file.length() == 0) {
                // Delete the file
                file.delete();
            }

            if (!file.exists()) {
                GameStatistics stats = new GameStatistics();
                PrintWriter writer = new PrintWriter(file);
                writer.print(mapper.writeValueAsString(stats));
                writer.close();
                return stats;
            }

            return mapper.readValue(file, GameStatistics.class);
        }

        public void save() throws IOException {

            try (PrintWriter writer = new PrintWriter(STATS_FILE_PATH)) {

                writer.print(mapper.writeValueAsString(this));
            }

        }

        public int getTimePlayed() {
            return timePlayed;
        }


        @JsonIgnore
        public Duration getTimePlayedDuration() {
            return Duration.ofMillis(timePlayed * 100L);
        }

        @JsonIgnore
        public int getPuzzlesCompleted() throws Exception {
            return DatabaseLoader.getUser().getCompleted().size();
        }

        @JsonIgnore
        public String getUserID() {
            try {
                return DatabaseLoader.getUser().getId();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public int getTimesOpened() {
            return timesOpened;
        }

        @Override
        public void run() {

            timesOpened++;

            while(true) {
                try {
                    Thread.sleep(100);
                    timePlayed++;
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

}
