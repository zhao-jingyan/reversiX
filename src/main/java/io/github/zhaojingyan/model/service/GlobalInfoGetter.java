package io.github.zhaojingyan.model.service;

import io.github.zhaojingyan.model.enums.GameMode;

public class GlobalInfoGetter {
    private final GameMode[] gameList;
    private final int currentGameNumber;
    private final int totalGamesNumber;

    public GlobalInfoGetter(GameManager gameManagerInstance) {
        this.gameList = gameManagerInstance.getGameList();
        this.currentGameNumber = gameManagerInstance.getCurrentGame().getGameNumber();
        this.totalGamesNumber = gameManagerInstance.getTotalGames();
    }

    // Getter方法
    public GameMode[] getGameList() {
        return gameList;
    }

    public int getCurrentGameNumber() {
        return currentGameNumber;
    }

    public int getTotalGamesNumber() {
        return totalGamesNumber;
    }
}
