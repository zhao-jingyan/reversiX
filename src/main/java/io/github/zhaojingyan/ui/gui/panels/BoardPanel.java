package io.github.zhaojingyan.ui.gui.panels;

import io.github.zhaojingyan.ui.gui.buttons.ButtonManager;
import io.github.zhaojingyan.ui.gui.buttons.CellButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardPanel extends GridPane {
    private int boardSize;
    private static final int CELL_SIZE = 40;
    private CellButton[][] cellButtons;

    public BoardPanel(int boardSize, ButtonManager buttonManager) {
        this.boardSize = boardSize;
        setHgap(0);
        setVgap(0);
        setStyle("-fx-background-color: #f8f8f8;");
        cellButtons = new CellButton[boardSize][boardSize];
        drawBoard(buttonManager);
    }

    private void drawBoard(ButtonManager buttonManager) {
        this.getChildren().clear();
        StackPane outerFrame = new StackPane();
        GridPane innerGrid = new GridPane();
        innerGrid.setHgap(2);
        innerGrid.setVgap(2);
        innerGrid.setStyle("-fx-background-color: #000; -fx-padding: 2;");
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                StackPane cell = new StackPane();
                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
                rect.setFill(Color.web("#fff8dc"));
                rect.setStroke(null);
                cell.getChildren().add(rect);
                CellButton btn = CellButton.create(row, col, buttonManager);
                cell.getChildren().add(btn);
                cellButtons[row][col] = btn;
                innerGrid.add(cell, col, row);
            }
        }
        Rectangle borderRect = new Rectangle(CELL_SIZE * boardSize + (boardSize - 1), CELL_SIZE * boardSize + (boardSize - 1));
        borderRect.setFill(Color.TRANSPARENT);
        borderRect.setStroke(Color.web("#000"));
        borderRect.setStrokeWidth(2);
        outerFrame.getChildren().addAll(borderRect, innerGrid);
        this.add(outerFrame, 0, 0);
    }

    /**
     * 根据OutputInformation渲染棋盘上的棋子和标记
     */
    public void renderBoard(io.github.zhaojingyan.model.output.OutputInformation output) {
        io.github.zhaojingyan.model.entities.Piece[][] pieceBoard = output.getGameInfo().getBoard().getPieceBoard();
        var board = output.getGameInfo().getBoard();
        var gameMode = output.getGameInfo().getGameMode();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                CellButton btn = cellButtons[row][col];
                btn.setGraphic(null); // 清除旧图形
                btn.setStyle("-fx-background-color: transparent; -fx-background-radius: 0; -fx-border-radius: 0; -fx-padding: 0; -fx-background-insets: 0;");
                io.github.zhaojingyan.model.entities.Piece piece = pieceBoard[row][col];
                switch (piece.getStatus()) {
                    case BLACK -> {
                        javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle(CELL_SIZE * 0.35);
                        circle.setFill(Color.BLACK);
                        btn.setGraphic(circle);
                    }
                    case WHITE -> {
                        javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle(CELL_SIZE * 0.35);
                        circle.setFill(Color.WHITE);
                        circle.setStroke(Color.BLACK);
                        circle.setStrokeWidth(2);
                        btn.setGraphic(circle);
                    }
                    case OBSTACLE -> {
                        javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(CELL_SIZE * 0.7, CELL_SIZE * 0.7);
                        rect.setFill(Color.GRAY);
                        btn.setGraphic(rect);
                    }
                    case BOMB -> {
                        javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(CELL_SIZE * 0.7, CELL_SIZE * 0.7);
                        rect.setFill(Color.RED);
                        btn.setGraphic(rect);
                    }
                    case EMPTY -> {
                        boolean isValid = board.isValid(new int[]{piece.getX(), piece.getY()});
                        boolean isReversi = gameMode != null && gameMode.name().equalsIgnoreCase("REVERSI");
                        if (isValid && isReversi) {
                            javafx.scene.shape.Circle dot = new javafx.scene.shape.Circle(CELL_SIZE * 0.12);
                            dot.setFill(Color.BLACK);
                            btn.setGraphic(dot);
                        }
                    }
                }
            }
        }
    }
}
