package io.github.zhaojingyan.model.service;

import io.github.zhaojingyan.model.enums.OutputType;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.ui.interfaces.InputInterface;
import io.github.zhaojingyan.ui.interfaces.OutputInterface;



public class MainController {
    private static final MainController instance = new MainController();
    private static OutputType outputType;
    private static InputInterface input;
    private static OutputInterface output;

    private MainController() {
        outputType = OutputType.REFRESH;
    }

    public static MainController getInstance() {
        return instance;
    }

    public static void setInputOutput(InputInterface input, OutputInterface output) {
        MainController.input = input;
        MainController.output = output;
    }

    // 游戏循环
    public static void gameLoop() {
        checkGameOver(); // 修复：首次进入时先判断游戏是否结束
        output.print(OutputBuilder.create(outputType, GameManager.getInstance()));
        while (logicShouldContinue()) {
            try {
                InputInformation inputInfo = input.getInput();
                outputType = inputInfo.getOutputType();
                inputInfo.preHandle();
                checkGameOver();
                output.print(OutputBuilder.create(outputType, GameManager.getInstance()));
            } catch (GameException e) {
                outputType = OutputType.INVALID_INPUT;
                output.printError(e, OutputBuilder.create(outputType, GameManager.getInstance()));
            }
        }
    }

    private static void checkGameOver() {
        if (outputType != OutputType.QUIT) {
            if (GameManager.getInstance().isCurrentGameOver()) {
                outputType = OutputType.GAME_OVER;
            }
            if (GameManager.getInstance().isAllGamesOver()) {
                outputType = OutputType.ALL_GAMES_OVER;
            }
        }
    }

    private static boolean logicShouldContinue() {
        return outputType != OutputType.QUIT && outputType != OutputType.ALL_GAMES_OVER;
    }

}

