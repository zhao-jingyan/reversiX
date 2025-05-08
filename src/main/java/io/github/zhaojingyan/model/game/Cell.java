package io.github.zhaojingyan.model.game;

import io.github.zhaojingyan.model.enums.CellStatus;

public class Cell {
        // 棋子属性
    private CellStatus status;
    private boolean isValid; // 是否有效
    private final int x;
    private final int y;

    // construct a piece
    // can only be accessed by board
    protected Cell(int x, int y, boolean isValid) {
        this.x = x;
        this.y = y;
        this.isValid = isValid;
        status = CellStatus.EMPTY;
    }

    // 棋子操作
    public void setPiece(CellStatus status) { this.status = status; }
    public void setValid(boolean isValid) { this.isValid = isValid; }
    public void flip() {
        status = status.opp();
    }

    // Getters
    public CellStatus getStatus() { return status; }
    public boolean getValid() { return isValid; }
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isValid() { return isValid; }
}