package io.github.zhaojingyan.controller.rule.imple;

import io.github.zhaojingyan.controller.rule.Rule;
import io.github.zhaojingyan.model.enums.CellStatus;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.game.Board;
import io.github.zhaojingyan.model.game.Cell;
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
        for(Cell cell : board.getCellBoard()) {
            cell.setPiece(CellStatus.EMPTY);
            cell.setValid(true);
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
        //no winner in peace game
        return PlayerSymbol.VOID;
    }
}
