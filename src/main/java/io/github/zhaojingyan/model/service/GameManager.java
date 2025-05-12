package io.github.zhaojingyan.model.service;

import java.util.HashMap;
import java.util.Map;

import io.github.zhaojingyan.model.enums.GameErrorCode;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.input.InputInformation;

/**
 * 游戏管理器，管理所有游戏实例
 */
public final class GameManager {
    // 单例相关
    private static final GameManager instance = new GameManager();
    private final Map<Integer, Game> games;
    private Game currentGame;

    // 构造函数
    private GameManager() {
        games = new HashMap<>();
        createGame("Bill_Black", "Walt_White", GameMode.PEACE);
        createGame("Bill_Black", "Walt_White", GameMode.REVERSI);
        createGame("Bill_Black", "Walt_White", GameMode.GOMOKU);
        try {
            switchToGame(1);
        } catch (GameException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    // 游戏管理
    public void createGame(String p1Name, String p2Name, GameMode gameMode) {
        Game game = new Game(games.size() + 1,gameMode, p1Name, p2Name);
        games.put(game.getGameNumber(), game);
    }

    public void switchToGame(int gameNum) throws GameException {
        if (games.get(gameNum) == null) {
            throw new GameException(GameErrorCode.GAME_NOT_FOUND,
                    "Game " + gameNum + " does not exist");
        }
        currentGame = games.get(gameNum);
    }

    /**
     * 更新当前游戏状态
     * 注意：如果游戏已经结束，此方法会抛出GameException
     * @param info 输入信息
     * @throws GameException 如果游戏已经结束
     */
    public void updateCurrentGame(InputInformation info) throws GameException {
        currentGame.update(info);
    }

    // Getters
    public static GameManager getInstance() { return instance; }
    public Game getCurrentGame() { return currentGame; }
    public boolean isCurrentGameOver() { return currentGame.isOver(); }
    public boolean isAllGamesOver() { return games.values().stream().allMatch(Game::isOver); }
    public int getTotalGames() { return games.size(); }
    public GameMode[] getGameList() {
        GameMode[] gameModes = new GameMode[games.size()];
        int index = 0;
        for (Game game : games.values())
            gameModes[index++] = game.getGameMode();
        return gameModes;
    }
}