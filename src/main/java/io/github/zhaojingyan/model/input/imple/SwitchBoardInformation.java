package io.github.zhaojingyan.model.input.imple;

import io.github.zhaojingyan.controller.game.GameException;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.game.Board;
import io.github.zhaojingyan.model.input.InputInformation;

public class SwitchBoardInformation implements InputInformation {
    private final int boardNumber;

    private SwitchBoardInformation(int boardNumber) {
        this.boardNumber = boardNumber;
    }

    public static SwitchBoardInformation create(String input) {
        return new SwitchBoardInformation(Integer.parseInt(input));
    }

    @Override
    public InputType getInputType() {
        return InputType.BOARDNUM;
    }

    @Override
    public Object getInfo() {
        return boardNumber;
    }

    @Override
    public void handle(boolean isWaitingForPass, Board board, PlayerSymbol currentSymbol, GameMode gameMode) throws GameException{
    }
}