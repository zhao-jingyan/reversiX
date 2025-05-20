package io.github.zhaojingyan.model.input.imple;

import io.github.zhaojingyan.model.entities.Board;
import io.github.zhaojingyan.model.enums.GameErrorCode;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.OutputType;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.service.GameException;
import io.github.zhaojingyan.model.service.GameManager;

public class MoveInformation implements InputInformation {
    private final int[] move;

    private MoveInformation(int[] move) {
        this.move = move;
    }

    public static MoveInformation create(String input) {
        int[] coordinates = new int[2];
        coordinates[0] = Integer.parseInt(String.valueOf(input.charAt(0)), 16) - 1; // 行
        coordinates[1] = input.charAt(1) - 'A'; // 列
        return new MoveInformation(coordinates);
    }

    @Override
    public OutputType getOutputType() {
        return OutputType.REFRESH;
    }

    @Override
    public int[] getInfo() {
        return move;
    }

    @Override
    public void handle(boolean isWaitingForPass, Board board, PlayerSymbol currentSymbol, GameMode gameMode) throws GameException {
        int[] coordinate = getInfo();
        int x = coordinate[0]; // 行
        int y = coordinate[1]; // 列
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

    @Override
    public void preHandle() throws GameException {
        GameManager.getInstance().updateCurrentGame(this);
    }

    @Override
    public String toString() {
        return "MoveInformation{" +
                "move=[" + move[0] + ", " + move[1] + "]" +
                '}';
    }

}