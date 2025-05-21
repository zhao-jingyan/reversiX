package io.github.zhaojingyan.ui.gui.buttons;

import io.github.zhaojingyan.ui.gui.GuiInput;
import javafx.scene.control.Button;

public final class PassButton extends Button {
    public PassButton() {
        super("pass");
        setMinWidth(80);
        setStyle(
                "-fx-font-size: 13px; -fx-font-weight: bold; -fx-font-family: 'monospaced', 'Consolas', 'Menlo', 'Courier', monospace; -fx-background-insets: 0; -fx-background-radius: 6; -fx-cursor: hand;");
        setVisible(false);
        setOnAction(event -> {
            GuiInput.handleButtonInput("pass");
        });
    }
}
