package io.github.zhaojingyan.model.input.imple;

import io.github.zhaojingyan.controller.game.GameException;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.game.Board;
import io.github.zhaojingyan.model.input.InputInformation;

public class PlaybackInformation implements InputInformation{

    private PlaybackInformation() {

    }

    public static PlaybackInformation create() {
        return new PlaybackInformation();
    }

    @Override
    public InputType getInputType() {
        return InputType.PLAYBACK;
    }

    @Override
    public Object getInfo() {
        return null;
    }

    @Override
    public void handle(boolean isWaitingForPass, Board board, PlayerSymbol currentSymbol, GameMode gameMode) throws GameException{
    }
}
