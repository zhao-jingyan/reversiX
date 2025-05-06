package io.github.zhaojingyan.model.output;

import io.github.zhaojingyan.controller.game.GameManager;
import io.github.zhaojingyan.model.enums.GameMode;

public class GlobalInfo {
        private final GameMode[] gameList;
    private final int currentGameNumber;
    private final GameMode currentGameMode;
    private final int totalGamesNumber;

    public GlobalInfo(GameManager gameManagerInstance) {
        this.gameList = gameManagerInstance.getGameList();
        this.currentGameNumber = gameManagerInstance.getCurrentGame().getGameNumber();
        this.currentGameMode = gameManagerInstance.getCurrentGame().getGameMode();
        this.totalGamesNumber = gameManagerInstance.getTotalGames();
    }

    // Getter方法
    public GameMode[] getGameList() { return gameList; }
    public int getCurrentGameNumber() { return currentGameNumber; }
    public int getTotalGamesNumber() { return totalGamesNumber; }
    public GameMode getCurrentGameMode() { return currentGameMode; }
}
