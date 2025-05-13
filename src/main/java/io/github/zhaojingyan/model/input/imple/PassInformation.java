package io.github.zhaojingyan.model.input.imple;
import io.github.zhaojingyan.model.entities.Board;
import io.github.zhaojingyan.model.enums.GameErrorCode;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.OutputType;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.service.GameException;
import io.github.zhaojingyan.model.service.GameManager;

public class PassInformation implements InputInformation {
    private PassInformation() {
    }

    public static PassInformation create() {
        return new PassInformation();
    }

    @Override
    public OutputType getOutputType() {
        return OutputType.REFRESH;
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

    @Override
    public void preHandle() throws GameException {
        GameManager.getInstance().updateCurrentGame(this);
    }
}