package io.github.zhaojingyan.model.output;

import io.github.zhaojingyan.model.enums.OutputType;
import io.github.zhaojingyan.model.service.GameInfoGetter;
import io.github.zhaojingyan.model.service.GlobalInfoGetter;

public class OutputInformation {
    private final GameInfoGetter boardInfo;
    private final GlobalInfoGetter gameInfo;
    private final OutputType type;

    public OutputInformation(GameInfoGetter boardInfo, GlobalInfoGetter gameInfo, OutputType type) {
        this.boardInfo = boardInfo;
        this.gameInfo = gameInfo;
        this.type = type;
    }

    // 基本 getter 方法
    public GameInfoGetter getGameInfo() {
        return boardInfo;
    }

    public GlobalInfoGetter getGlobalInfo() {
        return gameInfo;
    }

    public OutputType getOutputType() {
        return type;
    }
}
