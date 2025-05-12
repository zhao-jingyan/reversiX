package io.github.zhaojingyan.model.input.imple;
import io.github.zhaojingyan.controller.game.GameException;
import io.github.zhaojingyan.model.enums.GameErrorCode;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.enums.PieceStatus;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.game.Board;
import io.github.zhaojingyan.model.input.InputInformation;

public class BombInformation implements InputInformation {
    private final int[] bomb;

    private BombInformation(int[] bomb) {
        this.bomb = bomb;
    }

    public static BombInformation create(String input) {
        int[] coordinates = new int[2];
        coordinates[1] = Integer.parseInt(String.valueOf(input.charAt(1)), 16) - 1;
        coordinates[0] = input.charAt(2) - 'A';
        return new BombInformation(coordinates);
    }

    @Override
    public InputType getInputType() {
        return InputType.BOMB;
    }

    @Override
    public int[] getInfo() {
        return bomb;
    }
    
    @Override
    public void handle(boolean isWaitingForPass, Board board, PlayerSymbol currentSymbol, GameMode gameMode) throws GameException {
        int[] coordinate = getInfo();
        int x = coordinate[1];
        int y = coordinate[0];
        if (board.isOutOfBoard(coordinate) || gameMode != GameMode.GOMOKU) {
            throw new GameException(GameErrorCode.INVALID_INPUT,"Invalid input");
        }
        else if (!board.haveBomb(currentSymbol)) {
            throw new GameException(GameErrorCode.OUT_OF_BOMB, "Running out of bomb! You have no bomb left");
        }
        else if (!board.isOpp(currentSymbol.SymbolToStatus(),coordinate)) {
            throw new GameException(GameErrorCode.NOT_BOMB_TARGET, "Not a valid bomb target! [" + (char) (x < 9 ? '1' + x : 'A' + (x - 9))
                + (char) ('A' + y) + "] is not a valid target");
        } 
        else {
            board.setPiece(coordinate, PieceStatus.BOMB, currentSymbol);
        }
    }
}
