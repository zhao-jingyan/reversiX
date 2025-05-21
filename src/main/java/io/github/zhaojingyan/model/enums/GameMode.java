package io.github.zhaojingyan.model.enums;

public enum GameMode {
    PEACE(8),
    REVERSI(8),
    GOMOKU(15);

    private final int size;

    GameMode(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
