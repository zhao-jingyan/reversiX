package io.github.zhaojingyan.model.input.imple;

import io.github.zhaojingyan.model.entities.Board;
import io.github.zhaojingyan.model.enums.GameErrorCode;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.OutputType;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.service.GameException;
import io.github.zhaojingyan.model.service.GameManager;

public class SwitchBoardInformation implements InputInformation {
    private final int boardNumber;

    private SwitchBoardInformation(int boardNumber) {
        this.boardNumber = boardNumber;
    }

    public static SwitchBoardInformation create(String input) {
        return new SwitchBoardInformation(Integer.parseInt(input));
    }

    @Override
    public OutputType getOutputType() {
        return OutputType.REFRESH;
    }

    @Override
    public Object getInfo() {
        return boardNumber;
    }

    @Override
    public void handle(boolean isWaitingForPass, Board board, PlayerSymbol currentSymbol, GameMode gameMode)
            throws GameException {
    }

    @Override
    public void preHandle() throws GameException {
        try {
            int gameNum = (int) this.getInfo();
            GameManager.getInstance().switchToGame(gameNum);
        } catch (GameException e) {
            throw new GameException(GameErrorCode.GAME_NOT_FOUND,
                    "Game " + this.getInfo() + " does not exist");
        }
    }
}