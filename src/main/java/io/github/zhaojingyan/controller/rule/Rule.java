package io.github.zhaojingyan.controller.rule;

import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.PieceStatus;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.game.Board;
import io.github.zhaojingyan.model.input.InputInformation;

public interface Rule {

    GameMode getGameMode();

    void initializeBoard(Board board);

    void updateBoard(Board board, InputInformation info,PlayerSymbol currentSymbol);

    boolean shouldPass();

    boolean isOver(Board board);

    PieceStatus getWinner(Board board);
}
