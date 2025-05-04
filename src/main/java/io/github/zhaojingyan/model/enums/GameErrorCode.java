package io.github.zhaojingyan.model.enums;

public enum GameErrorCode {
    INVALID_INPUT("<Invalid input!>"),           // 无效输入
    ILLEGAL_MOVE("<Illegal move!>"),            // 非法移动
    CONFLICTING_MOVE("<A piece is already at this location!>"),  // 冲突移动
    MAY_NOT_PASS("<Cannot pass when there are valid moves!>"),   // 不能跳过
    GAME_NOT_FOUND("<Game does not exist!>"),    // 游戏不存在
    GAME_ALREADY_OVER("<This game is already over!>");  // 游戏已结束

    private final String defaultMessage;  // 默认错误信息

    GameErrorCode(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getMessage() {
        return defaultMessage;
    }
}