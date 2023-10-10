package starb.client;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import org.jetbrains.annotations.NotNull;

import static starb.client.StarbClient.TEMPLATE_BAR_COLOR;

/**
 *  UI Bar template generator for easy use.
 */
public class UIBarGenerator {

    /**
     * Generates a new UI Bar with the default color.
     * @return a new UI Bar.
     */
    public static @NotNull HBox newUIBar() {

        HBox barObject = new HBox();

        barObject.setPadding(new Insets(10,10,10,10));
        Background barBG = new Background(
                new BackgroundFill(TEMPLATE_BAR_COLOR,
                        CornerRadii.EMPTY, Insets.EMPTY));
        barObject.setBackground(barBG);

        return barObject;
    }
}
