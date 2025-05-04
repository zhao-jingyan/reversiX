package io.github.zhaojingyan.controller.game;

import io.github.zhaojingyan.model.enums.PieceStatus;
import io.github.zhaojingyan.model.game.Player;

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
        this.p1 = new Player(p1Name, PieceStatus.BLACK);
        this.p2 = new Player(p2Name, PieceStatus.WHITE);
        this.currentPlayer = p1;
    }

    // Getters
    public Player getChargePlayer() { return currentPlayer; }
    public PieceStatus getCurrentPiece() { return currentPlayer.getPiecetype();}
    public Player getPlayer1() { return p1; }
    public Player getPlayer2() { return p2; }

    //change the current player
    public void changeSpot() {
        currentPlayer = (currentPlayer == p1) ? p2 : p1;
    }
}
