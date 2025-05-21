package io.github.zhaojingyan.ui.gui;

import io.github.zhaojingyan.App;
import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.enums.OutputType;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.input.imple.InputInformationFactory;
import io.github.zhaojingyan.model.output.OutputInformation;
import io.github.zhaojingyan.model.service.GameException;
import io.github.zhaojingyan.model.service.GameManager;
import io.github.zhaojingyan.model.service.MainController;
import io.github.zhaojingyan.model.service.OutputBuilder;
import io.github.zhaojingyan.ui.gui.panels.BoardPanel;
import io.github.zhaojingyan.ui.gui.panels.InfoPanel;
import io.github.zhaojingyan.ui.gui.panels.ListPanel;
import io.github.zhaojingyan.ui.gui.panels.NewGamePanel;
import io.github.zhaojingyan.ui.interfaces.OutputInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GuiOutput extends Application implements OutputInterface {
    private HBox hbox;
    private VBox boardBox;
    private HBox rightBox;
    private Scene scene;
    private final Label statusLabel = new Label();

    @Override
    public void start(Stage primaryStage) {
        hbox = new HBox(30);
        hbox.setStyle("-fx-padding: 30 40 30 40; -fx-alignment: top-left; -fx-fill-height: true;");
        boardBox = new VBox();
        rightBox = new HBox(20);
        scene = new Scene(new VBox(hbox, statusLabel), 1200, 600); // 下方加label
        statusLabel.setStyle("-fx-font-size: 18px; -fx-padding: 10 0 0 20; -fx-text-fill:rgb(0, 0, 0);");
        statusLabel.setText("");

        // 初始化时先用一个默认output刷新一次
        try {
            InputInformation inputInfo = InputInformationFactory.create(InputType.BOARDNUM, "1");
            inputInfo.preHandle();
            OutputType outputType = inputInfo.getOutputType();
            OutputInformation output = OutputBuilder.create(outputType, GameManager.getInstance());
            print(output);
        } catch (GameException e) {
        }

        primaryStage.setTitle("Reversi Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        // 设置输入输出并用新线程运行主循环，避免阻塞UI
        MainController.setInputOutput(GuiInput.getInstance(), this);
        new Thread(() -> {
            MainController.gameLoop();
        }, "GameLoopThread").start();
    }

    @Override
    public void print(OutputInformation output) {
        Platform.runLater(() -> {
            // 新建一套panel并刷新UI
            BoardPanel boardPanel = new BoardPanel(output.getGameInfo().getBoard().getSize());
            boardPanel.render(output);
            InfoPanel infoPanel = new InfoPanel();
            infoPanel.init();
            // 刷新pass按钮状态
            infoPanel.getPassButton().setDisable(!output.getGameInfo().isWaitingForPass());
            infoPanel.render(output);

            ListPanel listPanel = new ListPanel();
            listPanel.render(output);
            NewGamePanel newGamePanel = new NewGamePanel();
            newGamePanel.render(output);

            infoPanel.setMinHeight(boardPanel.getMinHeight());
            infoPanel.setPrefHeight(boardPanel.getPrefHeight());
            infoPanel.setMaxHeight(boardPanel.getMaxHeight());
            // 不要设置listPanel高度，交给render动态控制
            // listPanel.setPanelHeight(boardPanel.getMinHeight(),
            // boardPanel.getPrefHeight(), boardPanel.getMaxHeight());
            newGamePanel.setMinHeight(boardPanel.getMinHeight());
            newGamePanel.setPrefHeight(boardPanel.getPrefHeight());
            newGamePanel.setMaxHeight(boardPanel.getMaxHeight());

            // 右侧面板
            rightBox.getChildren().clear();
            rightBox.getChildren().addAll(infoPanel, listPanel, newGamePanel);
            HBox.setHgrow(infoPanel, Priority.ALWAYS);
            HBox.setHgrow(listPanel, Priority.ALWAYS);
            HBox.setHgrow(newGamePanel, Priority.ALWAYS);
            infoPanel.setMaxWidth(Double.MAX_VALUE);
            listPanel.setMaxWidth(Double.MAX_VALUE);
            newGamePanel.setMaxWidth(Double.MAX_VALUE);
            rightBox.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(rightBox, Priority.ALWAYS);
            infoPanel.setStyle("-fx-alignment: top-center; -fx-padding: 0 10 0 10;");
            newGamePanel.setStyle("-fx-alignment: top-center; -fx-padding: 0 10 0 10;");

            // 棋盘
            boardBox.getChildren().clear();
            boardBox.getChildren().add(boardPanel);
            VBox.setVgrow(boardPanel, javafx.scene.layout.Priority.ALWAYS);
            HBox.setHgrow(boardBox, Priority.NEVER);

            hbox.getChildren().clear();
            hbox.getChildren().addAll(boardBox, rightBox);

            // Settlement info in English
            if (output.getOutputType() == OutputType.GAME_OVER) {
                statusLabel.setText(buildGameOver(output));
            } else {
                statusLabel.setText("");
            }

            // 检查是否为QUIT，若是则关闭窗口并退出进程
            if (output.getOutputType() == OutputType.QUIT) {
                GameManager.getInstance().save(App.getFilePath());
                System.exit(0);
            }
        });
    }

    private static String buildGameOver(OutputInformation output) {
        StringBuilder gameOver = new StringBuilder();
        gameOver.append("Game Over!\n");
        if (output.getGameInfo() != null && output.getGameInfo().getGameMode() != null
                && output.getGameInfo().getGameMode().toString().equals("REVERSI")) {
            gameOver.append(String.format("Player[%s ●]: %d\n", output.getGameInfo().getPlayer1Name(),
                    output.getGameInfo().getBlack()));
            gameOver.append(String.format("Player[%s ○]: %d\n", output.getGameInfo().getPlayer2Name(),
                    output.getGameInfo().getWhite()));
        }
        if (output.getGameInfo() != null && output.getGameInfo().getWinner() != null) {
            switch (output.getGameInfo().getWinner()) {
                case BLACK ->
                    gameOver.append(String.format("Player[%s ●] wins!\n", output.getGameInfo().getPlayer1Name()));
                case WHITE ->
                    gameOver.append(String.format("Player[%s ○] wins!\n", output.getGameInfo().getPlayer2Name()));
                case TIE ->
                    gameOver.append("It's a tie!\n");
                case VOID -> {
                }
            }
        }
        return gameOver.toString();
    }

    @Override
    public void printError(GameException e, OutputInformation output) {
        print(output);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
