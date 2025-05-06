package io.github.zhaojingyan.ui.console;

import java.io.IOException;

import io.github.zhaojingyan.controller.game.GameException;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.PieceStatus;
import io.github.zhaojingyan.model.enums.PlayerSymbol;
import io.github.zhaojingyan.model.game.Piece;
import io.github.zhaojingyan.model.output.OutputInformation;
import io.github.zhaojingyan.ui.interfaces.OutputInterface;

public class ConOutput implements OutputInterface {

    public ConOutput() {
    }

    // 打印信息
    @Override
    public void print(OutputInformation output) {
        switch (output.getOutputType()) {
            case REFRESH -> {
                clear();
                printInfo(output);
                System.out.printf(
                        "\n< Coordinates(A1-H8) | Game Num(1-%d) | New Game(peace,reversi,gomoku) | Pass(pass) | Quit(quit) >\n",
                        output.getGlobalInfo().getGameList().length);
                System.out.printf("[%-10s %c]%s> ", output.getGameInfo().getChargePlayerName(),
                        output.getGameInfo().getChargeSymbol() == PlayerSymbol.BLACK ? '○' : '●',
                        output.getGameInfo().isWaitingForPass() ? " (should pass) " : " ");
            }
            case GAME_OVER -> {
                clear();
                printInfo(output);
                printGameOver(output);
                System.out.printf("\n< Game Num(1-%d) | New Game(peace,reversi,gomoku) | Quit(quit) >\n",
                        output.getGlobalInfo().getGameList().length);
                System.out.printf("[%-10s %c] > ", output.getGameInfo().getChargePlayerName(),
                        output.getGameInfo().getChargeSymbol() == PlayerSymbol.BLACK ? '○' : '●');
            }
            case QUIT -> {
                System.out.println("Goodbye!");
                System.exit(0);
            }
            case ALL_GAMES_OVER -> {
                clear();
                printInfo(output);
                printGameOver(output);
                System.out.println("\nAll games are over!");
                System.exit(0);
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + output.getOutputType());
        }
    }

    // 打印游戏结束信息
    private static void printGameOver(OutputInformation output) {
        System.out.println("\nGame Over!");
        if (output.getGlobalInfo().getCurrentGameMode() == GameMode.REVERSI) {
            System.out.printf("Player[%s ○]: %d\n", output.getGameInfo().getPlayer1Name(), output.getGameInfo().getBlack());
            System.out.printf("Player[%s ●]: %d\n", output.getGameInfo().getPlayer2Name(), output.getGameInfo().getWhite());
        }
        if(output.getGameInfo().getWinner() != null) {
            switch (output.getGameInfo().getWinner()) {
                case BLACK -> System.out.printf("Player[%s ○] wins!\n", output.getGameInfo().getPlayer1Name());
                case WHITE -> System.out.printf("Player[%s ●] wins!\n", output.getGameInfo().getPlayer2Name());
                default -> System.out.println("It's a tie!");
            }
        }
    }

    // 打印游戏信息
    private static void printInfo(OutputInformation output) {
        // head
        System.out.print("   ");
        for (char c = 'A'; c < 'A' + 15; c++) {
            System.out.print(c + " ");
        }
        System.out.println();

        for (int row = 0; row < 15; row++) {
            System.out.printf("%2d ", row + 1);
            for (int column = 0; column < 15; column++) {
                Piece item = output.getGameInfo().getBoard().getPieceBoard()[row][column];
                if (output.getGameInfo().getBoard().getValidBoard()[row][column] && output.getGlobalInfo().getCurrentGameMode() == GameMode.REVERSI) {
                    System.out.printf("+ ");
                } else {
                    switch (item.getStatus()) {
                        case EMPTY -> System.out.printf("· ");
                        case BLACK -> System.out.printf("○ ");
                        case WHITE -> System.out.printf("● ");
                        case OBSTACLE -> System.out.printf("x ");
                    }
                }
            }
            System.out.println();
        }
    }

    // 打印玩家信息
    private static void printPlayerInfo(OutputInformation output, String playerName, PieceStatus pieceType, int score) {
        System.out.printf("   player[%-10s] %c", playerName,
                output.getGameInfo().getChargeSymbol().SymbolToStatus() == pieceType ? (pieceType == PieceStatus.BLACK ? '○' : '●')
                        : ' ');
        if (output.getGlobalInfo().getCurrentGameMode() == GameMode.REVERSI) {
            System.out.printf(" %-2d%-3s", score, "");
        } else {
            System.out.printf("%-6s", "");
        }
    }

    @Override
    public void printError(GameException e, OutputInformation output) {
        System.err.println(e.getMessage());
        System.out.printf(
            "\n< Coordinates(A1-H8) | Game Num(1-%d) | New Game(peace,reversi,gomoku) | Pass(pass) | Quit(quit) >\n",
            output.getGlobalInfo().getGameList().length);
        System.out.printf("[%-10s %c]%s> ", output.getGameInfo().getChargePlayerName(),
        output.getGameInfo().getChargeSymbol() == PlayerSymbol.BLACK ? '○' : '●',
        output.getGameInfo().isWaitingForPass() ? " (should pass) " : " ");
    }

    // 清除控制台
    private static void clear() {
        try {
            // 获取操作系统名称
            String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                // 在 Windows 系统中，使用 cls 命令清除控制台
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // 在其他系统中，使用 clear 命令清除控制台
                new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
        }
    }
}
