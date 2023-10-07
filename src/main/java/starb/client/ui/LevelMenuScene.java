package starb.client.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import starb.client.StarbClient;

import static starb.client.ExpandingPaneGenerator.newXPPane;

public class LevelMenuScene extends VBox {

    final Color templateBarUIColor = Color.web("#707070");

    public LevelMenuScene() throws Exception {

        // Set the Style
        this.getStylesheets().add(StarbClient.COMMON_STYLESHEET.
                        toURI().toURL().toString());

        HBox topBar = templateUIBar();
        HBox bottomBar = templateUIBar();

        Button backButton = new Button("Back");
        Button goodButton = new Button("Good :D");

        Button centerButton = new Button("Centerpiece");
        centerButton.setMinWidth(200);

        topBar.getChildren().addAll(newXPPane('h'), backButton);
        bottomBar.getChildren().addAll(goodButton, newXPPane('h'), centerButton);


        this.getChildren().addAll(topBar, new LevelSelector(), bottomBar);

    }


    /**
     *  Helper method to reduce code duplication by putting common UI elements
     *  for the UI interface bars.
     *
     */
    public HBox templateUIBar() {

        HBox barObject = new HBox();

        barObject.setPadding(new Insets(10,10,10,10));
        Background barBG = new Background(
                new BackgroundFill(templateBarUIColor,
                        CornerRadii.EMPTY, Insets.EMPTY));
        barObject.setBackground(barBG);

        return barObject;
    }

}
