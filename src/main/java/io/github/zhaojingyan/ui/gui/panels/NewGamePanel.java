package io.github.zhaojingyan.ui.gui.panels;

import io.github.zhaojingyan.model.output.OutputInformation;
import io.github.zhaojingyan.ui.gui.buttons.NewGameButton;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class NewGamePanel extends VBox implements GamePanel {
    public NewGamePanel() {
        setSpacing(10);
        setStyle("");
        Label title = new Label("New Game");
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #222; -fx-padding: 0 0 8 0; -fx-font-family: 'monospaced', 'Consolas', 'Menlo', 'Courier', monospace;");
        getChildren().addAll(title,
            new NewGameButton("Peace"),
            new NewGameButton("Reversi"),
            new NewGameButton("Gomoku")
        );
    }

    @Override
    public void render(OutputInformation output) {
        this.getChildren().clear();
        Label title = new Label("New Game");
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #222; -fx-padding: 0 0 8 0; -fx-font-family: 'monospaced', 'Consolas', 'Menlo', 'Courier', monospace;");
        getChildren().addAll(title,
            new NewGameButton("Peace"),
            new NewGameButton("Reversi"),
            new NewGameButton("Gomoku")
        );
    }
}
