package starb.client.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class LevelSelector extends StackPane {

    public LevelSelector() {

        this.setPadding(new Insets(10,10,10,10));

        Button prevPageButton = new Button("<");
        StackPane.setAlignment(prevPageButton, Pos.CENTER_LEFT);

        Button nextPageButton = new Button(">");
        StackPane.setAlignment(nextPageButton, Pos.CENTER_RIGHT);

        GridPane levelSelectionArea = new GridPane();
        levelSelectionArea.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        // Decrease the width of the levelSelectionArea by a bit
        levelSelectionArea.setMaxWidth(1000);

        // ._.

        GridPane.setRowSpan(levelSelectionArea, 5);
        GridPane.setColumnSpan(levelSelectionArea, 5);

        levelSelectionArea.setBackground(new Background(
                new BackgroundFill(Color.web("#40a0ffc0"),
                CornerRadii.EMPTY, Insets.EMPTY)));
        levelSelectionArea.setGridLinesVisible(true);


        VBox.setVgrow(this, Priority.ALWAYS);

        this.getChildren().addAll(levelSelectionArea,prevPageButton,nextPageButton);



    }


}
