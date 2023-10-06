package starb.client.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import starb.client.StarbClient;

public class LevelMenuScene extends VBox {

    final Color topBarColor = Color.web("#707070");

    public LevelMenuScene() throws Exception {

        // Set the Style
        this.getStylesheets().add(StarbClient.COMMON_STYLESHEET.
                        toURI().toURL().toString());

        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10,10,10,10));
        Background topBarBackground = new Background(
                new BackgroundFill(topBarColor,
                        CornerRadii.EMPTY, Insets.EMPTY));
        topBar.setBackground(topBarBackground);

        Pane fillerPane = new Pane();
        HBox.setHgrow(fillerPane, Priority.ALWAYS);

        Button backButton = new Button("Back");

        topBar.getChildren().addAll(fillerPane, backButton);

        this.getChildren().addAll(topBar);

    }


}
