package starbattle.client.ui.scenes;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import starbattle.client.ui.components.CustomAlert;
import starbattle.client.ui.components.UIBar;

import java.awt.*;
import java.io.File;
import java.net.URI;

import static starbattle.client.ui.components.ExpandingPaneGenerator.newXPPane;
import static starbattle.client.ui.scenes.SceneSwitcher.setScene;

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
        topBar.getChildren().addAll(newXPPane('h'),backButton);
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

        Pane fillerPane = newXPPane('v');


        this.getChildren().addAll(topBar, fillerPane, bottomBar);
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
}