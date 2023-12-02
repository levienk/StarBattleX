package starbattle.client.ui.components;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class Title extends Label {
    public Title (String text) {
        super(text);

        // Copied from commonStyle.css
        // TODO integrate with commonStyle.css
        setFont(new Font("Comic Sans MS", 20));
        setStyle("-fx-font-weight: bold");
        setStyle("-fx-text-fill: #ffffff");
    }
}
