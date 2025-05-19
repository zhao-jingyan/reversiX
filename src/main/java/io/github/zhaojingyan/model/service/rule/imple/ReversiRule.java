package io.github.zhaojingyan.model.service.rule.imple;

import io.github.zhaojingyan.model.entities.Board;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.PieceStatus;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.input.imple.MoveInformation;
import io.github.zhaojingyan.model.service.rule.Rule;

public class ReversiRule implements Rule {
    private final GameMode gamemode;
    private boolean isOver;
    private boolean isWaitingForPass;

    public ReversiRule() {
        this.gamemode = GameMode.REVERSI;
    }

    @Override
    public GameMode getGameMode() {
        return gamemode;
    }

    @Override
    public void initializeBoard(Board board) {
        // Initialize the board for Reversi
        int midRow = board.getRow() / 2;
        int midCol = board.getCol() / 2;

        board.setPiece(new int[]{midRow - 1, midCol - 1}, PieceStatus.WHITE,null);
        board.setPiece(new int[]{midRow - 1, midCol}, PieceStatus.BLACK,null);
        board.setPiece(new int[]{midRow, midCol - 1}, PieceStatus.BLACK,null);
        board.setPiece(new int[]{midRow, midCol}, PieceStatus.WHITE,null);
        refreshValid(board, PieceStatus.BLACK);
    }
    @Override
    public void updateBoard(Board board, InputInformation information,PlayerSymbol currentSymbol) {
        PieceStatus currentPiece = currentSymbol.SymbolToStatus();
        if(information instanceof MoveInformation moveInformation){
            int []coordinates = moveInformation.getInfo();
            flip(board, coordinates, currentPiece);
        }
        refreshValid(board, currentPiece.opp());
    }

    private void flip(Board board, int[] input, PieceStatus currentPiece) {
        int x = input[0];
        int y = input[1];
        int[][] directions = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},
            {1, 1}, {-1, -1}, {1, -1}, {-1, 1}
        };
        for (int[] dir : directions) {
            this.flipbeam(board, dir, x, y, currentPiece);
        }
    }

    private void flipbeam(Board board, int[] direction, int x, int y, PieceStatus currentPiece) {
        // set the variables
        int xp = x;
        int yp = y;
        PieceStatus piece = currentPiece;
        PieceStatus opp = currentPiece.opp();
        int dx = direction[0];
        int dy = direction[1];

        // flip the pieces
        while (isInBoard(8, xp + dx, yp + dy)  // in boarder
                && board.getPieceAt(xp + dx, yp + dy).getStatus() == opp) {  // do not meet same piece
            xp += dx;
            yp += dy;
            // going back and flip the pieces
            if (isInBoard(8, xp + dx, yp + dy)
                    && board.getPieceAt(xp + dx, yp + dy).getStatus() == piece) {
                while (xp != x || yp != y) {
                    if (board.getPieceAt(xp, yp).getStatus() == PieceStatus.BLACK) {
                        board.black--;
                        board.white++;
                    } else if (board.getPieceAt(xp, yp).getStatus() == PieceStatus.WHITE) {
                        board.black++;
                        board.white--;
                    }
                    board.getPieceAt(xp, yp).flip();
                    xp -= dx;
                    yp -= dy;
                }
                break;
            }
        }
    }
    @Override
    public boolean shouldPass() {
        // Implement logic to determine if the turn should be passed
        return isWaitingForPass;
    }

    @Override
    public boolean isOver(Board board) {
        // Implement logic to check if the game is over
        return isOver || board.isFull();
    }

    @Override
    public PlayerSymbol getWinner(Board board) {
        if(board.getBlack() > board.getWhite())
            return PlayerSymbol.BLACK;
        else if(board.getBlack() < board.getWhite())
            return PlayerSymbol.WHITE;
        else
            return PlayerSymbol.TIE;
    }

    private void refreshValid(Board board,PieceStatus type) {
        // 先清除所有有效位置标记
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) 
                board.getValidBoard()[i][j] = false;

        // 检查当前玩家是否有合法位置
        isWaitingForPass = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isValidPosition(board, type, i, j)) {
                    board.getValidBoard()[i][j] = true;
                    isWaitingForPass = false;
                }
            }
        }

        // 如果当前玩家没有合法位置
        if (isWaitingForPass) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (isValidPosition(board, type.opp(), i, j)) {
                        return;
                    }
                }
            }
            isWaitingForPass = false;
            isOver = true;
        }
    }

    private boolean isValidPosition(Board board, PieceStatus type, int x, int y) {
        if (!isInBoard(8, x, y) || board.getPieceAt(x, y).getStatus() != PieceStatus.EMPTY) {
            return false;
        }
        int[][] directions = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},
            {1, 1}, {-1, -1}, {1, -1}, {-1, 1}
        };
        for (int[] dir : directions) {
            if (canFlipInDirection(board, type, x, y, dir)) {
                return true;
            }
        }
        return false;
    }

    private boolean canFlipInDirection(Board board, PieceStatus type, int x, int y, int[] direction) {
        // set the variables
        int xp = x;
        int yp = y;
        int dx = direction[0];
        int dy = direction[1];
        PieceStatus piece = type;
        PieceStatus opp = type.opp();
        if (!isInBoard(8, xp + dx, yp + dy) || board.getPieceAt(xp + dx, yp + dy).getStatus() != opp)  // not in board or no opp
            return false;
        else {
            xp += dx;
            yp += dy;
            while (isInBoard(8, xp, yp)  // in boarder
                && board.getPieceAt(xp, yp).getStatus() != PieceStatus.EMPTY) {  // do not meet empty or valid
            if (board.getPieceAt(xp, yp).getStatus() == piece)
                return true;
            else if (board.getPieceAt(xp, yp).getStatus() == opp) {
                xp += dx;
                    yp += dy;
                }
            }
            return false;
        }
    }

    private boolean isInBoard(int size, int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }
}