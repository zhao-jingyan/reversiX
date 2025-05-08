package io.github.zhaojingyan.model.enums;

import io.github.zhaojingyan.model.game.Cell;

public enum CellStatus {

    BLACK {
        @Override
        public char getSymbol() {
            return '○';
        }

    },
    WHITE {
        @Override
        public char getSymbol() {
            return '●';
        }
    },
    EMPTY {
        @Override
        public char getSymbol(Cell cell, GameMode gameMode) {
            return (cell.getValid() && gameMode == GameMode.REVERSI) ? '+' : '·';
        }
    },
    OBSTACLE {

        @Override
        public char getSymbol() {
            return '#';
        }

    },
    BOMB {
        @Override
        public char getSymbol() {
            return '@';
        }
    };

    // 默认方法，适用于不需要额外参数的枚举值
    public char getSymbol() {
        throw new UnsupportedOperationException("This method should be overridden for specific enum values.");
    }

    // 重载方法，适用于需要额外参数的枚举值
    public char getSymbol(Cell cell, GameMode gameMode) {
        return getSymbol();
    }

    public CellStatus opp() {
        if (this == BLACK)
            return WHITE;
        if (this == WHITE)
            return BLACK;
        return this;
    }
}