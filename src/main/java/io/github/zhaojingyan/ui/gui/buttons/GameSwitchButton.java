package io.github.zhaojingyan.ui.gui.buttons;

import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.input.InputInformationFactory;

/**
 * 棋盘切换按钮
 */
public final class GameSwitchButton extends CustomButton {
    private final int gameNumber;
    
    /**
     * 创建棋盘切换按钮
     * @param gameNumber 棋盘编号
     */
    public GameSwitchButton(int gameNumber) {
        super("棋盘" + gameNumber);
        this.gameNumber = gameNumber;
        setMinWidth(80);
        updateInputInfo();
    }
    
    /**
     * 更新输入信息
     */
    public void updateInputInfo() {
        InputInformation inputInfo = InputInformationFactory.create(
            InputType.BOARDNUM, String.valueOf(gameNumber));
        setInputInfo(inputInfo);
    }
    
    /**
     * 高亮显示当前选中的棋盘
     * @param isSelected 是否选中
     */
    public void setSelected(boolean isSelected) {
        if (isSelected) {
            setStyle("-fx-background-color: #ffe066; -fx-border-color: #333; -fx-border-width: 2; -fx-font-weight: bold; -fx-background-insets: 0; -fx-background-radius: 6; -fx-cursor: hand; -fx-effect: null;");
        } else {
            setStyle("-fx-background-insets: 0; -fx-background-radius: 6; -fx-cursor: hand; -fx-effect: null;");
        }
    }
}
