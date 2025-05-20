package io.github.zhaojingyan.model.input.imple;
import io.github.zhaojingyan.model.entities.Board;
import io.github.zhaojingyan.model.enums.GameErrorCode;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.OutputType;
import io.github.zhaojingyan.model.enums.PieceStatus;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.service.GameException;
import io.github.zhaojingyan.model.service.GameManager;

public class BombInformation implements InputInformation {
    private final int[] bomb;

    private BombInformation(int[] bomb) {
        this.bomb = bomb;
    }

    public static BombInformation create(String input) {
        int[] coordinates = new int[2];
        coordinates[0] = Integer.parseInt(String.valueOf(input.charAt(1)), 16) - 1; // 行
        coordinates[1] = input.charAt(2) - 'A'; // 列
        return new BombInformation(coordinates);
    }

    @Override
    public OutputType getOutputType() {
        return OutputType.REFRESH;
    }

    @Override
    public int[] getInfo() {
        return bomb;
    }
    
    @Override
    public void handle(boolean isWaitingForPass, Board board, PlayerSymbol currentSymbol, GameMode gameMode) throws GameException {
        int[] coordinate = getInfo();
        int x = coordinate[0]; // 行
        int y = coordinate[1]; // 列
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

    @Override
    public void preHandle() throws GameException {
        GameManager.getInstance().updateCurrentGame(this);
    }

    @Override
    public String toString() {
        return "BombInformation{" +
                "bomb=[" + bomb[0] + ", " + bomb[1] + "]" +
                '}';
    }
}