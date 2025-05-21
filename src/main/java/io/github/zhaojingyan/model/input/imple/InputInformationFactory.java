package io.github.zhaojingyan.model.input.imple;

import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.input.InputInformation;

public class InputInformationFactory {
    // 创建输入信息对象唯一的调用点
    public static InputInformation create(InputType type, String input) {
        input = input.toUpperCase();
        return switch (type) {
            case COORDINATES -> MoveInformation.create(input);
            case BOMB -> BombInformation.create(input);
            case PASS -> PassInformation.create();
            case NEWGAME -> NewGameInformation.create(input);
            case BOARDNUM -> SwitchBoardInformation.create(input);
            case QUIT -> QuitInformation.create();
            case INVALID -> InvalidInformation.create();
            case PLAYBACK -> PlaybackInformation.create();
        };
    }
}