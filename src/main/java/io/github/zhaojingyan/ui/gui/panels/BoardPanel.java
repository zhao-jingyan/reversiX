package io.github.zhaojingyan.ui.gui.panels;

import io.github.zhaojingyan.model.output.OutputInformation;
import io.github.zhaojingyan.ui.gui.buttons.CellButton;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardPanel extends GridPane {
    private final int boardSize;
    private static final int CELL_SIZE = 25;
    private final int cellSize;
    private final CellButton[][] cellButtons;

    public BoardPanel(int boardSize) {
        this.boardSize = boardSize;
        if (boardSize == 8) {
            this.cellSize = 45; // 8x8棋盘格子更大
        } else {
            this.cellSize = CELL_SIZE;
        }
        setHgap(0);
        setVgap(0);
        setStyle(""); // 无背景色
        cellButtons = new CellButton[boardSize][boardSize];
        int totalSize = (boardSize + 1) * cellSize;
        setMinSize(totalSize, totalSize);
        setPrefSize(totalSize, totalSize);
        setMaxSize(totalSize, totalSize);
        drawBoard();
    }

    public int getBoardSize() {
        return boardSize;
    }

    private void drawBoard() {
        this.getChildren().clear();
        this.getColumnConstraints().clear();
        this.getRowConstraints().clear();
        for (int i = 0; i <= boardSize; i++) {
            javafx.scene.layout.ColumnConstraints colConst = new javafx.scene.layout.ColumnConstraints(cellSize);
            colConst.setMinWidth(cellSize);
            colConst.setPrefWidth(cellSize);
            colConst.setMaxWidth(cellSize);
            colConst.setHgrow(javafx.scene.layout.Priority.NEVER);
            this.getColumnConstraints().add(colConst);
            javafx.scene.layout.RowConstraints rowConst = new javafx.scene.layout.RowConstraints(cellSize);
            rowConst.setMinHeight(cellSize);
            rowConst.setPrefHeight(cellSize);
            rowConst.setMaxHeight(cellSize);
            rowConst.setVgrow(javafx.scene.layout.Priority.NEVER);
            this.getRowConstraints().add(rowConst);
        }
        // 绘制左侧和上方坐标
        for (int i = 0; i < boardSize; i++) {
            Label colLabel = new Label(String.valueOf((char) ('A' + i)));
            colLabel.setStyle(
                    "-fx-font-weight: bold; -fx-text-fill: #222; -fx-alignment: center; -fx-font-size: 13px; -fx-font-family: 'monospaced', 'Consolas', 'Menlo', 'Courier', monospace;");
            colLabel.setMinSize(cellSize, cellSize);
            colLabel.setPrefSize(cellSize, cellSize);
            colLabel.setMaxSize(cellSize, cellSize);
            GridPane.setHalignment(colLabel, javafx.geometry.HPos.CENTER);
            GridPane.setValignment(colLabel, javafx.geometry.VPos.CENTER);
            this.add(colLabel, i + 1, 0);
            String hex = Integer.toHexString(i + 1).toUpperCase();
            Label rowLabel = new Label(hex);
            rowLabel.setStyle(
                    "-fx-font-weight: bold; -fx-text-fill: #222; -fx-alignment: center; -fx-font-size: 13px; -fx-font-family: 'monospaced', 'Consolas', 'Menlo', 'Courier', monospace;");
            rowLabel.setMinSize(cellSize, cellSize);
            rowLabel.setPrefSize(cellSize, cellSize);
            rowLabel.setMaxSize(cellSize, cellSize);
            GridPane.setHalignment(rowLabel, javafx.geometry.HPos.CENTER);
            GridPane.setValignment(rowLabel, javafx.geometry.VPos.CENTER);
            this.add(rowLabel, 0, i + 1);
        }
        Label corner = new Label("");
        corner.setMinSize(cellSize, cellSize);
        corner.setPrefSize(cellSize, cellSize);
        corner.setMaxSize(cellSize, cellSize);
        this.add(corner, 0, 0);
        // 绘制格子
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                StackPane cell = new StackPane();
                Rectangle rect = new Rectangle(cellSize, cellSize);
                rect.setFill(Color.web("#fff8dc"));
                rect.setStroke(Color.web("#000"));
                rect.setStrokeWidth(1);
                cell.getChildren().add(rect);
                CellButton btn = new CellButton(row, col);
                btn.setMinSize(cellSize, cellSize);
                btn.setMaxSize(cellSize, cellSize);
                btn.setPrefSize(cellSize, cellSize);
                cell.getChildren().add(btn);
                cellButtons[row][col] = btn;
                this.add(cell, col + 1, row + 1);
            }
        }
    }

    /**
     * 根据OutputInformation渲染棋盘上的棋子和标记
     */
    public void render(OutputInformation output) {
        io.github.zhaojingyan.model.entities.Piece[][] pieceBoard = output.getGameInfo().getBoard().getPieceBoard();
        var board = output.getGameInfo().getBoard();
        var gameMode = output.getGameInfo().getGameMode();
        for (io.github.zhaojingyan.model.entities.Piece[] rowArr : pieceBoard) {
            for (io.github.zhaojingyan.model.entities.Piece piece : rowArr) {
                int x = piece.getX(), y = piece.getY();
                CellButton btn = cellButtons[x][y];
                btn.setGraphic(null); // 清除旧图形
                btn.setStyle(
                        "-fx-background-color: transparent; -fx-background-radius: 0; -fx-border-radius: 0; -fx-padding: 0; -fx-background-insets: 0;");
                switch (piece.getStatus()) {
                    case BLACK -> {
                        javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle(cellSize * 0.35);
                        circle.setFill(javafx.scene.paint.Color.BLACK);
                        btn.setGraphic(circle);
                    }
                    case WHITE -> {
                        javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle(cellSize * 0.35);
                        circle.setFill(javafx.scene.paint.Color.WHITE);
                        circle.setStroke(javafx.scene.paint.Color.BLACK);
                        circle.setStrokeWidth(2);
                        btn.setGraphic(circle);
                    }
                    case OBSTACLE -> {
                        javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(cellSize * 0.7,
                                cellSize * 0.7);
                        rect.setFill(javafx.scene.paint.Color.GRAY);
                        btn.setGraphic(rect);
                    }
                    case BOMB -> {
                        javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(cellSize * 0.7,
                                cellSize * 0.7);
                        rect.setFill(javafx.scene.paint.Color.RED);
                        btn.setGraphic(rect);
                    }
                    case EMPTY -> {
                        boolean isValid = board.isValid(new int[] { piece.getX(), piece.getY() });
                        boolean isReversi = gameMode != null && gameMode.name().equalsIgnoreCase("REVERSI");
                        if (isValid && isReversi) {
                            javafx.scene.shape.Circle dot = new javafx.scene.shape.Circle(cellSize * 0.12);
                            dot.setFill(javafx.scene.paint.Color.BLACK);
                            btn.setGraphic(dot);
                        }
                    }
                }
            }
        }
    }
}
