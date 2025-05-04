package io.github.zhaojingyan.model.input.imple;

import io.github.zhaojingyan.model.enums.GameMode;
import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.input.InputInformation;

public class NewGameInformation implements InputInformation {
    private final GameMode mode;

    private NewGameInformation(GameMode mode) {
        this.mode = mode;
    }

    public static NewGameInformation create(String input) {
        return new NewGameInformation(GameMode.valueOf(input.toUpperCase()));
    }

    @Override
    public InputType getInputType() {
        return InputType.NEWGAME;
    }

    @Override
    public Object getInfo() {
        return mode;
    }
}