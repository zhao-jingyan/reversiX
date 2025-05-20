package io.github.zhaojingyan;

/**
 * JavaFX应用程序主入口，启动黑白棋GUI界面
 */
public class MainFX {
    /**
     * 程序入口
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 启动GUI，确保start方法被调用
        javafx.application.Application.launch(io.github.zhaojingyan.ui.gui.GuiOutput.class, args);
    }
}
