package io.github.zhaojingyan.controller.logic;

import io.github.zhaojingyan.controller.game.GameException;
import io.github.zhaojingyan.controller.game.GameManager;
import io.github.zhaojingyan.model.enums.GameErrorCode;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.OutputType;
import io.github.zhaojingyan.model.input.InputInformation;

public class InputHandler {
    private static OutputType outputType;
    
    protected static OutputType handleInput(InputInformation input) throws GameException {
        switch (input.getInputType()) {
            case QUIT -> {
                outputType = OutputType.QUIT;
            }
            case BOARDNUM -> {
                // 截获越界的棋盘数
                try {
                    int gameNum = (int) input.getInfo();
                    GameManager.getInstance().switchToGame(gameNum);
                    outputType = OutputType.REFRESH;
                } catch (GameException e) {
                    throw new GameException(GameErrorCode.GAME_NOT_FOUND,
                            "Game " + input.getInfo() + " does not exist");
                }
            }
            case NEWGAME -> {
                GameManager.getInstance().createGame("Bill_Black", "Walt_White", (GameMode) input.getInfo());
                outputType = OutputType.REFRESH;
            }
            case COORDINATES, PASS, BOMB-> {
                GameManager.getInstance().updateCurrentGame(input);
                outputType = OutputType.REFRESH;
            }
            case PLAYBACK ->{
                outputType = OutputType.REFRESH;//clear screen, reprint
            }
            default -> {
                throw new GameException(GameErrorCode.INVALID_INPUT,
                        "Invalid input");
            }
        }
        return outputType;
    }
}