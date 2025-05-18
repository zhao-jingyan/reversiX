package io.github.zhaojingyan;

import io.github.zhaojingyan.model.service.GameManager;
import io.github.zhaojingyan.model.service.MainController;
import io.github.zhaojingyan.ui.console.ConInput;
import io.github.zhaojingyan.ui.console.ConOutput;

public class App {
    public static void main( String[] args ){
        GameManager.getInstance().load();
        MainController.setInputOutput(new ConInput(), new ConOutput());
        MainController.gameLoop();
    }
}  
