package io.github.zhaojingyan.ui.gui.buttons;

import java.util.List;

public class ButtonManager {
    private static final ButtonManager INSTANCE = new ButtonManager();
    private final List<CustomButton> buttons = new java.util.ArrayList<>();

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

    public int getCurrentGameNumber() {
        return 1; // TODO: 返回当前游戏编号
    }
}
