package io.github.zhaojingyan.ui.gui.buttons;

public class BombButton extends CustomButton {
    
    /**
     * 创建炸弹按钮
     */
    private BombButton(ButtonManager buttonManager) {
        super("@");
        setMinWidth(80);
        setOnAction(event -> {
            buttonManager.toggleBombMode();
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
        if (ButtonManager.getInstance().isBombMode()) {
            setStyle("-fx-background-color: #ff6666; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-insets: 0; -fx-background-radius: 6; -fx-cursor: hand;");
            setText("炸弹模式激活");
        } else {
            setStyle("-fx-background-insets: 0; -fx-background-radius: 6; -fx-cursor: hand;");
            setText("使用炸弹");
        }
    }
}