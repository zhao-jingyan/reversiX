package io.github.zhaojingyan.model.game;

import io.github.zhaojingyan.model.enums.PieceStatus;

public class Player {
    private final String name;            // 玩家名称
    private final PieceStatus piecetype;  // 执棋

    // 构造函数
    public Player(String name, PieceStatus piecetype) {
        this.name = name;
        this.piecetype = piecetype;
    }

    // Getters
    public String getName() { return name; }
    public PieceStatus getPiecetype() { return piecetype; }
}

