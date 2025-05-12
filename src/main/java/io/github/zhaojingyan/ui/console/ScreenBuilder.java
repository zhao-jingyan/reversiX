package io.github.zhaojingyan.ui.console;

import io.github.zhaojingyan.model.entities.Piece;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.output.OutputInformation;

public class ScreenBuilder {

    protected ScreenBuilder() {
    }

    protected Screen constructBoardPanel(OutputInformation output) {
        int boardSize = output.getGameInfo().getBoard().getSize();
        Screen rawScreen = new Screen(boardSize + 1, boardSize + 1);
        for (Piece[] row : output.getGameInfo().getBoard().getPieceBoard()) {
            for (Piece piece : row) {
                switch (piece.getStatus()) {
                    case BLACK -> rawScreen.setPixel(piece.getX() + 1, piece.getY() + 1, '○');
                    case WHITE -> rawScreen.setPixel(piece.getX() + 1, piece.getY() + 1, '●');
                    case EMPTY -> {
                        if (output.getGameInfo().getBoard().isValid(new int[] { piece.getX(), piece.getY() })
                                && output.getGameInfo().getGameMode() == GameMode.REVERSI) {
                            rawScreen.setPixel(piece.getX() + 1, piece.getY() + 1, '+');
                        } else {
                            rawScreen.setPixel(piece.getX() + 1, piece.getY() + 1, '·');
                        }
                    }
                    case OBSTACLE -> rawScreen.setPixel(piece.getX() + 1, piece.getY() + 1, '#');
                    case BOMB -> rawScreen.setPixel(piece.getX() + 1, piece.getY() + 1, '@');
                }
            }
        }
        rawScreen.setPixel(0, 0, ' ');
        for (int i = 0; i < boardSize; i++) {
            rawScreen.setPixel(i + 1, 0, (char) ('A' + i));
            rawScreen.setPixel(0, i + 1, (char) (i < 9 ? '1' + i : 'A' + (i - 9)));
        }
        return rawScreen;
    }

    protected Screen constructPlayerPanel(OutputInformation output) {
        // Logic to construct the center panel as a string
        Screen centerPanel = new Screen(25, 5);
        centerPanel.setRow(1, String.format("  Game %d", output.getGameInfo().getCurrentGameNumber(), ""));
        centerPanel.setRow(2, String.format("  Player: %-10s %c", output.getGameInfo().getPlayer1Name(),
                output.getGameInfo().getChargeSymbol() == PlayerSymbol.BLACK ? '○' : ' '));
        centerPanel.setRow(3, String.format("  Player: %-10s %c", output.getGameInfo().getPlayer2Name(),
                output.getGameInfo().getChargeSymbol() == PlayerSymbol.WHITE ? '●' : ' '));
        if (output.getGameInfo().getGameMode() == GameMode.GOMOKU) {
            centerPanel.setRow(4, String.format("  Current Round: %d", output.getGameInfo().getCurrentRound()));
        }
        return centerPanel;
    }

    protected Screen constructGameInfoPanel(OutputInformation output) {
        switch (output.getGameInfo().getGameMode()) {
            case REVERSI -> {
                Screen panel = new Screen(10, 3);
                panel.setRow(0, String.format("Count"));
                panel.setRow(1, String.format("  %d", output.getGameInfo().getBlack()));
                panel.setRow(2, String.format("  %d", output.getGameInfo().getWhite()));
                return panel;
            }
            case GOMOKU -> {
                Screen panel = new Screen(10, 4);
                panel.setRow(0, String.format("Bombs"));
                panel.setRow(1, String.format("  %d", output.getGameInfo().getBlackBomb()));
                panel.setRow(2, String.format("  %d", output.getGameInfo().getWhiteBomb()));
                return panel;
            }
            default -> {
                return new Screen(0, 0);
            }
        }
    }
    
    protected Screen constructGameListPanel(OutputInformation output) {
        int rows = output.getGlobalInfo().getGameList().length + 2;
        Screen rightPanel = new Screen(30, rows);
        rightPanel.setRow(1, "  Game List");
        for (int i = 0; i < output.getGlobalInfo().getGameList().length; i++) {
            rightPanel.setRow(i + 2, String.format("  %d. %s", i + 1, output.getGlobalInfo().getGameList()[i]));
        }
        return rightPanel;
    }
}
