package io.github.zhaojingyan.model.input.imple;

import io.github.zhaojingyan.model.entities.Board;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.OutputType;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.service.GameException;

public class QuitInformation implements InputInformation {
    private QuitInformation() {
    }

    public static QuitInformation create() {
        return new QuitInformation();
    }

    @Override
    public OutputType getOutputType() {
        return OutputType.QUIT;
    }

    @Override
    public Object getInfo() {
        return null;
    }

    @Override
    public void handle(boolean isWaitingForPass, Board board, PlayerSymbol currentSymbol, GameMode gameMode) throws GameException{
    }

    @Override
    public void preHandle() throws GameException {
        // No action needed for quit
    }

    @Override
    public String toString() {
        return "QuitInformation{}";
    }
}