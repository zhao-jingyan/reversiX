// 文件已废弃，不再需要GameSwitchButton，保留空壳防止编译报错
package io.github.zhaojingyan.ui.gui.buttons;

/**
 * @deprecated GameSwitchButton is no longer used. Game switching is now handled by ListPanel's ListView.
 */
@Deprecated
public final class GameSwitchButton extends CustomButton {
    public GameSwitchButton() {
        super("");
        setVisible(false);
        setManaged(false);
    }
    @Override
    public void updateAppearance() {}
}
