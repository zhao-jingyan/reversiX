package io.github.zhaojingyan.model.enums;

public enum PlayerSymbol {
    BLACK,
    WHITE;

    public PieceStatus SymbolToStatus(){
        switch (this) {
            case BLACK -> {
                return PieceStatus.BLACK;
            }
            case WHITE -> {
                return PieceStatus.WHITE;
            }
            default -> throw new IllegalArgumentException("Unknown PlayerSymbol: " + this);
        }
    }
}
