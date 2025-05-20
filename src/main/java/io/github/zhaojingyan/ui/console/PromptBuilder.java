package io.github.zhaojingyan.ui.console;

import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.output.OutputInformation;

public class PromptBuilder {

    protected PromptBuilder() {
    }

    protected String buildPrompt(OutputInformation output) {
        StringBuilder prompt = new StringBuilder();
        switch (output.getOutputType()) {
            case QUIT -> prompt.append(buildGoodbye());
            case GAME_OVER -> {
                prompt.append(buildGameOver(output));
                prompt.append(buildIndicator(output));
            }
            case REFRESH, INVALID_INPUT -> prompt.append(buildIndicator(output));
        }
        return prompt.toString();
    }

    private static String buildGoodbye() {
        return "Goodbye!";
    }

    private static String buildIndicator(OutputInformation output) {
        return String.format("""
                < Coordinates(%s) | Game Num(1-%d) | New Game(peace,reversi,gomoku) | Pass(pass) | Quit(quit) >
                [%-10s %c]%s >""",
                CoordinatesIndicator(output.getGameInfo().getGameMode()),
                output.getGlobalInfo().getGameList().length,
                output.getGameInfo().getChargePlayerName(),
                output.getGameInfo().getChargeSymbol() == PlayerSymbol.BLACK ? '○' : '●',
                output.getGameInfo().isWaitingForPass() ? " (should pass) " : "");
    }

    private static String buildGameOver(OutputInformation output) {
        StringBuilder gameOver = new StringBuilder();
        gameOver.append("Game Over!\n\n");
        if (output.getGameInfo().getGameMode() == GameMode.REVERSI) {
            gameOver.append(String.format("Player[%s ○]: %d\n", output.getGameInfo().getPlayer1Name(),
                    output.getGameInfo().getBlack()));
            gameOver.append(String.format("Player[%s ●]: %d\n", output.getGameInfo().getPlayer2Name(),
                    output.getGameInfo().getWhite()));
        }
        switch (output.getGameInfo().getWinner()) {
            case BLACK ->
                gameOver.append(String.format("Player[%s ○] wins!\n\n", output.getGameInfo().getPlayer1Name()));
            case WHITE ->
                gameOver.append(String.format("Player[%s ●] wins!\n\n", output.getGameInfo().getPlayer2Name()));
            case TIE -> 
                gameOver.append("It's a tie!\n\n");
            case VOID -> {}
        }
        return gameOver.toString();
    }

    private static String CoordinatesIndicator(GameMode gameMode) {
        int size = gameMode.getSize();
        StringBuilder sb = new StringBuilder();
        sb.append("1A-");
        sb.append((char) (size < 9 ? '1' + size - 1 : 'A' + (size - 10)));
        sb.append((char) ('A' + size - 1));
        return sb.toString();
    }
}