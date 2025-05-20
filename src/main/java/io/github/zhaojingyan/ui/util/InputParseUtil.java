package io.github.zhaojingyan.ui.util;

import io.github.zhaojingyan.model.enums.InputType;

public class InputParseUtil {
    public static InputType determineType(String input) {
        if(input.length() > 9 && input.substring(0,9).toLowerCase().equals("playback ")){
            return InputType.PLAYBACK;
        }
        if(input.length() == 3 && input.charAt(0) == '@'  && isCoordinate(input.substring(1))) {
            return InputType.BOMB;
        }
        else if (isCoordinate(input)) {
            return InputType.COORDINATES;
        }
        else if (input.toLowerCase().equals("pass")) {
            return InputType.PASS;
        }
        else if (input.toLowerCase().equals("quit")) {
            return InputType.QUIT;
        }
        else if (input.toLowerCase().equals("peace") || input.toLowerCase().equals("reversi")
                || input.toLowerCase().equals("gomoku")) {
            return InputType.NEWGAME;
        }
        else {
            try {
                if (Integer.parseInt(input) >= 1) {
                    return InputType.BOARDNUM;
                } else {
                    return InputType.INVALID;
                }
            } catch (NumberFormatException e) {
                return InputType.INVALID;
            }
        }
    }

    public static boolean isCoordinate(String input) {
        return input.length() == 2 &&
            ((input.charAt(1) >= 'A' && input.charAt(1) <= 'O') ||
            (input.charAt(1) >= 'a' && input.charAt(1) <= 'o')) &&
            ((input.charAt(0) >= '1' && input.charAt(0) <= '9') ||
            (input.charAt(0) >= 'A' && input.charAt(0) <= 'F') ||
            (input.charAt(0) >= 'a' && input.charAt(0) <= 'f'));
    }
}
