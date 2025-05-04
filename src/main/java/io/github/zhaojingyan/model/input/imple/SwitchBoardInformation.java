package io.github.zhaojingyan.model.input.imple;

import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.input.InputInformation;

public class SwitchBoardInformation implements InputInformation {
    private final int boardNumber;

    private SwitchBoardInformation(int boardNumber) {
        this.boardNumber = boardNumber;
    }

    public static SwitchBoardInformation create(String input) {
        return new SwitchBoardInformation(Integer.parseInt(input));
    }

    @Override
    public InputType getInputType() {
        return InputType.BOARDNUM;
    }

    @Override
    public Object getInfo() {
        return boardNumber;
    }
}