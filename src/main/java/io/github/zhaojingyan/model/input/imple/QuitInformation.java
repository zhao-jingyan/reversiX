package io.github.zhaojingyan.model.input.imple;

import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.input.InputInformation;

public class QuitInformation implements InputInformation {
    private QuitInformation() {
    }

    public static QuitInformation create() {
        return new QuitInformation();
    }

    @Override
    public InputType getInputType() {
        return InputType.QUIT;
    }

    @Override
    public Object getInfo() {
        return null;
    }
}