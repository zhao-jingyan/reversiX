package io.github.zhaojingyan.ui.gui;

import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.output.GameInfo;
import io.github.zhaojingyan.model.output.OutputInformation;
import io.github.zhaojingyan.model.service.GameException;
import io.github.zhaojingyan.ui.interfaces.OutputInterface;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

/**
 * GUI输出处理类，负责将OutputInformation渲染到GUI界面
 */
public class GuiOutput implements OutputInterface {
    private BoardView boardView;
    private Label statusLabel;
    private GameControlPanel controlPanel;

    public GuiOutput(BoardView boardView, Label statusLabel, GameControlPanel controlPanel) {
        this.boardView = boardView;
        this.statusLabel = statusLabel;
        this.controlPanel = controlPanel;
    }

    public void refresh(BoardView boardView, Label statusLabel, GameControlPanel controlPanel){
        this.boardView = boardView;
        this.statusLabel = statusLabel;
        this.controlPanel = controlPanel;
    }

    @Override
    public void print(OutputInformation output) {

        Platform.runLater(() -> {
            GameInfo gameInfo = output.getGameInfo();
            
            // 刷新棋盘
            boardView.refresh(gameInfo);
            
            // 更新状态信息
            updateStatusLabel(gameInfo);
            
            // 更新控制面板
            controlPanel.updateForGame(gameInfo);

            refresh(boardView, statusLabel, controlPanel);
        });
    }

    @Override
    public void printError(GameException e, OutputInformation output) {
        Platform.runLater(() -> {
            // 显示错误对话框
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            
            // 刷新界面
            print(output);
        });
    }
    
    private void updateStatusLabel(GameInfo gameInfo) {
        StringBuilder status = new StringBuilder();
        status.append("游戏模式: ").append(gameInfo.getGameMode());
        status.append(" | 当前玩家: ").append(gameInfo.getChargePlayerName());
        status.append(" (").append(gameInfo.getChargeSymbol()).append(")");
        
        if (gameInfo.getGameMode() == GameMode.REVERSI) {
            status.append(" | 黑: ").append(gameInfo.getBlack());
            status.append(", 白: ").append(gameInfo.getWhite());
        } else if (gameInfo.getGameMode() == GameMode.GOMOKU) {
            status.append(" | 炸弹: 黑=").append(gameInfo.getBlackBomb());
            status.append(", 白=").append(gameInfo.getWhiteBomb());
        }
        
        if (gameInfo.isWaitingForPass()) {
            status.append(" | 需要跳过回合!");
        }
        
        if (gameInfo.getWinner() != null) {
            status.append(" | 游戏结束! 获胜者: ").append(gameInfo.getWinner());
        }
        
        statusLabel.setText(status.toString());
    }
}
