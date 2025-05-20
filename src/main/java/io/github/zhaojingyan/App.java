package io.github.zhaojingyan;

import io.github.zhaojingyan.model.service.GameManager;
import io.github.zhaojingyan.model.service.MainController;
import io.github.zhaojingyan.ui.console.ConInput;
import io.github.zhaojingyan.ui.console.ConOutput;

public class App {
    private static final String SAVEPATH = System.getProperty("user.dir") + java.io.File.separator + "pj.game";
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("gui")) {
            // GUI模式下也先尝试加载进度
            GameManager.getInstance().load(SAVEPATH);
            javafx.application.Application.launch(io.github.zhaojingyan.ui.gui.GuiOutput.class, args);

        } else {
            // 启动终端版本
            GameManager.getInstance().load(SAVEPATH);
            MainController.setInputOutput(new ConInput(), new ConOutput());
            MainController.gameLoop();
            GameManager.getInstance().save(SAVEPATH);
            // 若用过 JavaFX，确保彻底退出
            try {
                javafx.application.Platform.exit();
            } catch (Exception ignore) {}
        }
    }

    public static String getFilePath(){
        return SAVEPATH;
    }
}
