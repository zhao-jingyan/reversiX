package io.github.zhaojingyan.ui.gui.buttons;

import io.github.zhaojingyan.ui.gui.GuiInput;

/**
 * 退出游戏按钮，样式统一，便于事件绑定
 */
public class QuitButton extends CustomButton {
    private QuitButton(ButtonManager buttonManager) {
        super("Quit");
        setMinWidth(80);
        setStyle("-fx-background-insets: 0; -fx-background-radius: 6; -fx-cursor: hand;");
        setOnAction(event -> {
            // 这里直接处理退出逻辑，比如调用buttonManager.quitGame()等
            GuiInput.handleButtonInput(text);
            buttonManager.refreshAllButtons();
        });
    }

    public static QuitButton create(ButtonManager buttonManager) {
        QuitButton button = new QuitButton(buttonManager);
        buttonManager.addButton(button);
        return button;
    }

    @Override
    public void updateAppearance(){
        setStyle("-fx-background-insets: 0; -fx-background-radius: 6; -fx-cursor: hand;");
    }
}
