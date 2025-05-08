package io.github.zhaojingyan.controller.rule.imple;

import io.github.zhaojingyan.controller.rule.Rule;
import io.github.zhaojingyan.model.enums.CellStatus;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.game.Board;
import io.github.zhaojingyan.model.game.Cell;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.input.imple.MoveInformation;

public class ReversiRule implements Rule {
    private final GameMode gamemode = GameMode.REVERSI;
    private boolean isOver;
    private boolean isWaitingForPass;
    private static final int[][] DIRECTIONS = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},
            {1, 1}, {-1, -1}, {1, -1}, {-1, 1}
    };

    @Override
    public GameMode getGameMode() {
        return gamemode;
    }

    @Override
    public void initializeBoard(Board board) {
        int midRow = board.getRow() / 2;
        int midCol = board.getCol() / 2;

        board.setPiece(new int[]{midRow - 1, midCol - 1}, CellStatus.WHITE, null);
        board.setPiece(new int[]{midRow - 1, midCol}, CellStatus.BLACK, null);
        board.setPiece(new int[]{midRow, midCol - 1}, CellStatus.BLACK, null);
        board.setPiece(new int[]{midRow, midCol}, CellStatus.WHITE, null);
        refreshValid(board, CellStatus.BLACK);
    }

    @Override
    public void updateBoard(Board board, InputInformation information, PlayerSymbol currentSymbol) {
        if (!(information instanceof MoveInformation moveInfo)) return;

        int[] coordinates = moveInfo.getInfo();
        flip(board, coordinates, currentSymbol.SymbolToStatus());
        refreshValid(board, currentSymbol.SymbolToStatus().opp());
    }

    private void flip(Board board, int[] input, CellStatus currentPiece) {
        for (int[] direction : DIRECTIONS) {
            flipOrCheckInDirection(board, direction, input[0], input[1], currentPiece, true);
        }
    }

    @Override
    public boolean shouldPass() {
        return isWaitingForPass;
    }

    @Override
    public boolean isOver(Board board) {
        return isOver || board.isFull();
    }

    @Override
    public PlayerSymbol getWinner(Board board) {
        return board.getBlack() > board.getWhite() ? PlayerSymbol.BLACK :
               board.getBlack() < board.getWhite() ? PlayerSymbol.WHITE :
               PlayerSymbol.TIE;
    }

    private void refreshValid(Board board, CellStatus type) {
        // Clear all valid positions
        board.getCellBoard().forEach(cell -> cell.setValid(false));

        // Check if current player has any valid moves
        boolean hasValidMove = board.getCellBoard().stream()
                .anyMatch(cell -> isValidPosition(board, cell, type));

        isWaitingForPass = !hasValidMove;

        // If no moves, check if the game should end
        if (isWaitingForPass) {
            boolean opponentHasMove = board.getCellBoard().stream()
                    .anyMatch(cell -> isValidPosition(board, cell, type.opp()));
            if (!opponentHasMove) {
                isWaitingForPass = false;
                isOver = true;
            }
        }
    }

    private boolean isValidPosition(Board board, Cell cell, CellStatus currentPiece) {
        if (cell.getStatus() != CellStatus.EMPTY) return false;

        for (int[] direction : DIRECTIONS) {
            if (flipOrCheckInDirection(board, direction, cell.getX(), cell.getY(), currentPiece, false)) {
                return true;
            }
        }
        return false;
    }

    private boolean isInBoard(int size, int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    private boolean flipOrCheckInDirection(Board board, int[] direction, int x, int y,
                                          CellStatus currentPiece, boolean flipMode) {
        int dx = direction[0], dy = direction[1];
        int xp = x + dx, yp = y + dy;

        // Must start with an opponent's piece
        if (!isInBoard(8, xp, yp) || board.getCellAt(xp, yp).getStatus() != currentPiece.opp()) {
            return false;
        }

        // Traverse in direction until we find a matching piece or an empty cell
        while (isInBoard(8, xp += dx, yp += dy)) {
            CellStatus status = board.getCellAt(xp, yp).getStatus();

            if (status == CellStatus.EMPTY) return false;
            if (status != currentPiece) continue;

            // If in check mode, return true
            if (!flipMode) return true;

            // Flip all pieces back to the original position
            while (xp != x || yp != y) {
                xp -= dx;
                yp -= dy;
                Cell cell = board.getCellAt(xp, yp);
                cell.flip();
                board.adjustPieceCount(cell.getStatus() == CellStatus.BLACK ? CellStatus.BLACK : CellStatus.WHITE);
            }
            return true;
        }
        return false;
    }
}