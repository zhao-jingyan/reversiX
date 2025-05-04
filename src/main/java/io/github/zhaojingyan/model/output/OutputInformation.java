package io.github.zhaojingyan.model.output;

import io.github.zhaojingyan.model.enums.OutputType;

public class OutputInformation {
        private final GameInfo boardInfo;
    private final GlobalInfo gameInfo;
    private final OutputType type;

    public OutputInformation(GameInfo boardInfo, GlobalInfo gameInfo, OutputType type) {
        this.boardInfo = boardInfo;
        this.gameInfo = gameInfo;
        this.type = type;
    }

    // 基本 getter 方法
    public GameInfo getGameInfo() { return boardInfo; }
    public GlobalInfo getGlobalInfo() { return gameInfo; }
    public OutputType getOutputType() { return type; }
}
