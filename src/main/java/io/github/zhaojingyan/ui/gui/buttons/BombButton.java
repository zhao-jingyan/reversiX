package io.github.zhaojingyan.ui.gui.buttons;

import io.github.zhaojingyan.ui.gui.GuiInput;
import javafx.scene.control.Button;

public class BombButton extends Button {

    /**
     * 创建炸弹按钮
     */
    public BombButton() {
        super("");
        setMinWidth(80);
        setStyle("-fx-font-size: 13px; -fx-background-color: #eee; -fx-text-fill: #333; -fx-font-weight: bold; -fx-font-family: 'monospaced', 'Consolas', 'Menlo', 'Courier', monospace; -fx-background-insets: 0; -fx-background-radius: 6; -fx-cursor: hand;");
        setText("Use Bomb");
        setVisible(false);
        setOnAction(event -> {
            GuiInput.toggleBombMode();
            updateAppearance();
        });
    }

    public void updateAppearance() {
        if (GuiInput.getInstance().isBombMode()) {
            setStyle("-fx-font-size: 13px; -fx-background-color: #ff6666; -fx-text-fill: #fff; -fx-font-weight: bold; -fx-font-family: 'monospaced', 'Consolas', 'Menlo', 'Courier', monospace; -fx-background-insets: 0; -fx-background-radius: 6; -fx-cursor: hand;");
            setText("Bomb Activated");
        } else {
            setStyle("-fx-font-size: 13px; -fx-background-color: #eee; -fx-text-fill: #333; -fx-font-weight: bold; -fx-font-family: 'monospaced', 'Consolas', 'Menlo', 'Courier', monospace; -fx-background-insets: 0; -fx-background-radius: 6; -fx-cursor: hand;");
            setText("Use Bomb");
        }
    }
}