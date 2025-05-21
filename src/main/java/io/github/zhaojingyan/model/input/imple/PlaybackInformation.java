package io.github.zhaojingyan.model.input.imple;

import io.github.zhaojingyan.model.entities.Board;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.OutputType;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.service.GameException;

public class PlaybackInformation implements InputInformation {

    private PlaybackInformation() {

    }

    protected static PlaybackInformation create() {
        return new PlaybackInformation();
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
    public void handle(boolean isWaitingForPass, Board board, PlayerSymbol currentSymbol, GameMode gameMode)
            throws GameException {
    }

    @Override
    public void preHandle() throws GameException {
        // No action needed for playback
    }

    @Override
    public String toString() {
        return "PlaybackInformation{}";
    }
}
