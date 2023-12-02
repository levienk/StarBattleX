package starbattle.client.ui.components;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

final public class ExpandingPaneGenerator {

    public static @NotNull Pane newXPPane(char type) {

        Pane pane = new Pane();

        if (type == 'h' || type == 'H') {
            HBox.setHgrow(pane, Priority.ALWAYS);
        } else if (type == 'v' || type == 'V') {
            VBox.setVgrow(pane, Priority.ALWAYS);
        }

        return pane;
    }



}
