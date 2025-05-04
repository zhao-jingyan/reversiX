package io.github.zhaojingyan.controller.game;

import io.github.zhaojingyan.controller.rule.Rule;
import io.github.zhaojingyan.controller.rule.RuleFactory;
import io.github.zhaojingyan.model.enums.GameErrorCode;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.PieceStatus;
import io.github.zhaojingyan.model.game.Board;
import io.github.zhaojingyan.model.game.Player;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.input.imple.MoveInformation;
import io.github.zhaojingyan.model.input.imple.PassInformation;


public class Game {
    private final int gameNumber;
    private final Rule gamerule;
    private final PlayerController playerController;
    private final Board board;
    private int round;
    private boolean isOver;
    private boolean isWaitingForPass;
    private PieceStatus winner;

    protected Game(int gameNumber,GameMode gameMode, String p1, String p2, int boardSize) {
        this.gameNumber = gameNumber;
        this.gamerule = RuleFactory.createRule(gameMode);
        this.playerController = new PlayerController(p1, p2);
        this.board = new Board(boardSize);
        this.isOver = false;
        this.isWaitingForPass = false;
        this.winner = PieceStatus.EMPTY;
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
            round++;
        }
    }

    private void handleMoveInformation(MoveInformation moveInformation)
            throws GameException {
        int[] coordinate = moveInformation.getInfo();
        if (!board.isValid(coordinate)) {
            if (!board.isEmpty(coordinate))
                throw new GameException(GameErrorCode.CONFLICTING_MOVE, "Conflicting move! ["
                        + (char) ('A' + coordinate[1]) + (coordinate[0] + 1) + "] is already occupied");
            else
                throw new GameException(GameErrorCode.ILLEGAL_MOVE, "Illegal move! [" + (char) ('A' + coordinate[1])
                        + (coordinate[0] + 1) + "] is not a valid position");
        } 
        else {
            board.addPiece(coordinate, playerController.getCurrentPiece());
            ruleUpdate(board,moveInformation);
            playerController.changeSpot();
        }
    }

    private void handlePassInformation(InputInformation inputInformation) throws GameException {
        if (!isWaitingForPass)
            throw new GameException(GameErrorCode.MAY_NOT_PASS, "Cannot pass when there are valid moves");
        else {
            playerController.changeSpot();
            ruleUpdate(board,inputInformation);
        }
    }

    //extracted ruleUpdate
    private void ruleUpdate(Board board, InputInformation inputInformation) {
        gamerule.updateBoard(board,inputInformation,playerController.getCurrentPiece());
        isWaitingForPass = gamerule.shouldPass();
        isOver = gamerule.isOver(board);
        winner = gamerule.getWinner(board);
    }

    //Getters
    public int getGameNumber() { return gameNumber; }
    public boolean isOver() { return isOver; }
    public boolean isWaitingForPass() { return isWaitingForPass; }
    public PieceStatus getWinner() { return winner; }
    public GameMode getGameMode() { return gamerule.getGameMode(); }
    public Board getBoard() { return board; }
    public int getRound() { return round; }
    public String getP1Name() { return playerController.getPlayer1().getName(); }
    public String getP2Name() { return playerController.getPlayer2().getName(); }
    public Player getChargePlayer() { return playerController.getChargePlayer(); }
}
