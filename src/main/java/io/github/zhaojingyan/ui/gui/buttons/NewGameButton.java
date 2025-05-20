package io.github.zhaojingyan.ui.gui.buttons;

import io.github.zhaojingyan.ui.gui.GuiInput;

public class NewGameButton extends CustomButton {

    private NewGameButton(String gameName, ButtonManager buttonManager) {
        super(gameName);
        setMinWidth(80);

        setOnAction(event -> {
            GuiInput.handleButtonInput(gameName);
            buttonManager.refreshAllButtons();
        });
    }

    public static NewGameButton create(String gameName, ButtonManager buttonManager) {
        NewGameButton button = new NewGameButton(gameName, buttonManager);
        buttonManager.addButton(button);
        return button;
    }

    @Override
    public void updateAppearance() {
        // 保持和 GameSwitchButton 一致的圆角和基础样式
        setStyle("-fx-background-insets: 0; -fx-background-radius: 6; -fx-cursor: hand;");
    }
}