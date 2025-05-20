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
    private ObservableList<String> gameListData;

    public ListPanel() {
        setSpacing(8);
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
                // 模拟GameSwitchButton点击逻辑
                GuiInput.handleButtonInput(String.valueOf(gameNum));
                io.github.zhaojingyan.ui.gui.buttons.ButtonManager.getInstance().refreshAllButtons();
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
        for (int i = 0; i < gameList.length; i++) {
            int gameNum = i + 1;
            String gameName = gameList[i].toString();
            gameListData.add("Game " + gameNum + ": " + gameName);
        }
        // 可选：自动选中当前游戏
        // gameListView.getSelectionModel().select(...);
    }
}
