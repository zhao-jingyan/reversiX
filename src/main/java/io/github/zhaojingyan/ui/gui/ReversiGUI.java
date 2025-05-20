package io.github.zhaojingyan.ui.gui;

import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.enums.OutputType;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.input.InputInformationFactory;
import io.github.zhaojingyan.model.output.OutputInformation;
import io.github.zhaojingyan.model.service.GameManager;
import io.github.zhaojingyan.model.service.OutputBuilder;
import io.github.zhaojingyan.ui.gui.buttons.ButtonManager;
import io.github.zhaojingyan.ui.gui.panels.BoardPanel;
import io.github.zhaojingyan.ui.gui.panels.InfoPanel;
import io.github.zhaojingyan.ui.gui.panels.ListPanel;
import io.github.zhaojingyan.ui.gui.panels.NewGamePanel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReversiGUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        ButtonManager buttonManager = ButtonManager.getInstance();
        HBox hbox = new HBox(30);
        hbox.setStyle("-fx-padding: 30 40 30 40; -fx-alignment: top-left; -fx-fill-height: true;");

        // 测试BoardPanel，棋盘大小可变
        BoardPanel boardPanel = null;
        InfoPanel infoPanel = null;
        ListPanel listPanel = null;
        NewGamePanel newGamePanel = null;
        
        // 用标准流程构造一次输入输出，生成OutputInformation
        try {
            // 构造一个新游戏输入
            InputInformation inputInfo = InputInformationFactory.create(InputType.BOARDNUM, "3");
            inputInfo.preHandle();
            OutputType outputType = inputInfo.getOutputType();
            OutputInformation output = OutputBuilder.create( outputType,GameManager.getInstance());
            boardPanel = new BoardPanel(output.getGameInfo().getBoard().getSize(), buttonManager);
            boardPanel.render(output);
            infoPanel = new InfoPanel();
            infoPanel.render(output);
            listPanel = new ListPanel();
            listPanel.render(output);
            newGamePanel = new NewGamePanel();
            newGamePanel.render(output);


            infoPanel.setMinHeight(boardPanel.getMinHeight());
            infoPanel.setPrefHeight(boardPanel.getPrefHeight());
            infoPanel.setMaxHeight(boardPanel.getMaxHeight());
            listPanel.setPanelHeight(boardPanel.getMinHeight(), boardPanel.getPrefHeight(), boardPanel.getMaxHeight());
            newGamePanel.setMinHeight(boardPanel.getMinHeight());
            newGamePanel.setPrefHeight(boardPanel.getPrefHeight());
            newGamePanel.setMaxHeight(boardPanel.getMaxHeight());

        } catch (Exception e) {
            e.printStackTrace();
        }

        HBox rightBox = new HBox(20);
        rightBox.getChildren().clear();
        rightBox.getChildren().addAll(infoPanel, listPanel, newGamePanel);
        HBox.setHgrow(infoPanel, Priority.ALWAYS);
        HBox.setHgrow(listPanel, Priority.ALWAYS);
        HBox.setHgrow(newGamePanel, Priority.ALWAYS);
        infoPanel.setMaxWidth(Double.MAX_VALUE);
        listPanel.setMaxWidth(Double.MAX_VALUE);
        newGamePanel.setMaxWidth(Double.MAX_VALUE);
        // 让右侧三列紧凑排列，避免右侧留白
        rightBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(rightBox, Priority.ALWAYS);

        // 设置infoPanel和newGamePanel在各自空间内左右居中，上端对齐
        infoPanel.setStyle("-fx-alignment: top-center; -fx-padding: 0 10 0 10;");
        newGamePanel.setStyle("-fx-alignment: top-center; -fx-padding: 0 10 0 10;");

        // 让棋盘高度随窗口拉伸变化，保持正方形
        BoardPanel finalBoardPanel = boardPanel;
        VBox boardBox = new VBox();
        if (finalBoardPanel != null) {
            boardBox.getChildren().add(finalBoardPanel);
            VBox.setVgrow(finalBoardPanel, javafx.scene.layout.Priority.ALWAYS);
        }
        HBox.setHgrow(boardBox, Priority.NEVER);
        hbox.getChildren().clear();
        hbox.getChildren().addAll(boardBox, rightBox);
        Scene scene = new Scene(hbox, 1100, 500);


        primaryStage.setTitle("Reversi 棋盘测试");
        primaryStage.setScene(scene);
        primaryStage.show();
        buttonManager.refreshAllButtons();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
