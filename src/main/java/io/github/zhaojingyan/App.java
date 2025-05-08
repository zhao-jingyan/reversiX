package io.github.zhaojingyan;

import io.github.zhaojingyan.controller.logic.MainController;
import io.github.zhaojingyan.ui.console.ConInput;
import io.github.zhaojingyan.ui.console.ConOutput;

public class App {
    public static void main( String[] args ){
        //set input and output
        MainController.setInputOutput(new ConInput(), new ConOutput());
        //start the game loop
        MainController.gameLoop();
    }
}  
