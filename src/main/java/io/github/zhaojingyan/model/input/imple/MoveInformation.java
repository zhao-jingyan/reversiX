package io.github.zhaojingyan.model.input.imple;

import io.github.zhaojingyan.controller.game.GameException;
import io.github.zhaojingyan.model.enums.GameErrorCode;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.game.Board;
import io.github.zhaojingyan.model.input.InputInformation;

public class MoveInformation implements InputInformation {
    private final int[] move;

    private MoveInformation(int[] move) {
        this.move = move;
    }

    public static MoveInformation create(String input) {
        int[] coordinates = new int[2];
        coordinates[1] = Integer.parseInt(String.valueOf(input.charAt(0)), 16) - 1;
        coordinates[0] = input.charAt(1) - 'A';
        return new MoveInformation(coordinates);
    }

    @Override
    public InputType getInputType() {
        return InputType.COORDINATES;
    }

    @Override
    public int[] getInfo() {
        return move;
    }

    @Override
    public void handle(boolean isWaitingForPass, Board board, PlayerSymbol currentSymbol, GameMode gameMode) throws GameException {
        int[] coordinate = getInfo();
        int x = coordinate[1];
        int y = coordinate[0];
        if (board.isOutOfBoard(coordinate)) {
            throw new GameException(GameErrorCode.INVALID_INPUT,"Invalid input");
        }
        if (!board.isValid(coordinate)) {
            if (!board.isEmpty(coordinate))
            throw new GameException(GameErrorCode.CONFLICTING_MOVE, "Conflicting move! ["
                + (char) (x < 9 ? '1' + x : 'A' + (x - 9)) + (char) ('A' + y) + "] is already occupied");
            else
            throw new GameException(GameErrorCode.ILLEGAL_MOVE, "Illegal move! [" + (char) (x < 9 ? '1' + x : 'A' + (x - 9))
                + (char) ('A' + y) + "] is not a valid position");
        } 
        else {
            board.setPiece(coordinate, currentSymbol.SymbolToStatus(),null);
        }
    }
}