package io.github.zhaojingyan;

import io.github.zhaojingyan.controller.logic.MainController;
import io.github.zhaojingyan.ui.console.ConInput;
import io.github.zhaojingyan.ui.console.ConOutput;
import io.github.zhaojingyan.ui.interfaces.InputInterface;
import io.github.zhaojingyan.ui.interfaces.OutputInterface;

public class App {
    public static void main( String[] args ){
        InputInterface input = new ConInput();
        OutputInterface output = new ConOutput();
        MainController.setInputOutput(input, output);
        MainController.gameLoop();
    }
}  
