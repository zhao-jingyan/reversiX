package io.github.zhaojingyan.model.input.imple;

import io.github.zhaojingyan.controller.game.GameException;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.game.Board;
import io.github.zhaojingyan.model.input.InputInformation;

public class NewGameInformation implements InputInformation {
    private final GameMode mode;

    private NewGameInformation(GameMode mode) {
        this.mode = mode;
    }

    public static NewGameInformation create(String input) {
        return new NewGameInformation(GameMode.valueOf(input.toUpperCase()));
    }

    @Override
    public InputType getInputType() {
        return InputType.NEWGAME;
    }

    @Override
    public Object getInfo() {
        return mode;
    }

    @Override
    public void handle(boolean isWaitingForPass, Board board, PlayerSymbol currentSymbol, GameMode gameMode) throws GameException {

    }
}