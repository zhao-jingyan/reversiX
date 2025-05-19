package io.github.zhaojingyan.ui.gui;

import io.github.zhaojingyan.model.output.GameInfo;
import io.github.zhaojingyan.model.service.Game;
import io.github.zhaojingyan.model.service.GameManager;
import io.github.zhaojingyan.model.service.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * 新版GUI主程序，使用自定义按钮和主循环
 */
public class ReversiGUI extends Application {
    private GuiInput guiInput;
    private GuiOutput guiOutput;
    private GameControlPanel controlPanel;
    private Label statusLabel;
    private BoardView boardView;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("黑白棋 GUI");
        
        // 状态标签
        statusLabel = new Label("正在初始化...");
        statusLabel.setPadding(new Insets(5, 10, 5, 10));
        
        // 初始化游戏管理器
        GameManager gameManager = GameManager.getInstance();
        Game currentGame = gameManager.getCurrentGame();
        GameInfo gameInfo = new GameInfo(currentGame);
        
        // 获取当前游戏棋盘大小
        int boardSize = gameInfo.getGameMode().getSize();
        
        // 创建棋盘视图
        boardView = new BoardView(boardSize);
        
        // 创建输入输出处理
        guiInput = new GuiInput();
        
        // 创建控制面板
        controlPanel = new GameControlPanel(guiInput);
        controlPanel.setPadding(new Insets(10));
        
        // 初始化GUI输出
        guiOutput = new GuiOutput(boardView, statusLabel, controlPanel);
        
        // 创建主布局
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(boardView);
        mainPane.setRight(controlPanel);
        mainPane.setBottom(statusLabel);
        
        // 创建场景
        Scene scene = new Scene(mainPane, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // 启动游戏主循环
        Thread gameThread = new Thread(this::gameLoop);
        gameThread.setDaemon(true);
        gameThread.start();
        
        // 初始刷新
        GameInfo initialInfo = new GameInfo(gameManager.getCurrentGame());
        boardView.refresh(initialInfo);
        controlPanel.updateForGame(initialInfo);
    }
    
    /**
     * 游戏主循环，与控制台游戏循环保持一致
     */
    private void gameLoop() {
        try {
            // 设置输入输出
            MainController.setInputOutput(guiInput, guiOutput);
            
            // 运行游戏循环
            MainController.gameLoop();
        } catch (Exception e) {
            Platform.runLater(() -> {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                    javafx.scene.control.Alert.AlertType.ERROR);
                alert.setTitle("错误");
                alert.setHeaderText("游戏运行错误");
                alert.setContentText("发生错误: " + e.getMessage());
                alert.showAndWait();
            });
        }
    }
    
    /**
     * 程序入口
     */
    public static void main(String[] args) {
        // 临时移除加载存档功能以便测试
        // String savePath = System.getProperty("user.dir") + java.io.File.separator + "pj.game";
        // GameManager.getInstance().load(savePath);
        
        // 启动JavaFX应用
        launch(args);
        
        // 临时移除保存存档功能以便测试
        // GameManager.getInstance().save(savePath);
    }
}
