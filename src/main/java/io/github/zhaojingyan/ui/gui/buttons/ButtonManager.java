package io.github.zhaojingyan.ui.gui.buttons;

import java.util.List;

public class ButtonManager {
    private static final ButtonManager INSTANCE = new ButtonManager();
    private final List<CustomButton> buttons = new java.util.ArrayList<>();
    private boolean isBombMode = false;
    private boolean isWaitingForPass = false;

    private ButtonManager() {}

    public static ButtonManager getInstance() {
        return INSTANCE;
    }

    public void addButton(CustomButton button) {
        buttons.add(button);
    }

    public void removeButton(CustomButton button) {
        buttons.remove(button);
    }

    public void refreshAllButtons() {
        for (CustomButton button : buttons) {
            button.updateAppearance();
        }
    }

    public void getString(String text) {
        // 处理按钮点击事件
        System.out.println("Button clicked: " + text);
    }

    public void toggleBombMode() {
        isBombMode = !isBombMode;
        System.out.println("Bomb mode: " + (isBombMode ? "ON" : "OFF"));
    }

    
    public boolean isBombMode() {
        return isBombMode;
    }

    public boolean isWaitingForPass() {
        // TODO: 返回当前是否允许pass
        return isWaitingForPass; // 或根据实际情况返回
    }

    public int getCurrentGameNumber() {
        return 1; // TODO: 返回当前游戏编号
    }
}
