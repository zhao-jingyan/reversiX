package io.github.zhaojingyan.model.input.imple;

import io.github.zhaojingyan.controller.game.GameException;
import io.github.zhaojingyan.model.enums.GameErrorCode;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.game.Board;
import io.github.zhaojingyan.model.input.InputInformation;

public class PassInformation implements InputInformation {
    private PassInformation() {
    }

    public static PassInformation create() {
        return new PassInformation();
    }

    @Override
    public InputType getInputType() {
        return InputType.PASS;
    }

    @Override
    public Object getInfo() {
        return null;
    }

    @Override
    public void handle(boolean isWaitingForPass, Board board, PlayerSymbol currentSymbol, GameMode gameMode) throws GameException{
        if (!isWaitingForPass)
            throw new GameException(GameErrorCode.MAY_NOT_PASS, "Cannot pass when there are valid moves");
    }
}