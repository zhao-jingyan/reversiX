package io.github.zhaojingyan.ui.gui.panels;

import io.github.zhaojingyan.model.output.OutputInformation;
import io.github.zhaojingyan.ui.gui.buttons.ButtonManager;
import io.github.zhaojingyan.ui.gui.buttons.NewGameButton;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class NewGamePanel extends VBox implements GamePanel {
    public NewGamePanel() {
        setSpacing(10);
        setStyle("");
        Label title = new Label("New Game");
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #222; -fx-padding: 0 0 8 0; -fx-font-family: 'monospaced', 'Consolas', 'Menlo', 'Courier', monospace;");
        ButtonManager buttonManager = ButtonManager.getInstance();
        getChildren().addAll(title,
            createStyledButton("PEACE", buttonManager),
            createStyledButton("REVERSI", buttonManager),
            createStyledButton("GOMOKU", buttonManager)
        );
    }

    private NewGameButton createStyledButton(String mode, ButtonManager buttonManager) {
        String label = switch (mode) {
            case "PEACE" -> "Peace";
            case "REVERSI" -> "Reversi";
            case "GOMOKU" -> "Gomoku";
            default -> mode;
        };
        NewGameButton btn = NewGameButton.create(label, buttonManager);
        btn.getStyleClass().add("game-btn");
        btn.setStyle("-fx-font-size: 13px; -fx-font-family: 'monospaced', 'Consolas', 'Menlo', 'Courier', monospace;");
        return btn;
    }

    @Override
    public void render(OutputInformation output) {
        this.getChildren().clear();
        Label title = new Label("New Game");
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #222; -fx-padding: 0 0 8 0; -fx-font-family: 'monospaced', 'Consolas', 'Menlo', 'Courier', monospace;");
        ButtonManager buttonManager = ButtonManager.getInstance();
        getChildren().addAll(title,
            createStyledButton("PEACE", buttonManager),
            createStyledButton("REVERSI", buttonManager),
            createStyledButton("GOMOKU", buttonManager)
        );
    }
}
