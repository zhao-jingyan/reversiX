package io.github.zhaojingyan.ui.gui.buttons;

public class NewGameButton extends CustomButton {
    private final String gameName;

    private NewGameButton(String gameName, ButtonManager buttonManager) {
        super(gameName);
        this.gameName = gameName;
        setMinWidth(80);

        setOnAction(event -> {
            buttonManager.getString(gameName);
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