package io.github.zhaojingyan.controller.game;

import io.github.zhaojingyan.controller.rule.Rule;
import io.github.zhaojingyan.controller.rule.RuleFactory;
import io.github.zhaojingyan.model.enums.GameErrorCode;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.PieceStatus;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.game.Board;
import io.github.zhaojingyan.model.game.Player;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.input.imple.BombInformation;
import io.github.zhaojingyan.model.input.imple.MoveInformation;
import io.github.zhaojingyan.model.input.imple.PassInformation;


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
        if (inputInformation instanceof PassInformation) {
            handlePassInformation(inputInformation);
        }
        // 处理moveInformation
        else if (inputInformation instanceof MoveInformation moveInformation) {
            handleMoveInformation(moveInformation);
        }
        else if(inputInformation instanceof BombInformation bombInformation) {
            handleBombInformation(bombInformation);
        }
        round++;
    }

    private void handleMoveInformation(MoveInformation moveInformation)
            throws GameException {
        int[] coordinate = moveInformation.getInfo();
        int x = coordinate[1];
        int y = coordinate[0];
        if (board.isOutOfBoard(coordinate)) {
            throw new GameException(GameErrorCode.INVALID_INPUT,"Invalid input");
        }
        if (!board.isValid(coordinate)) {
            if (!board.isEmpty(coordinate))
            throw new GameException(GameErrorCode.CONFLICTING_MOVE, "Conflicting move! ["
                + (char) (x < 9 ? '1' + x : 'A' + (x - 9)) + (char) ('A' + y) + "] is already occupied");
            else
            throw new GameException(GameErrorCode.ILLEGAL_MOVE, "Illegal move! [" + (char) (x < 9 ? '1' + x : 'A' + (x - 9))
                + (char) ('A' + y) + "] is not a valid position");
        } 
        else {
            board.setPiece(coordinate, playerController.getCurrentSymbol().SymbolToStatus(),null);
            ruleUpdate(board,moveInformation);
        }
    }

    private void handleBombInformation(BombInformation bombInformation)
            throws GameException {
        int[] coordinate = bombInformation.getInfo();
        int x = coordinate[1];
        int y = coordinate[0];
        if (board.isOutOfBoard(coordinate) || getGameMode() != GameMode.GOMOKU) {
            throw new GameException(GameErrorCode.INVALID_INPUT,"Invalid input");
        }
        else if (!board.haveBomb(playerController.getCurrentSymbol())){
            throw new GameException(GameErrorCode.OUT_OF_BOMB, "Running out of bomb! You have no bomb left");
        }
        else if (!board.isOpp(playerController.getCurrentSymbol().SymbolToStatus(),coordinate)) {
            throw new GameException(GameErrorCode.NOT_BOMB_TARGET, "Not a valid bomb target! [" + (char) (x < 9 ? '1' + x : 'A' + (x - 9))
                + (char) ('A' + y) + "] is not a valid target");
        } 
        else {
            board.setPiece(coordinate, PieceStatus.BOMB, playerController.getCurrentSymbol());
            ruleUpdate(board,bombInformation);
        }
    }


    private void handlePassInformation(InputInformation inputInformation) throws GameException {
        if (!isWaitingForPass)
            throw new GameException(GameErrorCode.MAY_NOT_PASS, "Cannot pass when there are valid moves");
        else {
            ruleUpdate(board,inputInformation);
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
