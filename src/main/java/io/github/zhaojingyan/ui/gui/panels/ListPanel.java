package io.github.zhaojingyan.ui.gui.panels;

import io.github.zhaojingyan.model.output.OutputInformation;
import io.github.zhaojingyan.ui.gui.GuiInput;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ListPanel extends VBox implements GamePanel {
    private final ListView<String> gameListView;
    private final ObservableList<String> gameListData;
    // 用于防止死循环的当前游戏号缓存
    private int lastSelectedGame = -1;

    public ListPanel() {
        setSpacing(3);
        setStyle("");
        setAlignment(Pos.TOP_CENTER);
        setMinHeight(0);
        setPrefHeight(Region.USE_COMPUTED_SIZE);
        setMaxHeight(Region.USE_PREF_SIZE);
        Label title = new Label("Game List");
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #222; -fx-padding: 0 0 8 0; -fx-font-family: 'monospaced', 'Consolas', 'Menlo', 'Courier', monospace;");
        gameListData = FXCollections.observableArrayList();
        gameListView = new ListView<>(gameListData);
        gameListView.setStyle("-fx-font-size: 13px; -fx-font-family: 'monospaced', 'Consolas', 'Menlo', 'Courier', monospace;");
        gameListView.setPrefHeight(200);
        gameListView.setMinWidth(80); // 允许更小宽度，便于HBox动态分配
        gameListView.setMaxWidth(Double.MAX_VALUE); // 允许最大宽度自适应
        VBox.setVgrow(gameListView, javafx.scene.layout.Priority.ALWAYS);
        gameListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && newVal.intValue() >= 0) {
                int gameNum = newVal.intValue() + 1;
                if (lastSelectedGame != gameNum) {
                    GuiInput.handleButtonInput(String.valueOf(gameNum));
                }
            }
        });
        getChildren().addAll(title, gameListView);
    }

    public void setPanelHeight(double min, double pref, double max) {
        setMinHeight(min);
        setPrefHeight(pref);
        setMaxHeight(max);
        gameListView.setMinHeight(min);
        gameListView.setPrefHeight(pref);
        gameListView.setMaxHeight(max);
    }

    @Override
    public void render(OutputInformation output) {
        gameListData.clear();
        var gameList = output.getGlobalInfo().getGameList();
        int currentGame = output.getGlobalInfo().getCurrentGameNumber();
        for (int i = 0; i < gameList.length; i++) {
            int gameNum = i + 1;
            String gameName = gameList[i].toString();
            gameListData.add("Game " + gameNum + ": " + gameName);
        }
        // 自动选中当前游戏并高亮
        if (currentGame > 0 && currentGame <= gameList.length) {
            lastSelectedGame = currentGame;
            gameListView.getSelectionModel().select(currentGame - 1);
        }
        // 超过12个时出现滑块，否则自适应高度
        int visibleCount = gameListData.size();
        if (visibleCount > 12) {
            gameListView.setFixedCellSize(32);
            int showCount = 12;
            gameListView.setPrefHeight(showCount * 32 + 2);
            gameListView.setMinHeight(showCount * 32 + 2);
            gameListView.setMaxHeight(showCount * 32 + 2);
        } else {
            gameListView.setFixedCellSize(32);
            gameListView.setPrefHeight(visibleCount > 0 ? visibleCount * 32 + 2 : 0);
            gameListView.setMinHeight(visibleCount > 0 ? visibleCount * 32 + 2 : 0);
            gameListView.setMaxHeight(visibleCount > 0 ? visibleCount * 32 + 2 : 0);
        }
    }
}
