package io.github.zhaojingyan.ui.gui.panels;

import io.github.zhaojingyan.model.output.OutputInformation;
import io.github.zhaojingyan.ui.gui.buttons.BombButton;
import io.github.zhaojingyan.ui.gui.buttons.PassButton;
import io.github.zhaojingyan.ui.gui.buttons.PlayBackButton;
import io.github.zhaojingyan.ui.gui.buttons.QuitButton;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class InfoPanel extends VBox {
    private final Label gameIdLabel;
    private final Label blackInfoLabel;
    private final Label whiteInfoLabel;
    private final Label roundLabel;
    private final BombButton bombButton;
    private final PassButton passButton;
    private final QuitButton quitButton;
    private final PlayBackButton playBackButton;

    public InfoPanel() {
        setSpacing(8);
        setStyle("");
        setMinWidth(150); // 允许更小宽度，便于HBox动态分配
        setPrefWidth(170);
        setMaxWidth(Double.MAX_VALUE); // 允许最大宽度自适应
        gameIdLabel = new Label();
        gameIdLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #333; -fx-font-family: 'monospaced', 'Consolas', 'Menlo', 'Courier', monospace;");
        blackInfoLabel = new Label();
        blackInfoLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #111; -fx-font-family: 'monospaced', 'Consolas', 'Menlo', 'Courier', monospace;");
        whiteInfoLabel = new Label();
        whiteInfoLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #111; -fx-font-family: 'monospaced', 'Consolas', 'Menlo', 'Courier', monospace;");
        roundLabel = new Label();
        roundLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #444; -fx-font-family: 'monospaced', 'Consolas', 'Menlo', 'Courier', monospace;");
        playBackButton = new PlayBackButton();
        bombButton = new BombButton();
        passButton = new PassButton();
        quitButton = new QuitButton();
        getChildren().addAll(gameIdLabel, blackInfoLabel, whiteInfoLabel, roundLabel, playBackButton, bombButton, passButton, quitButton);
    }

    public void init() {
        setVgrow(this, Priority.ALWAYS); // 构造完成后再调用
    }

    public void setGameId(String gameId) {
        gameIdLabel.setText("Game: " + gameId);
    }

    /**
     * 设置黑白棋落子信息和棋子/炸弹数
     * @param blackInfo 例如 "黑棋: 上一步落子 E3, 棋子数: 12"
     * @param whiteInfo 例如 "白棋: 上一步落子 F4, 棋子数: 10"
     */
    public void setPlayerInfo(String blackInfo, String whiteInfo) {
        blackInfoLabel.setText(blackInfo);
        whiteInfoLabel.setText(whiteInfo);
    }

    /**
     * 设置当前轮数，仅GOMOKU模式下显示
     */
    public void setRoundInfo(Integer round, boolean isGomoku) {
        if (isGomoku && round != null) {
            if (getChildren().indexOf(roundLabel) != 3) {
                getChildren().remove(roundLabel);
                getChildren().add(3, roundLabel);
            }
            roundLabel.setText("  Round: " + round);
            roundLabel.setVisible(true);
        } else {
            getChildren().remove(roundLabel);
        }
    }

    /**
     * 设置炸弹按钮在gomoku模式下显示
     */
    public void setBombButtonVisible(boolean visible) {
        bombButton.setVisible(visible);
        if (visible && !getChildren().contains(bombButton)) {
            getChildren().add(getChildren().size() - 1, bombButton); // quitButton始终在最后
        } else if (!visible) {
            getChildren().remove(bombButton);
        }
    }

    public Button getBombButton() {
        return bombButton;
    }

    /**
     * 设置PASS按钮在reversi模式下显示
     */
    public void setPassButtonVisible(boolean visible) {
        passButton.setVisible(visible);
        if (visible && !getChildren().contains(passButton)) {
            getChildren().add(getChildren().size() - 1, passButton);
        } else if (!visible) {
            getChildren().remove(passButton);
        }
    }

    public Button getPassButton() {
        return passButton;
    }

    public QuitButton getQuitButton() {
        return quitButton;
    }

    public void render(OutputInformation output) {
        var gameInfo = output.getGameInfo();
        // 游戏编号
        setGameId(String.valueOf(gameInfo.getCurrentGameNumber()));
        // 玩家名与棋子/炸弹数
        String blackInfo, whiteInfo;
        String chargeName = gameInfo.getChargePlayerName();
        // 棋子/炸弹数
        if (gameInfo.getGameMode().name().equalsIgnoreCase("REVERSI")) {
            blackInfo = String.format("%s ● %s ● %d",
                chargeName.equals(gameInfo.getPlayer1Name()) ? "> " : "  ",
                gameInfo.getPlayer1Name(),
                gameInfo.getBlack());
            whiteInfo = String.format("%s ○ %s ○ %d",
                chargeName.equals(gameInfo.getPlayer2Name()) ? "> " : "  ",
                gameInfo.getPlayer2Name(),
                gameInfo.getWhite());
            setPassButtonVisible(true);
            setBombButtonVisible(false);
            setRoundInfo(null, false);
        } else if (gameInfo.getGameMode().name().equalsIgnoreCase("GOMOKU")) {
            blackInfo = String.format("%s ● %s 💣 %d",
                chargeName.equals(gameInfo.getPlayer1Name()) ? "> " : "  ",
                gameInfo.getPlayer1Name(),
                gameInfo.getBlackBomb());
            whiteInfo = String.format("%s ○ %s 💣 %d",
                chargeName.equals(gameInfo.getPlayer2Name()) ? "> " : "  ",
                gameInfo.getPlayer2Name(),
                gameInfo.getWhiteBomb());
            setPassButtonVisible(false);
            setBombButtonVisible(true);
            setRoundInfo(gameInfo.getCurrentRound(), true);
        } else {
            blackInfo = String.format("%s ● %s",
                chargeName.equals(gameInfo.getPlayer1Name()) ? "> " : "  ",
                gameInfo.getPlayer1Name());
            whiteInfo = String.format("%s ○ %s",
                chargeName.equals(gameInfo.getPlayer2Name()) ? "> " : "  ",
                gameInfo.getPlayer2Name());
            setPassButtonVisible(false);
            setBombButtonVisible(false);
            setRoundInfo(null, false);
        }
        setPlayerInfo(blackInfo, whiteInfo);
    }
}
