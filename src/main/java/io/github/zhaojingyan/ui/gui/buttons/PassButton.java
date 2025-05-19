package io.github.zhaojingyan.ui.gui.buttons;

import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.input.InputInformationFactory;

/**
 * Pass按钮，用于玩家跳过回合
 */
public final class PassButton extends CustomButton {
    
    /**
     * 创建Pass按钮
     */
    public PassButton() {
        super("Pass");
        setMinWidth(80);
        updateInputInfo();
    }
    
    /**
     * 更新输入信息
     */
    public void updateInputInfo() {
        InputInformation inputInfo = InputInformationFactory.create(InputType.PASS, "PASS");
        setInputInfo(inputInfo);
    }
}
