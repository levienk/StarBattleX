package starb.client.ui;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import static starb.client.ExpandingPaneGenerator.newXPPane;
public class LevelSelector extends AnchorPane {

    public LevelSelector() {

        Button prevPageButton = new Button("<");
        AnchorPane.setLeftAnchor(prevPageButton, 0.0);



        Button nextPageButton = new Button(">");
        AnchorPane.setRightAnchor(nextPageButton, 0.0);

        VBox.setVgrow(this, Priority.ALWAYS);

        this.getChildren().addAll(prevPageButton, newXPPane('h'),nextPageButton);



    }


}
