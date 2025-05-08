package io.github.zhaojingyan.model.input.imple;

import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.input.InputInformation;

public class PlaybackInformation implements InputInformation{

    private PlaybackInformation() {

    }

    public static PlaybackInformation create() {
        return new PlaybackInformation();
    }

    @Override
    public InputType getInputType() {
        return InputType.PLAYBACK;
    }

    @Override
    public Object getInfo() {
        return null;
    }
}
