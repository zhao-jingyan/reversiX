package io.github.zhaojingyan.model.input;

import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.input.imple.InvalidInformation;
import io.github.zhaojingyan.model.input.imple.MoveInformation;
import io.github.zhaojingyan.model.input.imple.NewGameInformation;
import io.github.zhaojingyan.model.input.imple.PassInformation;
import io.github.zhaojingyan.model.input.imple.QuitInformation;
import io.github.zhaojingyan.model.input.imple.SwitchBoardInformation;

public class InputInformationFactory {
    //创建输入信息对象唯一的调用点
    public static InputInformation create(InputType type, String input) {
        input = input.toUpperCase();
        return switch (type) {
            case COORDINATES -> MoveInformation.create(input);
            case PASS -> PassInformation.create();
            case NEWGAME -> NewGameInformation.create(input);
            case BOARDNUM -> SwitchBoardInformation.create(input);
            case QUIT -> QuitInformation.create();
            case INVALID -> InvalidInformation.create();
        };
    }
}