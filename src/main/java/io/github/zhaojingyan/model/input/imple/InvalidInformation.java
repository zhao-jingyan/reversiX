package io.github.zhaojingyan.model.input.imple;

import io.github.zhaojingyan.model.entities.Board;
import io.github.zhaojingyan.model.enums.GameErrorCode;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.OutputType;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.service.GameException;

public class InvalidInformation implements InputInformation {
    private InvalidInformation() {
    }

    public static InvalidInformation create() {
        return new InvalidInformation();
    }

    @Override
    public OutputType getOutputType() {
        return OutputType.INVALID_INPUT;
    }

    @Override
    public Object getInfo() {
        return null;
    }

    @Override
    public void handle(boolean isWaitingForPass, Board board, PlayerSymbol currentSymbol, GameMode gameMode) {
    }

    @Override
    public void preHandle() throws GameException {
        throw new GameException(GameErrorCode.INVALID_INPUT, "Invalid input");
    }

    @Override
    public String toString() {
        return "InvalidInformation{}";
    }
}