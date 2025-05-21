package io.github.zhaojingyan.model.rule.imple;

import java.io.Serializable;

import io.github.zhaojingyan.model.entities.Board;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.rule.Rule;

public class PeaceRule implements Rule, Serializable {
    private static final long serialVersionUID = 1L;
    private final GameMode gamemode;

    public PeaceRule() {
        this.gamemode = GameMode.PEACE;
    }

    @Override
    public GameMode getGameMode() {
        return gamemode;
    }

    @Override
    public void initializeBoard(Board board) {
        for (int i = 0; i < board.getRow(); i++) {
            for (int j = 0; j < board.getCol(); j++) {
                board.setValid(new int[] { i, j }, true);
            }
        }
    }

    @Override
    public void updateBoard(Board board, InputInformation information, PlayerSymbol currentSymbol) {

    }

    @Override
    public boolean shouldPass() {
        // no pass in peace game
        return false;
    }

    @Override
    public boolean isOver(Board board) {
        return board.isFull();
    }

    @Override
    public PlayerSymbol getWinner(Board board) {
        // no winner in peace game
        return PlayerSymbol.VOID;
    }
}
