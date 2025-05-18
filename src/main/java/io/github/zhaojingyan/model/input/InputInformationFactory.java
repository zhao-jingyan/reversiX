package io.github.zhaojingyan.model.input;

import io.github.zhaojingyan.model.input.imple.BombInformation;
import io.github.zhaojingyan.model.input.imple.InvalidInformation;
import io.github.zhaojingyan.model.input.imple.MoveInformation;
import io.github.zhaojingyan.model.input.imple.NewGameInformation;
import io.github.zhaojingyan.model.input.imple.PassInformation;
import io.github.zhaojingyan.model.input.imple.PlaybackInformation;
import io.github.zhaojingyan.model.input.imple.QuitInformation;
import io.github.zhaojingyan.model.input.imple.SwitchBoardInformation;

public class InputInformationFactory {
    // 创建输入信息对象唯一的调用点
    public static InputInformation create(String input) {
        input = input.toLowerCase();

        if (input.length() > 9 && input.substring(0, 9).equals("PLAYBACK ")) {
            return PlaybackInformation.create();
        }
        // 检查是否是坐标
        else if (input.length() == 3 && input.charAt(0) == '@' && isCoordinate(input.substring(1))) {
            return BombInformation.create(input);
        } else if (isCoordinate(input)) {
            return MoveInformation.create(input);
        }
        // 检查是否是pass
        else if (input.equals("pass")) {
            return PassInformation.create();
        }
        // 检查是否是quit
        else if (input.equals("quit")) {
            return QuitInformation.create();
        }
        // 检查是否是newgame
        else if (input.equals("peace") || input.equals("reversi")
                || input.equals("gomoku")) {
            return NewGameInformation.create(input);
        }
        // 检查是否是boardnum
        else {
            try {
                if (Integer.parseInt(input) >= 1) {
                    return SwitchBoardInformation.create(input);
                } else {
                    return InvalidInformation.create();
                }
            } catch (NumberFormatException e) {
                return InvalidInformation.create();
            }
        }
    }
        private static boolean isCoordinate(String input) {
        return input.length() == 2 &&
            ((input.charAt(1) >= 'A' && input.charAt(1) <= 'O') ||
            (input.charAt(1) >= 'a' && input.charAt(1) <= 'o')) &&
            ((input.charAt(0) >= '1' && input.charAt(0) <= '9') ||
            (input.charAt(0) >= 'A' && input.charAt(0) <= 'F') ||
            (input.charAt(0) >= 'a' && input.charAt(0) <= 'f'));
    }
}