package io.github.zhaojingyan.ui.gui.buttons;

import io.github.zhaojingyan.ui.gui.GuiInput;

public class BombButton extends CustomButton {

    
    /**
     * 创建炸弹按钮
     */
    private BombButton(ButtonManager buttonManager) {
        super("");
        setMinWidth(80);
        setOnAction(event -> {
            GuiInput.toggleBombMode();
            buttonManager.refreshAllButtons();
        });
    }

    public static BombButton create(ButtonManager buttonManager) {
        BombButton button = new BombButton(buttonManager);
        buttonManager.addButton(button);
        return button;
    }
    
    @Override
    public void updateAppearance() {
        if (io.github.zhaojingyan.ui.gui.GuiInput.getInstance().isBombMode()) {
            setStyle("-fx-background-color: #ff6666; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-insets: 0; -fx-background-radius: 6; -fx-cursor: hand;");
            setText("Bomb Activated");
        } else {
            setStyle("-fx-background-insets: 0; -fx-background-radius: 6; -fx-cursor: hand;");
            setText("Use Bomb");
        }
    }
}