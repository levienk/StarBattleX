package starbattle.client.ui.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import starbattle.client.ui.components.CustomAlert;
import starbattle.client.ui.components.UIBar;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;

import static starbattle.client.ui.components.ExpandingPaneGenerator.newXPPane;
import static starbattle.client.ui.scenes.SceneSwitcher.setScene;
import static starbattle.client.ui.scenes.StarBattleClient.gameStatistics;

public class SettingsMenu extends VBox {

    private final Button eraseAllProgressButton;

    @SuppressWarnings("FieldCanBeLocal")
    private final String ERASE_ALL_PROGRESS_LABEL = "Erase All Progress";

    private int eraseAllProgressTimer;

    public SettingsMenu() throws Exception {

        this.getStylesheets().add(StarBattleClient.COMMON_STYLESHEET.
                toURI().toURL().toString());

        HBox topBar = new UIBar();
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            try {
                setScene(LevelMenuScene.class);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        topBar.getChildren().addAll(new Label("Settings"), newXPPane('h'),backButton);
        Button premiumButton = new Button("✨ Purchase Premium ✨");
        premiumButton.getStyleClass().add("level-button-new");
        premiumButton.setOnAction(e -> {
            try {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
                }
            } catch (Exception ex) {
                premiumButton.setVisible(false);
            }
        });


        HBox bottomBar = new UIBar();
        eraseAllProgressButton = new Button(ERASE_ALL_PROGRESS_LABEL);
        eraseAllProgressButton.getStyleClass().add("dangerous-button");
        eraseAllProgressButton.setOnAction(e -> eraseProgressAction());
        eraseAllProgressTimer = 0;

        bottomBar.getChildren().addAll(eraseAllProgressButton,newXPPane('h'),premiumButton);

        VBox mainPane = new VBox();
        VBox.setVgrow(mainPane, Priority.ALWAYS);

        HBox dualSection = new HBox();

        GridPane developerCredits = new DeveloperCredits();
        VBox metaInfo = new MetaCredits();

        dualSection.getChildren().addAll(developerCredits,metaInfo);
        mainPane.getChildren().add(dualSection);

        this.getChildren().addAll(topBar, mainPane, bottomBar);
    }

    private void eraseProgressAction() {
        eraseAllProgressTimer++;
        switch (eraseAllProgressTimer) {
            case 1:
                eraseAllProgressButton.setText("Are you sure?");
                break;
            case 2:
                eraseAllProgressButton.setText("You cannot undo this.");
                break;
            case 3:
                eraseAllProgressButton.setText("Last chance to back out.");
                break;
            default:
                if (new File("Assets/User/userID.txt").delete()) {
                    CustomAlert popup = new CustomAlert("User Data Deleted",
                            "The application will now close.");
                    new File("Assets/User/stats.json").delete();
                    popup.getCancelButton().setVisible(false);
                    popup.showAndWait();
                    System.exit(0);
                } else {
                    CustomAlert popup = new CustomAlert("Error",
                            "An error occurred while deleting user data.");
                    popup.getCancelButton().setVisible(false);
                    popup.showAndWait();
                }
        }
        eraseAllProgressButton.setDisable(true);

        Thread eraseBlockTimer = new Thread(() -> {

           try {
               Thread.sleep(2000);
               eraseAllProgressButton.setDisable(false);
           } catch (Exception e) {}
        });

        eraseBlockTimer.start();
    }

    private static class DeveloperCredits extends GridPane {

