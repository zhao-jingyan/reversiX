package io.github.zhaojingyan.model.game;

import io.github.zhaojingyan.model.enums.PieceStatus;

public class Board {
    // 棋盘属性
    private final Piece[][] board;   // 存储棋子信息
    private final boolean[][] valid; // 存储有效位置
    public int white;         // 白子数量
    public int black;         // 黑子数量
    public int size;

    public Board(int size) {
        this.size = size;
        board = new Piece[size][size];
        valid = new boolean[size][size];
        white = 0;
        black = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Piece();
                valid[i][j] = false;
            }
        }
    }

    public void setPiece(int[] coordinates, PieceStatus pieceStatus) {
        board[coordinates[0]][coordinates[1]].setPiece(pieceStatus);
        this.valid[coordinates[0]][coordinates[1]] = false;
        switch (pieceStatus) {
            case WHITE -> this.white++;
            case BLACK -> this.black++;
            case EMPTY -> this.valid[coordinates[0]][coordinates[1]] = true;
            case OBSTACLE -> {}
        } 
    }

    public void setValid(int[] coordinates, boolean isValid) {
        this.valid[coordinates[0]][coordinates[1]] = isValid;
    }

    // Getters
    public int getWhite() { return white; }
    public int getBlack() { return black; }
    public Piece[][] getPieceBoard() { return board; }
    public boolean[][] getValidBoard() { return valid; }
    public int getRow() { return size; }
    public int getCol() { return size; }
    public PieceStatus getPieceStatus(int[] coordinates) { return board[coordinates[0]][coordinates[1]].getStatus(); }
    public boolean isValid(int[] coordinates) { return valid[coordinates[0]][coordinates[1]]; }
    public boolean isEmpty(int[] coordinates) { return board[coordinates[0]][coordinates[1]].getStatus() == PieceStatus.EMPTY; }

    public boolean isFull(){
        for (Piece[] row : board)
            for (Piece item : row)
                if (item.getStatus() == PieceStatus.EMPTY)
                    return false;
        return true;
    }
}
