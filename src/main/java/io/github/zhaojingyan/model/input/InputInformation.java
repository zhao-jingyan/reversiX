package io.github.zhaojingyan.model.input;

import io.github.zhaojingyan.model.entities.Board;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.OutputType;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.service.GameException;

public interface InputInformation {
    // 获取输出类型
    OutputType getOutputType();

    // 获取信息
    Object getInfo();

    void handle(boolean isWaitingForPass, Board board, PlayerSymbol currentSymbol, GameMode gameMode)
            throws GameException;

    void preHandle() throws GameException;

}
