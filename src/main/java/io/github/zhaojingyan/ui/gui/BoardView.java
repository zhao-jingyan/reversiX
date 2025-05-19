package io.github.zhaojingyan.ui.gui;

import io.github.zhaojingyan.model.entities.Board;
import io.github.zhaojingyan.model.entities.Piece;
import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.PieceStatus;
import io.github.zhaojingyan.model.output.GameInfo;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * 只负责棋盘的显示和刷新，不处理输入逻辑
 */
public class BoardView extends GridPane {
    private final int size;
    private final Button[][] buttons;

    public BoardView(int size) {
        this.size = size;
        this.buttons = new Button[size][size];
        setHgap(0);
        setVgap(0);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Button btn = new Button();
                btn.setMinSize(40, 40);
                btn.setMaxSize(40, 40);
                btn.setPrefSize(40, 40);
                btn.setFocusTraversable(false);
                buttons[x][y] = btn;
                add(btn, x, y); // x为列，y为行
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Button getButton(int x, int y) {
        // x为列，y为行，顺序与所有输入/渲染/事件一致
        return buttons[x][y];
    }

    /**
     * 新版刷新：只依赖 GameInfo，所有显示信息从 GameInfo 获取
     * 坐标顺序与 console/ScreenBuilder 保持一致：x为列，y为行
     */
    public void refresh(GameInfo info) {
        Board board = info.getBoard();
        GameMode mode = info.getGameMode();
        boolean showValid = mode == GameMode.REVERSI;
        for (int x = 0; x < size; x++) { // x为列
            for (int y = 0; y < size; y++) { // y为行
                CellInfo cell = buildCellInfo(board, x, y, showValid);
                renderCell(buttons[x][y], cell);
            }
        }
    }

    /**
     * 构造单格显示信息，x为列，y为行
     */
    private CellInfo buildCellInfo(Board board, int x, int y, boolean showValid) {
        Piece piece = board.getPieceAt(x, y); // x为列，y为行
        PieceStatus status = piece.getStatus();
        boolean isValid = false;
        try {
            isValid = board.isValid(new int[]{x, y});
        } catch (Exception ignored) {}
        boolean showValidDot = isValid && showValid && status == PieceStatus.EMPTY;
        return new CellInfo(status, showValidDot);
    }

    /**
     * 渲染单格
     */
    private void renderCell(Button btn, CellInfo cell) {
        btn.setText("");
        btn.setGraphic(null);
        String baseStyle = "-fx-background-color: #f5deb3; -fx-border-color: black; -fx-border-width: 1px;";
        btn.setStyle(baseStyle);
        Canvas canvas = new Canvas(36, 36);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if (cell.status == PieceStatus.BLACK) {
            gc.setFill(Color.BLACK);
            gc.fillOval(5.5, 5.5, 25, 25);
        } else if (cell.status == PieceStatus.WHITE) {
            gc.setFill(Color.WHITE);
            gc.fillOval(5.5, 5.5, 25, 25);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeOval(5.5, 5.5, 25, 25);
        } else if (cell.status == PieceStatus.OBSTACLE) {
            gc.setFill(Color.GRAY);
            gc.fillRect(10, 10, 16, 16);
        } else if (cell.status == PieceStatus.BOMB) {
            gc.setFill(Color.DARKRED);
            gc.fillOval(10, 10, 16, 16);
        } else if (cell.showValidDot) {
            gc.setFill(Color.BLACK);
            gc.fillOval(15, 15, 7, 7);
        }
        if (cell.status == PieceStatus.BLACK || cell.status == PieceStatus.WHITE || cell.status == PieceStatus.OBSTACLE || cell.status == PieceStatus.BOMB || cell.showValidDot) {
            btn.setGraphic(canvas);
        }
    }

    /**
     * 单格显示信息
     */
    private static class CellInfo {
        final PieceStatus status;
        final boolean showValidDot;
        CellInfo(PieceStatus status, boolean showValidDot) {
            this.status = status;
            this.showValidDot = showValidDot;
        }
    }
}
