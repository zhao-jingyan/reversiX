package io.github.zhaojingyan.model.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import io.github.zhaojingyan.model.enums.GameErrorCode;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.input.InputInformation;

/**
 * 游戏管理器，管理所有游戏实例
 */
public final class GameManager implements Serializable {
    private static final long serialVersionUID = 1L;

    // 单例相关
    private static final GameManager instance = new GameManager();
    private final Map<Integer, Game> games;
    private Game currentGame;

    // 构造函数
    private GameManager() {
        games = new HashMap<>();
        initialize();
    }

    private void initialize() {
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

    public void updateCurrentGame(InputInformation info) throws GameException {
        currentGame.update(info);
    }

    public void save(String resourcePath) {
        try (java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(
                new java.io.FileOutputStream(resourcePath))) {
            out.writeObject(games);
            int currentNum = (currentGame == null) ? -1 : currentGame.getGameNumber();
            out.writeInt(currentNum);
            System.out.println("Game state saved successfully.");
        } catch (Exception e) {
            System.err.println("Failed to save game state: " + e.getMessage());
            System.err.println("Please check file permissions or object serializability. The program will continue running.");
        }
    }

    public void load(String resourcePath) {
        try (java.io.ObjectInputStream in = new java.io.ObjectInputStream(
                new java.io.FileInputStream(resourcePath))) {
            @SuppressWarnings("unchecked")
            Map<Integer, Game> loadedGames = (Map<Integer, Game>) in.readObject();
            games.clear();
            games.putAll(loadedGames);
            int currentNum = in.readInt();
            if (currentNum != -1) {
                currentGame = games.get(currentNum);
            } else {
                currentGame = null;
            }
            System.out.println("Game state loaded successfully.");
        } catch (Exception e) {
            System.err.println("Failed to load game state: " + e.getMessage());
            System.err.println("Initializing to default state.");
            games.clear();
            initialize();
        }
    }

    // Getters
    public static GameManager getInstance() { return instance; }
    public Game getCurrentGame() { return currentGame; }
    public boolean isCurrentGameOver() { return currentGame.isOver(); }
    public int getTotalGames() { return games.size(); }
    public GameMode[] getGameList() {
        GameMode[] gameModes = new GameMode[games.size()];
        int index = 0;
        for (Game game : games.values())
            gameModes[index++] = game.getGameMode();
        return gameModes;
    }
}