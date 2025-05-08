package io.github.zhaojingyan.model.input.imple;
import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.input.InputInformation;

public class BombInformation implements InputInformation {
    private final int[] bomb;

    private BombInformation(int[] bomb) {
        this.bomb = bomb;
    }

    public static BombInformation create(String input) {
        int[] coordinates = new int[2];
        coordinates[1] = Integer.parseInt(String.valueOf(input.charAt(1)), 16) - 1;
        coordinates[0] = input.charAt(2) - 'A';
        return new BombInformation(coordinates);
    }

    @Override
    public io.github.zhaojingyan.model.enums.InputType getInputType() {
        return InputType.BOMB;
    }

    @Override
    public int[] getInfo() {
        return bomb;
    }
    
}
