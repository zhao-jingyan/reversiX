package io.github.zhaojingyan.controller.rule.imple;

import io.github.zhaojingyan.controller.rule.Rule;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.PieceStatus;
import io.github.zhaojingyan.model.game.Board;
import io.github.zhaojingyan.model.input.InputInformation;

public class PeaceRule implements Rule {
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
                board.setValid(new int[]{i,j}, true);
            }
        }
    }

    @Override
    public void updateBoard(Board board, InputInformation information,PieceStatus currentPiece) {
        
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
    public PieceStatus getWinner(Board board) {
        //no winner in peace game
        return null;
    }
}
