package io.github.zhaojingyan.model.service;

import io.github.zhaojingyan.model.entities.Player;
import io.github.zhaojingyan.model.enums.PlayerSymbol;

public class PlayerController {
    private final Player p1;          // 玩家1
    private final Player p2;          // 玩家2
    private Player currentPlayer;      // 当前行动玩家

    /**
     * 构造函数
     * @param p1Name 玩家1名称
     * @param p2Name 玩家2名称
     */
    public PlayerController(String p1Name, String p2Name) {
        this.p1 = new Player(p1Name, PlayerSymbol.BLACK);
        this.p2 = new Player(p2Name, PlayerSymbol.WHITE);
        this.currentPlayer = p1;
    }

    // Getters
    public Player getChargePlayer() { return currentPlayer; }
    public PlayerSymbol getCurrentSymbol() { return currentPlayer.getSymbol();}
    public Player getPlayer1() { return p1; }
    public Player getPlayer2() { return p2; }

    //change the current player
    public void changeSpot() {
        currentPlayer = (currentPlayer == p1) ? p2 : p1;
    }
}
