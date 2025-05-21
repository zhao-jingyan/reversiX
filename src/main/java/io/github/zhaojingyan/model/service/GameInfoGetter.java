package io.github.zhaojingyan.model.service;

import io.github.zhaojingyan.model.entities.Board;
import io.github.zhaojingyan.model.entities.Player;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.PlayerSymbol;

public class GameInfoGetter {

    private final Board board;
    private final String player1Name;
    private final String player2Name;
    private final Player chargePlayer;
    private final PlayerSymbol winner;
    private final int currentGameNumber;
    private final int currentRound;
    private final int white;
    private final int black;
    private final boolean isWaitingForPass;
    private final GameMode gameMode;

    public GameInfoGetter(Game game) {
        this.gameMode = game.getGameMode();
        this.board = game.getBoard();
        this.player1Name = game.getP1Name();
        this.player2Name = game.getP2Name();
        this.chargePlayer = game.getChargePlayer();
        this.currentGameNumber = game.getGameNumber();
        this.currentRound = game.getRound();
        this.white = game.getBoard().getWhite();
        this.black = game.getBoard().getBlack(); // Updated to use getBoard().getBlack()
        this.isWaitingForPass = game.isWaitingForPass();
        this.winner = game.getWinner();
    }

    // Getter方法
    public Board getBoard() {
        return board;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public int getCurrentGameNumber() {
        return currentGameNumber;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getWhite() {
        return white;
    }

    public int getBlack() {
        return black;
    }

    public int getWhiteBomb() {
        return board.getWhiteBomb();
    }

    public int getBlackBomb() {
        return board.getBlackBomb();
    }

    public PlayerSymbol getWinner() {
        return winner;
    }

    public String getChargePlayerName() {
        return chargePlayer.getName();
    }

    public PlayerSymbol getChargeSymbol() {
        return chargePlayer.getSymbol();
    }

    public boolean isWaitingForPass() {
        return isWaitingForPass;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

}
