package io.github.zhaojingyan.ui.gui.buttons;

import javafx.scene.control.Button;

public abstract class CustomButton extends Button {
    public final String text;
    
    public CustomButton(String text) {
        super(text);
        this.text = text;
    }

    public abstract void updateAppearance();

}
