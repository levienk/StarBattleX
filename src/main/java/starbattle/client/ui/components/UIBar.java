package starbattle.client.ui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;

import static starbattle.client.StarBattleClient.TEMPLATE_BAR_COLOR;

/**
 *  UI Bar template generator for easy use.
 */
public class UIBar extends HBox {

    /**
     * Generates a new UI Bar template with preconfigured properties.
     */
    public UIBar() {
        this.setPadding(new Insets(10,10,10,10));
        Background barBG = new Background(
                new BackgroundFill(TEMPLATE_BAR_COLOR,
                        CornerRadii.EMPTY, Insets.EMPTY));
        this.setBackground(barBG);
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
    }

}
