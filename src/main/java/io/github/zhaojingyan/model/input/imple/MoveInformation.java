package io.github.zhaojingyan.model.input.imple;

import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.input.InputInformation;

public class MoveInformation implements InputInformation {
    private final int[] move;

    private MoveInformation(int[] move) {
        this.move = move;
    }

    public static MoveInformation create(String input) {
        int[] coordinates = new int[2];
        coordinates[0] = input.charAt(1) - '1';
        coordinates[1] = input.charAt(0) - 'A';
        return new MoveInformation(coordinates);
    }

    @Override
    public InputType getInputType() {
        return InputType.COORDINATES;
    }

    @Override
    public int[] getInfo() {
        return move;
    }
}