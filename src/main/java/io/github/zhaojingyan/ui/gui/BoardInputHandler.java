package io.github.zhaojingyan.ui.gui;

import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.input.InputInformationFactory;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * 棋盘输入处理器，处理棋盘上的按钮点击事件
 */
public final class BoardInputHandler {
    private final BoardView boardView;
    private GuiInput guiInput;
    private GameControlPanel controlPanel;
    private OnCellClickListener listener; // 保留原来的监听器接口以兼容旧代码
    
    /**
     * 创建棋盘输入处理器（原始构造函数，兼容旧代码）
     */
    public BoardInputHandler(BoardView boardView) {
        this.boardView = boardView;
        bindEvents();
    }
    
    /**
     * 创建棋盘输入处理器（新构造函数，包含完整功能）
     */
    public BoardInputHandler(BoardView boardView, GuiInput guiInput, GameControlPanel controlPanel) {
        this.boardView = boardView;
        this.guiInput = guiInput;
        this.controlPanel = controlPanel;
        bindEvents();
    }
    
    /**
     * 绑定棋盘按钮事件
     */
    public void bindEvents() {
        int size = boardView.getSize();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Button btn = boardView.getButton(x, y);
                final int col = x, row = y;
                btn.setOnMouseClicked((MouseEvent event) -> {
                    // 如果新逻辑可用，使用新逻辑
                    if (guiInput != null && controlPanel != null) {
                        handleCellClick(col, row);
                    } 
                    // 否则使用传统的回调方式
                    else if (listener != null) {
                        listener.onCellClick(row, col);
                    }
                });
            }
        }
    }
    
    /**
     * 处理格子点击的新方法
     */
    private void handleCellClick(int x, int y) {
        // 构造坐标字符串，按照Console中的逻辑转换坐标
        // Console中：coordinates[1] = x (列), coordinates[0] = y (行)
        // 在MoveInformation中: (x + 1)为十六进制, y 是字母
        String coord = String.format("%X%c", y + 1, (char)('A' + x));
        
        // 判断是否为炸弹模式
        InputInformation info;
        if (controlPanel.isBombMode()) {
            // 炸弹模式，构造炸弹信息
            info = InputInformationFactory.create(InputType.BOMB, "@" + coord);
            // 点击后退出炸弹模式
            controlPanel.exitBombMode();
        } else {
            // 普通模式，构造移动信息
            info = InputInformationFactory.create(InputType.COORDINATES, coord);
        }
        
        // 发送输入信息
        guiInput.addInputInfo(info);
    }

    /**
     * 设置单元格点击监听器（兼容旧代码）
     */
    public void setOnCellClickListener(OnCellClickListener listener) {
        this.listener = listener;
    }

    /**
     * 单元格点击监听器接口（兼容旧代码）
     */
    public interface OnCellClickListener {
        void onCellClick(int row, int col);
    }
}
