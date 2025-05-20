package io.github.zhaojingyan.ui.gui.buttons;

import io.github.zhaojingyan.ui.gui.GuiInput;
import javafx.scene.control.Button;
/**
 * 退出游戏按钮，样式统一，便于事件绑定
 */
public class QuitButton extends Button {
    public QuitButton() {
        super("Quit");
        setMinWidth(80);
        setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-font-family: 'monospaced', 'Consolas', 'Menlo', 'Courier', monospace; -fx-background-insets: 0; -fx-background-radius: 6; -fx-cursor: hand;");
        setOnAction(event -> {
            GuiInput.handleButtonInput("quit");
        });
    }
}
