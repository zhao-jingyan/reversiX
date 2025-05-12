package io.github.zhaojingyan.model.input.imple;

import io.github.zhaojingyan.model.entities.Board;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.InputType;
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
    public InputType getInputType() {
        return InputType.QUIT;
    }

    @Override
    public Object getInfo() {
        return null;
    }

    @Override
    public void handle(boolean isWaitingForPass, Board board, PlayerSymbol currentSymbol, GameMode gameMode) throws GameException{
    }
}