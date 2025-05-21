package io.github.zhaojingyan.model.input.imple;

import io.github.zhaojingyan.model.entities.Board;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.OutputType;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.service.GameException;
import io.github.zhaojingyan.model.service.GameManager;

public class NewGameInformation implements InputInformation {
    private final GameMode mode;

    private NewGameInformation(GameMode mode) {
        this.mode = mode;
    }

    protected static NewGameInformation create(String input) {
        return new NewGameInformation(GameMode.valueOf(input.toUpperCase()));
    }

    @Override
    public OutputType getOutputType() {
        return OutputType.REFRESH;
    }

    @Override
    public Object getInfo() {
        return mode;
    }

    @Override
    public void handle(boolean isWaitingForPass, Board board, PlayerSymbol currentSymbol, GameMode gameMode)
            throws GameException {

    }

    @Override
    public void preHandle() throws GameException {
        GameManager.getInstance().createGame("Bill_Black", "Walt_White", (GameMode) this.getInfo());
    }

    @Override
    public String toString() {
        return "NewGameInformation{" +
                "mode=" + mode +
                '}';
    }
}