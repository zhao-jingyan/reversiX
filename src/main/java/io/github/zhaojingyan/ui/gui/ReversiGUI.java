package io.github.zhaojingyan.ui.gui;

import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.enums.OutputType;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.input.InputInformationFactory;
import io.github.zhaojingyan.model.output.OutputInformation;
import io.github.zhaojingyan.model.service.GameManager;
import io.github.zhaojingyan.model.service.OutputBuilder;
import io.github.zhaojingyan.ui.gui.buttons.BombButton;
import io.github.zhaojingyan.ui.gui.buttons.ButtonManager;
import io.github.zhaojingyan.ui.gui.buttons.GameSwitchButton;
import io.github.zhaojingyan.ui.gui.buttons.NewGameButton;
import io.github.zhaojingyan.ui.gui.buttons.PassButton;
import io.github.zhaojingyan.ui.gui.panels.BoardPanel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
    public class ReversiGUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        ButtonManager buttonManager = ButtonManager.getInstance();
        VBox vbox = new VBox(20);
        vbox.setStyle("-fx-padding: 30; -fx-alignment: center;");

        // 测试NewGameButton
        NewGameButton newGameBtn = NewGameButton.create("peace", buttonManager);
        // 测试PassButton
        PassButton passBtn = PassButton.create(buttonManager);
        // 测试BombButton
        BombButton bombBtn = BombButton.create(buttonManager);
        // 测试GameSwitchButton
        GameSwitchButton switchBtn1 = GameSwitchButton.create(1, "reversi",buttonManager);
        GameSwitchButton switchBtn2 = GameSwitchButton.create(2, "peace",buttonManager);

        // 测试BoardPanel，棋盘大小可变
        int boardSize; // 你可以改为任意大小
        BoardPanel boardPanel = null;
        
        // 用标准流程构造一次输入输出，生成OutputInformation
        try {
            // 构造一个新游戏输入
            InputInformation inputInfo = InputInformationFactory.create(InputType.BOARDNUM, "3");
            inputInfo.preHandle();
            OutputType outputType = inputInfo.getOutputType();
            OutputInformation output = OutputBuilder.create( outputType,GameManager.getInstance());
            boardSize = output.getGameInfo().getBoard().getSize();
            boardPanel = new BoardPanel(boardSize, buttonManager);
            boardPanel.renderBoard(output);
        } catch (Exception e) {
            e.printStackTrace();
        }

        vbox.getChildren().addAll(newGameBtn, passBtn, bombBtn, switchBtn1, switchBtn2, boardPanel);

        Scene scene = new Scene(vbox, 500, 500);
        primaryStage.setTitle("Reversi 棋盘测试");
        primaryStage.setScene(scene);
        primaryStage.show();
        buttonManager.refreshAllButtons();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
