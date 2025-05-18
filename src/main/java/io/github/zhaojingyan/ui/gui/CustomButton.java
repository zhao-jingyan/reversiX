package io.github.zhaojingyan.ui.gui;

import javafx.scene.control.Button;

public class CustomButton extends Button {
    private final String info;

    public CustomButton(String info) {
        this.info = info;
        this.setOnAction(event -> handleClick());
    }

    private void handleClick() {
        System.out.println(info);
    }

    public String getInfo() {
        return info;
    }
}
