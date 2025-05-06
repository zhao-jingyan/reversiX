package io.github.zhaojingyan.controller.rule.imple;

import java.util.Random;

import io.github.zhaojingyan.controller.rule.Rule;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.PieceStatus;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.game.Board;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.input.imple.MoveInformation;

public class GomokuRule implements Rule {
    private final GameMode gamemode;
    private boolean isOver;
    private PieceStatus winner;

    public GomokuRule() {
        this.gamemode = GameMode.GOMOKU;
    }

    @Override
    public GameMode getGameMode() {
        return gamemode;
    }

    @Override
    public void updateBoard(Board board, InputInformation information, PlayerSymbol currentSymbol) {
        PieceStatus currentPiece = currentSymbol.SymbolToStatus();
        int[] coordinates = ((MoveInformation) information).getInfo();
        if (fiveInARow(board, coordinates, currentPiece)) {
            isOver = true;
            winner = currentPiece;
        }
    }

    private boolean fiveInARow(Board board, int[] coordinates, PieceStatus currentPiece) {
            // 四个方向: 水平、垂直、两个对角线
            int[][] directions = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};  // 水平、垂直、主对角线、副对角线
            
            // 检查每个方向
            for (int[] dir : directions) {
                if (checkDirection(board, coordinates[0], coordinates[1], dir, currentPiece)) {
                    return true;
                }
            }
            return false;
        }

    // 检查某个方向是否五子连珠
    private boolean checkDirection(Board board,int x, int y, int[] dir, PieceStatus currentPiece) {
        int count = 1;
        int dx = dir[0];
        int dy = dir[1];

        // 正向检查
        count += countPieces(board, x, y, dx, dy, currentPiece);

        // 反向检查
        count += countPieces(board, x, y, -dx, -dy, currentPiece);

        return count >= 5;
    }

    // 计算某个方向上相同颜色的棋子数量
    private int countPieces(Board board, int x, int y, int dx, int dy, PieceStatus currentPiece) {
        int count = 0;
        int newX = x + dx;
        int newY = y + dy;

        while (newX >= 0 && newX < board.getRow() && newY >= 0 && newY < board.getCol()
                && board.getPieceStatus(new int[] { newX, newY }) == currentPiece) {
            count++;
            newX += dx;
            newY += dy;
        }

        return count;
    }

    @Override
    public void initializeBoard(Board board) {
        for (int i = 0; i < board.getRow(); i++)
            for (int j = 0; j < board.getCol(); j++)
                board.setValid(new int[] { i, j }, true);
        Random random = new Random();
        int obstacles = 0;

        while (obstacles < 4) {
            int x = random.nextInt(board.getRow());
            int y = random.nextInt(board.getCol());
            int[] position = {x, y};

            if (board.getPieceStatus(position) == PieceStatus.EMPTY) {
                board.setPiece(position, PieceStatus.OBSTACLE);
                obstacles++;
            }
        }
    }

    @Override
    public boolean shouldPass() {
        // in gomoku game, there is no pass
        return false;
    }

    @Override
    public boolean isOver(Board board) {
        // Implement logic to check if the game is over
        return isOver || board.isFull();
    }

    @Override
    public PieceStatus getWinner(Board board) {
        if (winner != null) 
            return winner;
        return PieceStatus.EMPTY;
    }
}
