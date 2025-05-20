package io.github.zhaojingyan.ui.gui.buttons;

import io.github.zhaojingyan.ui.gui.GuiInput;

public class PlayBackButton extends CustomButton {
    private PlayBackButton() {
        super("Playback");
        setMinWidth(80);
        setStyle("-fx-background-insets: 0; -fx-background-radius: 6; -fx-cursor: hand;");
        setOnAction(event -> {
            GuiInput.handleButtonInput("playback");
        });
    }

    public static PlayBackButton create() {
        PlayBackButton button = new PlayBackButton();
        ButtonManager.getInstance().addButton(button);
        return button;
    }

    @Override
    public void updateAppearance() {
        setStyle("-fx-background-insets: 0; -fx-background-radius: 6; -fx-cursor: hand;");
    }
}
