package io.github.zhaojingyan.model.input.imple;

import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.input.InputInformation;

public class InvalidInformation implements InputInformation {
    private InvalidInformation() {
    }

    public static InvalidInformation create() {
        return new InvalidInformation();
    }

    @Override
    public InputType getInputType() {
        return InputType.INVALID;
    }

    @Override
    public Object getInfo() {
        return null;
    }
}