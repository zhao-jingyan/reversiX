package io.github.zhaojingyan.ui.console;

import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.game.Cell;
import io.github.zhaojingyan.model.output.OutputInformation;
import io.github.zhaojingyan.model.output.Screen;

public class ScreenBuilder {

    protected ScreenBuilder() {
    }

    protected Screen constructBoardPanel(OutputInformation output) {
        int boardSize = output.getGameInfo().getBoardSize();
        Screen rawScreen = new Screen(boardSize + 1, boardSize + 1);
       for(Cell cell : output.getGameInfo().getCellBoard()) {
                char symbol = cell.getStatus().getSymbol(cell, output.getGameInfo().getGameMode());
                rawScreen.setPixel(cell.getX() + 1, cell.getY() + 1, symbol);
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
                return middlePanel(10, 3, "Score", output.getGameInfo().getBlack(),
                        output.getGameInfo().getWhite());
            }
            case GOMOKU -> {
                return middlePanel(10, 4, "Bomb", output.getGameInfo().getBlackBomb(),
                        output.getGameInfo().getWhiteBomb());
            }
            default -> {
                return new Screen(0, 0);
            }
        }
    }

    private Screen middlePanel(int width, int height,String title, int num1, int num2) {
        Screen panel = new Screen(width, height);
        panel.setRow(0, String.format(title));
        panel.setRow(1, String.format("  %d", num1));
        panel.setRow(2, String.format("  %d", num2));
        return panel;
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
