package io.github.zhaojingyan.model.entities;

import java.io.Serializable;

import io.github.zhaojingyan.model.enums.PieceStatus;
import io.github.zhaojingyan.model.enums.PlayerSymbol;

public class Board implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 维护的数据 */
    private final Piece[][] board; // 存储棋子信息
    private final boolean[][] valid; // 存储有效位置
    public int white; // 白子数量
    public int black; // 黑子数量
    public int size; // 棋盘大小
    public int whiteBomb; // 白子炸弹数量
    public int blackBomb; // 黑子炸弹数量

    public Board(int size) {
        this.size = size;
        board = new Piece[size][size];
        valid = new boolean[size][size];
        white = 0;
        black = 0;
        whiteBomb = 3;
        blackBomb = 2;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Piece(i, j);
                valid[i][j] = false;
            }
        }
    }

    public void setPiece(int[] coordinates, PieceStatus pieceStatus, PlayerSymbol playerSymbol) {
        board[coordinates[0]][coordinates[1]].setPiece(pieceStatus);
        this.valid[coordinates[0]][coordinates[1]] = false;
        switch (pieceStatus) {
            case WHITE -> this.white++;
            case BLACK -> this.black++;
            case EMPTY -> this.valid[coordinates[0]][coordinates[1]] = true;
            case OBSTACLE -> {
            }
            case BOMB -> {
                if (playerSymbol == PlayerSymbol.WHITE) {
                    this.whiteBomb--;
                } else if (playerSymbol == PlayerSymbol.BLACK) {
                    this.blackBomb--;
                }
            }
        }
    }

    public void setValid(int[] coordinates, boolean isValid) {
        this.valid[coordinates[0]][coordinates[1]] = isValid;
    }

    // Getters
    public int getSize() {
        return size;
    }

    public int getWhite() {
        return white;
    }

    public int getBlack() {
        return black;
    }

    public int getWhiteBomb() {
        return whiteBomb;
    }

    public int getBlackBomb() {
        return blackBomb;
    }

    public Piece getPieceAt(int x, int y) {
        return board[x][y];
    }

    public Piece[][] getPieceBoard() {
        return board;
    }

    public boolean[][] getValidBoard() {
        return valid;
    }

    public int getRow() {
        return size;
    }

    public int getCol() {
        return size;
    }

    public PieceStatus getPieceStatus(int[] coordinates) {
        return board[coordinates[0]][coordinates[1]].getStatus();
    }

    public boolean isValid(int[] coordinates) {
        return valid[coordinates[0]][coordinates[1]];
    }

    public boolean isEmpty(int[] coordinates) {
        return board[coordinates[0]][coordinates[1]].getStatus() == PieceStatus.EMPTY;
    }

    public boolean isFull() {
        for (Piece[] row : board)
            for (Piece item : row)
                if (item.getStatus() == PieceStatus.EMPTY)
                    return false;
        return true;
    }

    public boolean isOutOfBoard(int[] coordinates) {
        return coordinates[0] < 0 || coordinates[0] >= size || coordinates[1] < 0 || coordinates[1] >= size;
    }

    public boolean isOpp(PieceStatus type, int[] coordinates) {
        return board[coordinates[0]][coordinates[1]].getStatus() == type.opp();
    }

    public boolean haveBomb(PlayerSymbol type) {
        if (type == PlayerSymbol.WHITE) {
            return whiteBomb > 0;
        } else if (type == PlayerSymbol.BLACK) {
            return blackBomb > 0;
        }
        return false;
    }
}
