package io.github.zhaojingyan.model.game;

import io.github.zhaojingyan.model.enums.PieceStatus;

public class Piece {
        // 棋子属性
    private PieceStatus status;

    // construct a piece
    // can only be accessed by board
    protected Piece() {
        status = PieceStatus.EMPTY;
    }

    // 棋子操作
    public void setPiece(PieceStatus status) { this.status = status; }
    public void flip() {
        status = status.opp();
    }

    // Getters
    public PieceStatus getStatus() { return status; }
}