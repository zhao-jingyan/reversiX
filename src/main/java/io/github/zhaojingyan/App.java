package io.github.zhaojingyan;

import io.github.zhaojingyan.model.service.GameManager;
import io.github.zhaojingyan.model.service.MainController;
import io.github.zhaojingyan.ui.console.ConInput;
import io.github.zhaojingyan.ui.console.ConOutput;

public class App {
    public static void main( String[] args ){
        String savePath = System.getProperty("user.dir") + java.io.File.separator + "pj.game";
        GameManager.getInstance().load(savePath);
        MainController.setInputOutput(new ConInput(), new ConOutput());
        MainController.gameLoop();
        GameManager.getInstance().save(savePath);
    }
}
