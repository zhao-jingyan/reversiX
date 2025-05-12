package io.github.zhaojingyan.model.entities;

import io.github.zhaojingyan.model.enums.PlayerSymbol;

public class Player {
    private final String name;            // 玩家名称
    private final PlayerSymbol symbol;  // 执棋

    // 构造函数
    public Player(String name, PlayerSymbol piecetype) {
        this.name = name;
        this.symbol = piecetype;
    }

    // Getters
    public String getName() { return name; }
    public PlayerSymbol getSymbol() { return symbol; }
}

