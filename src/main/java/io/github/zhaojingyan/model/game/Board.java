package io.github.zhaojingyan.model.game;

import java.util.ArrayList;

import io.github.zhaojingyan.model.enums.CellStatus;
import io.github.zhaojingyan.model.enums.PlayerSymbol;

public class Board {
    // 棋盘属性
    private final ArrayList<Cell> board;   // 存储棋子信息
    public int white;         // 白子数量
    public int black;         // 黑子数量
    public int size;
    public int whiteBomb;   // 白子炸弹数量
    public int blackBomb;   // 黑子炸弹数量
    private int emptyNum;

    public Board(int size) {
        this.size = size;
        this.emptyNum = size * size;
        board = new ArrayList<>(size * size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board.add(new Cell(i, j, true));
            }
        }
        white = 0;
        black = 0;
        whiteBomb = 3;
        blackBomb = 2;
    }

    private Cell getCell(int x, int y) {
        if (x <0 || x >= size || y <0 || y >= size) {
        throw new IndexOutOfBoundsException("坐标超出范围");
        }
        int index = y * size + x;
        return board.get(index);
    }

    public void setPiece(int[] coordinates, CellStatus pieceStatus, PlayerSymbol playerSymbol) {
        int x = coordinates[0];
        int y = coordinates[1];
        getCell(x,y).setPiece(pieceStatus);
        getCell(x,y).setValid(false);

        switch (pieceStatus) {
            case WHITE -> {
                this.white++;
                this.emptyNum--;
            }
            case BLACK -> {
                this.black++;
                this.emptyNum--;
            }
            case EMPTY -> getCell(x,y).setValid(true);
            case OBSTACLE -> {
                this.emptyNum--;
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
        int x = coordinates[0];
        int y = coordinates[1];
        getCell(x,y).setValid(isValid);
    }

    public void adjustPieceCount(CellStatus flippedPiece) {
        if (flippedPiece == CellStatus.BLACK) {
            black++;
            white--;
        } else if (flippedPiece == CellStatus.WHITE) {
            black--;
            white++;
        }
    }

    // Getters
    public int getSize() { return size; }
    public int getWhite() { return white; }
    public int getBlack() { return black; }
    public int getWhiteBomb() { return whiteBomb; }
    public int getBlackBomb() { return blackBomb; }
    public Cell getCellAt(int x, int y) { return getCell(x,y); }
    public ArrayList<Cell> getCellBoard() { return board; }
    public int getRow() { return size; }
    public int getCol() { return size; }
    public boolean isValid(int[] coordinates) { return getCell(coordinates[0], coordinates[1]).getValid(); }
    public boolean isEmpty(int[] coordinates) { return getCell(coordinates[0], coordinates[1]).getStatus() == CellStatus.EMPTY; }

    public boolean isOutOfBoard(int[] coordinates) {
        return coordinates[0] < 0 || coordinates[0] >= size || coordinates[1] < 0 || coordinates[1] >= size;
    }
    public boolean isOpp(CellStatus type, int[] coordinates) {
        return getCell(coordinates[0], coordinates[1]).getStatus() == type.opp();
    }
    public boolean haveBomb(PlayerSymbol type) {
        if (type == PlayerSymbol.WHITE) {
            return whiteBomb > 0;
        } else if (type == PlayerSymbol.BLACK) {
            return blackBomb > 0;
        }
        return false;
    }
    public boolean isFull() {
        return emptyNum == 0;
    }
}