        private DeveloperCredits() {

            this.add(new Profile(
                    "Daniel Ma", """
                    MongoDB and
                    REST API Architect
                    """,
                    "Assets/Images/Credits/Daniel Ma.png"), 0, 0);
            this.add(new Profile(
                    "Kade Levin",
                    """
                            Puzzle Game
                            Logic Architect
                            """,
                    "Assets/Images/Credits/Kade Levin.png"), 1, 0);
            this.add(new Profile(
                    "Max Lopez",
                    """
                            Quality Tester and
                            Project Architect
                            """,
                    "Assets/Images/Credits/Max Lopez.png"), 0, 1);
            this.add(new Profile(
                    "Randy N.",
                    """
                            Client-side Engineer
                            and UI Designer
                            """,
                    "Assets/Images/Credits/Randy Nguyen.jpg"), 1, 1);

            this.setPadding(new Insets(10));
            this.setHgap(-10);
            this.setVgap(-10);

            this.setBackground(new Background(new BackgroundFill(
                    javafx.scene.paint.Color.rgb(0,0,0,0.1),
                    CornerRadii.EMPTY, new Insets(10))));

        }

        private static class Profile extends VBox {

            Profile(String personName, String description, String iconDir) {
                Image icon;
                try {
                    icon = new Image(new File(iconDir).toURI().toURL().toString());
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                ImageView iconView = new ImageView(icon);
                iconView.setFitWidth(112);
                iconView.setFitHeight(112);

                Label nameLabel = new Label(personName);
                nameLabel.setPadding(new Insets(0, 10, 0, 10));

                Text descriptionText = new Text(description);
                VBox descriptionTextContainer = new VBox();
                descriptionTextContainer.getChildren().add(descriptionText);
                descriptionTextContainer.setPadding(new Insets(0, 10, 10, 10));
                descriptionText.getStyleClass().add("credits-text");

                this.setBackground(new Background(new BackgroundFill(
                        javafx.scene.paint.Color.rgb(0,0,0,0.1),
                        CornerRadii.EMPTY, new Insets(10))));
                this.setPadding(new Insets(20, 10, 0, 10));
                this.setSpacing(10);
                this.setAlignment(Pos.TOP_CENTER);
                this.getChildren().addAll(iconView, nameLabel, descriptionTextContainer);


            }

        }

    }

    private static class GameStatisticsPanel extends VBox {

        private GameStatisticsPanel() throws Exception {

            Label timePlayedLabel = new Label("Time Played: " +
                    gameStatistics.getTimePlayedDuration()
                            .toString().substring(2)
                            .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                            .toLowerCase());


            Label timesOpenedLabel =
                    new Label("You opened the Game: " +
                            gameStatistics.getTimesOpened() + " times.");

            Label numOfPuzzlesCompletedLabel = new Label("Puzzles Completed: " +
                    gameStatistics.getPuzzlesCompleted());

            Label userIDLabel = new Label("User ID:\n" + gameStatistics.getUserID());


            this.setBackground(new Background(new BackgroundFill(
                    javafx.scene.paint.Color.rgb(0,0,0,0.1),
                    CornerRadii.EMPTY, new Insets(10))));
            this.setPadding(new Insets(10));
            this.setSpacing(-10);

            this.getChildren().addAll(
                    timePlayedLabel, timesOpenedLabel, numOfPuzzlesCompletedLabel, userIDLabel);

            this.getChildren().forEach(node -> {
                node.getStyleClass().add("label-mini");
                ((Label) node).setBackground(new Background(new BackgroundFill(
                        javafx.scene.paint.Color.rgb(0,0,0,0.1),
                        CornerRadii.EMPTY, new Insets(10))));
                ((Label) node).setPadding(new Insets(20));
            });


        }
    }

    private static class MetaCredits extends VBox {

        private MetaCredits() throws Exception {

            VBox gameStats = new GameStatisticsPanel();

            Label thanks = new Label("This game is made as part of our class project. This " +
                    "project is made through the\ncollaboration of all four members of the team. " +
                    "We also thank Dr. Wolff for his guidance and for the opportunity for us to work together." +
                    "\n\nThank you for playing!");

            thanks.getStyleClass().add("label-mini");

            thanks.setWrapText(true);

            thanks.setPadding(new Insets(10));

            this.setPadding(new Insets(10));

            this.setBackground(new Background(new BackgroundFill(
                    javafx.scene.paint.Color.rgb(0,0,0,0.1),
                    CornerRadii.EMPTY, new Insets(10))));

            this.getChildren().addAll(gameStats,thanks);




        }


    }

}