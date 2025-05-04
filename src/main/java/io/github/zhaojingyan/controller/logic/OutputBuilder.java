package io.github.zhaojingyan.controller.logic;

import io.github.zhaojingyan.controller.game.Game;
import io.github.zhaojingyan.controller.game.GameManager;
import io.github.zhaojingyan.model.enums.OutputType;
import io.github.zhaojingyan.model.output.GameInfo;
import io.github.zhaojingyan.model.output.GlobalInfo;
import io.github.zhaojingyan.model.output.OutputInformation;


public class OutputBuilder {
        protected static OutputInformation create(OutputType type, GameManager gameManager) {
        Game game = gameManager.getCurrentGame();
        GameManager gameManagerInstance = GameManager.getInstance();

        return new OutputInformation(new GameInfo(game), new GlobalInfo(gameManagerInstance), type);
    }
}
