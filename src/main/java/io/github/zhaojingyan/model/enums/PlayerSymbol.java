package io.github.zhaojingyan.model.enums;

public enum PlayerSymbol {
    BLACK,
    WHITE,
    TIE,
    VOID;

    public CellStatus SymbolToStatus(){
        switch (this) {
            case BLACK -> {
                return CellStatus.BLACK;
            }
            case WHITE -> {
                return CellStatus.WHITE;
            }
            default -> throw new IllegalArgumentException("UnSupported PlayerSymbol: " + this);
        }
    }
}
