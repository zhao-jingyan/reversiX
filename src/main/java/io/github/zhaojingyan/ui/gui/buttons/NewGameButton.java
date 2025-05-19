package io.github.zhaojingyan.ui.gui.buttons;

import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.input.InputInformationFactory;

public class NewGameButton extends CustomButton {
    /**
     * 创建新游戏按钮
     */
    private final String gameName;
    
    public NewGameButton(String gameName) {
        super("新游戏");
        this.gameName = gameName;
        setMinWidth(80);
        setStyle("-fx-background-insets: 0; -fx-background-radius: 6; -fx-cursor: hand;");
    }

    /**
     * 更新输入信息
     */
    public void updateInputInfo() {
        InputInformation inputInfo = InputInformationFactory.create(
            InputType.NEWGAME, gameName );
        setInputInfo(inputInfo);
    }
}