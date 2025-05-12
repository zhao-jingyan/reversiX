package io.github.zhaojingyan.model.output;

import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.service.GameManager;

public class GlobalInfo {
        private final GameMode[] gameList;
    private final int currentGameNumber;
    private final int totalGamesNumber;

    public GlobalInfo(GameManager gameManagerInstance) {
        this.gameList = gameManagerInstance.getGameList();
        this.currentGameNumber = gameManagerInstance.getCurrentGame().getGameNumber();
        this.totalGamesNumber = gameManagerInstance.getTotalGames();
    }

    // Getter方法
    public GameMode[] getGameList() { return gameList; }
    public int getCurrentGameNumber() { return currentGameNumber; }
    public int getTotalGamesNumber() { return totalGamesNumber; }
}
