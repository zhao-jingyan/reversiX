package io.github.zhaojingyan.ui.gui.buttons;

import io.github.zhaojingyan.ui.gui.GuiInput;
import javafx.scene.control.Button;

public final class CellButton extends Button {
    private final String coord;

    public CellButton(int row, int col) {
        super("");
        coord = String.format("%X%c", row + 1, (char)('A' + col));
        setMinSize(40, 40);
        setMaxSize(40, 40);
        setPrefSize(40, 40);
        setFocusTraversable(false);
        // 关键：去除按钮的凸起/阴影/边框圆角等视觉效果，完全平面
        setStyle("-fx-background-radius: 0; -fx-background-insets: 0; -fx-padding: 0; -fx-border-radius: 0; -fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-box-shadow: none; -fx-effect: none; -fx-background-color: transparent;");
        this.setOnAction(event -> {
            GuiInput.handleButtonInput(coord);
        });
    }
}
