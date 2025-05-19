package io.github.zhaojingyan.ui.gui;

import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.output.GameInfo;
import io.github.zhaojingyan.ui.gui.buttons.BombButton;
import io.github.zhaojingyan.ui.gui.buttons.CustomButton;
import io.github.zhaojingyan.ui.gui.buttons.GameSwitchButton;
import io.github.zhaojingyan.ui.gui.buttons.PassButton;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 游戏控制面板，包含游戏操作按钮
 */
public class GameControlPanel extends VBox {
    private final GameSwitchButton[] gameSwitchButtons;
    private final PassButton passButton;
    private final BombButton bombButton;
    private final GuiInput guiInput;
    private final Label statusLabel;
    private boolean bombMode = false;
    
    /**
     * 创建游戏控制面板
     * @param guiInput GUI输入处理器
     */
    public GameControlPanel(GuiInput guiInput) {
        this.guiInput = guiInput;
        setSpacing(10);
        
        // 创建状态标签
        statusLabel = new Label("准备就绪");
        statusLabel.setStyle("-fx-padding: 5px; -fx-background-color: #f0f0f0; -fx-background-radius: 3px;");
        
        // 游戏切换按钮
        HBox gameSwitchBox = new HBox(10);
        gameSwitchButtons = new GameSwitchButton[3];
        for (int i = 0; i < 3; i++) {
            final int gameNum = i + 1;
            GameSwitchButton btn = new GameSwitchButton(gameNum);
            btn.setOnAction(e -> {
                // 先处理按钮点击事件，发送切换游戏命令
                handleButtonClick(btn);
                // 不在这里处理高亮，而是让updateForGame方法根据当前游戏状态更新按钮样式
            });
            gameSwitchButtons[i] = btn;
            gameSwitchBox.getChildren().add(btn);
        }
        
        // Pass按钮
        passButton = new PassButton();
        passButton.setOnAction(e -> handleButtonClick(passButton));
        
        // 炸弹按钮
        bombButton = new BombButton();
        bombButton.setOnAction(e -> {
            // 切换炸弹模式
            bombMode = bombButton.toggleBombMode();
            // 当进入炸弹模式时，提示用户
            if (bombMode) {
                statusLabel.setText("已激活炸弹模式 - 请点击对方棋子进行炸弹攻击");
            }
        });
        
        // 添加所有控件
        getChildren().addAll(gameSwitchBox, passButton, bombButton, statusLabel);
        
        // 初始状态
        passButton.setVisible(false);
        bombButton.setVisible(false);
    }
    
    /**
     * 处理按钮点击
     * @param button 自定义按钮
     */
    private void handleButtonClick(CustomButton button) {
        guiInput.handleButtonClick(button);
    }
    
    /**
     * 根据游戏信息更新控制面板
     * @param gameInfo 游戏信息
     */
    public void updateForGame(GameInfo gameInfo) {
        // 更新棋盘选择按钮
        int currentGameNum = gameInfo.getCurrentGameNumber();
        for (int i = 0; i < gameSwitchButtons.length; i++) {
            gameSwitchButtons[i].setSelected(i == currentGameNum - 1);
        }
        
        // 根据游戏模式显示/隐藏相关按钮
        GameMode mode = gameInfo.getGameMode();
        passButton.setVisible(mode == GameMode.REVERSI);
        bombButton.setVisible(mode == GameMode.GOMOKU);
        
        // 在Reversi模式下，根据是否需要跳过启用/禁用Pass按钮
        if (mode == GameMode.REVERSI) {
            passButton.setDisable(!gameInfo.isWaitingForPass());
        }
        
        // 炸弹按钮状态更新
        if (mode == GameMode.GOMOKU) {
            boolean hasBombs = gameInfo.getChargeSymbol().toString().equals("BLACK") ? 
                gameInfo.getBlackBomb() > 0 : gameInfo.getWhiteBomb() > 0;
            bombButton.setDisable(!hasBombs);
        }
    }
    
    /**
     * 判断是否处于炸弹模式
     * @return 是否炸弹模式
     */
    public boolean isBombMode() {
        return bombMode;
    }
    
    /**
     * 退出炸弹模式
     */
    public void exitBombMode() {
        bombMode = false;
        bombButton.toggleBombMode();
    }
}
