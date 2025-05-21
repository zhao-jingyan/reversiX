package io.github.zhaojingyan.model.enums;

public enum PieceStatus {
    EMPTY,
    OBSTACLE,
    BOMB,
    BLACK,
    WHITE;

    public PieceStatus opp() {
        if (this == BLACK)
            return WHITE;
        if (this == WHITE)
            return BLACK;
        return this;
    }
}
