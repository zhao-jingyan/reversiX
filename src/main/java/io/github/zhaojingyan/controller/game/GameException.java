package io.github.zhaojingyan.controller.game;

import io.github.zhaojingyan.model.enums.GameErrorCode;

public class GameException extends Exception {
    private final GameErrorCode code;  // 错误代码

    //构造函数
    public GameException(GameErrorCode code, String message) {
        super(message);
        this.code = code;
    }

    public GameErrorCode getCode() {
        return code;
    }
}
