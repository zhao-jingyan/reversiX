package io.github.zhaojingyan.model.entities;

import io.github.zhaojingyan.model.enums.PieceStatus;

public class Piece {
        // 棋子属性
    private PieceStatus status;
    private final int x;
    private final int y;

    // construct a piece
    // can only be accessed by board
    protected Piece(int x, int y) {
        this.x = x;
        this.y = y;
        status = PieceStatus.EMPTY;
    }

    // 棋子操作
    public void setPiece(PieceStatus status) { this.status = status; }
    public void flip() {
        status = status.opp();
    }

    // Getters
    public PieceStatus getStatus() { return status; }
    public int getX() { return x; }
    public int getY() { return y; }
}