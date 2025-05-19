package io.github.zhaojingyan.ui.gui.buttons;

/**
 * 炸弹按钮，用于Gomoku游戏模式
 */
public class BombButton extends CustomButton {
    private boolean bombMode = false;
    
    /**
     * 创建炸弹按钮
     */
    public BombButton() {
        super("使用炸弹");
        setMinWidth(80);
        updateButtonAppearance();
    }
    
    /**
     * 切换炸弹模式
     * @return 返回当前模式
     */
    public boolean toggleBombMode() {
        bombMode = !bombMode;
        updateButtonAppearance();
        return bombMode;
    }
    
    /**
     * 更新按钮外观
     */
    private void updateButtonAppearance() {
        if (bombMode) {
            setStyle("-fx-background-color: #ff6666; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-insets: 0; -fx-background-radius: 6; -fx-cursor: hand;");
            setText("炸弹模式激活");
        } else {
            setStyle("-fx-background-insets: 0; -fx-background-radius: 6; -fx-cursor: hand;");
            setText("使用炸弹");
        }
    }
    
    /**
     * 获取当前是否为炸弹模式
     * @return 是否为炸弹模式
     */
    public boolean isBombMode() {
        return bombMode;
    }
}
