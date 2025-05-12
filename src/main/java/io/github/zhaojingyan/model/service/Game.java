package io.github.zhaojingyan.model.service;

import io.github.zhaojingyan.model.entities.Board;
import io.github.zhaojingyan.model.entities.Player;
import io.github.zhaojingyan.model.enums.GameErrorCode;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.service.rule.Rule;
import io.github.zhaojingyan.model.service.rule.RuleFactory;


public class Game{
    private final int gameNumber;
    private final Rule gamerule;
    private final Board board;
    private final PlayerController playerController;
    private int round;
    private boolean isOver;
    private boolean isWaitingForPass;
    private PlayerSymbol winner;

    protected Game(int gameNumber,GameMode gameMode, String p1, String p2) {
        this.gameNumber = gameNumber;
        this.gamerule = RuleFactory.createRule(gameMode);
        this.playerController = new PlayerController(p1, p2);
        this.board = new Board(gameMode.getSize());
        this.isOver = false;
        this.isWaitingForPass = false;
        this.winner = null;
        this.round = 1;
        gamerule.initializeBoard(board);
    }

    public void update(InputInformation inputInformation) throws GameException {
        // isOver锁死
        if (isOver)
            throw new GameException(GameErrorCode.GAME_ALREADY_OVER, "This game is already over!");
        // 处理passInformation
        else{
            inputInformation.handle(isWaitingForPass, board, playerController.getCurrentSymbol(), getGameMode());
            ruleUpdate(board, inputInformation);
            round++;
        }
    }
    
    //extracted ruleUpdate
    private void ruleUpdate(Board board, InputInformation inputInformation) {
        gamerule.updateBoard(board,inputInformation,playerController.getCurrentSymbol());
        isWaitingForPass = gamerule.shouldPass();
        isOver = gamerule.isOver(board);
        winner = gamerule.getWinner(board);
        playerController.changeSpot();
    }

    //Getters
    public int getGameNumber() { return gameNumber; }
    public boolean isOver() { return isOver; }
    public boolean isWaitingForPass() { return isWaitingForPass; }
    public PlayerSymbol getWinner() { return winner; }
    public GameMode getGameMode() { return gamerule.getGameMode(); }
    public Board getBoard() { return board; }
    public int getRound() { return round; }
    public String getP1Name() { return playerController.getPlayer1().getName(); }
    public String getP2Name() { return playerController.getPlayer2().getName(); }
    public Player getChargePlayer() { return playerController.getChargePlayer(); }
}
