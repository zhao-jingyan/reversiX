package io.github.zhaojingyan.model.output;

import java.util.ArrayList;

import io.github.zhaojingyan.controller.game.Game;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.game.Cell;
import io.github.zhaojingyan.model.game.Player;
public class GameInfo {

    private final ArrayList<Cell> board;
    private final int boardSize;
    private final String player1Name;
    private final String player2Name;
    private final Player chargePlayer;
    private final PlayerSymbol winner;
    private final int currentGameNumber;
    private final int currentRound;
    private final int white;
    private final int black;
    private final int whiteBomb;
    private final int blackBomb;
    private final boolean isWaitingForPass;
    private final GameMode gameMode;

    public GameInfo(Game game) {
        this.gameMode = game.getGameMode();
        this.boardSize = game.getBoard().getSize();
        this.board = game.getBoard().getCellBoard();
        this.player1Name = game.getP1Name();
        this.player2Name = game.getP2Name();
        this.chargePlayer = game.getChargePlayer();
        this.currentGameNumber = game.getGameNumber();
        this.currentRound = game.getRound();
        this.white = game.getBoard().getWhite();
        this.black = game.getBoard().getBlack(); // Updated to use getBoard().getBlack()
        this.whiteBomb = game.getBoard().getWhiteBomb();
        this.blackBomb = game.getBoard().getBlackBomb();
        this.isWaitingForPass = game.isWaitingForPass();
        this.winner = game.getWinner();
    }

    // Getter方法
    public ArrayList<Cell> getCellBoard() { return board; }
    public int getBoardSize() { return boardSize; }
    public String getPlayer1Name() { return player1Name; }
    public String getPlayer2Name() { return player2Name; }
    public int getCurrentGameNumber() { return currentGameNumber; }
    public int getCurrentRound() { return currentRound; }
    public int getWhite() { return white; }
    public int getBlack() { return black; }
    public int getWhiteBomb() { return whiteBomb; }
    public int getBlackBomb() { return blackBomb; }
    public PlayerSymbol getWinner() { return winner; }
    public String getChargePlayerName() { return chargePlayer.getName(); }
    public PlayerSymbol getChargeSymbol() { return chargePlayer.getSymbol(); }
    public boolean isWaitingForPass() { return isWaitingForPass;}
    public GameMode getGameMode() { return gameMode; }

}
