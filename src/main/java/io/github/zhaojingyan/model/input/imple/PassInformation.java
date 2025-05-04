package io.github.zhaojingyan.model.input.imple;

import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.input.InputInformation;

public class PassInformation implements InputInformation {
    private PassInformation() {
    }

    public static PassInformation create() {
        return new PassInformation();
    }

    @Override
    public InputType getInputType() {
        return InputType.PASS;
    }

    @Override
    public Object getInfo() {
        return null;
    }
}