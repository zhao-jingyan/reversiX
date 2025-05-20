package io.github.zhaojingyan.ui.gui.buttons;

public final class PassButton extends CustomButton {
    private PassButton(ButtonManager buttonManager) {
        super("pass");
        setMinWidth(80);

        setOnAction(event -> {
            // 这里直接处理pass逻辑，比如调用buttonManager.pass()等
            buttonManager.getString(text);
            buttonManager.refreshAllButtons();
        });
    }

    public static PassButton create(ButtonManager buttonManager) {
        PassButton button = new PassButton(buttonManager);
        buttonManager.addButton(button);
        return button;
    }

    @Override
    public void updateAppearance() {
        boolean canPass = ButtonManager.getInstance().isWaitingForPass();
        setDisable(!canPass);
        if (canPass) {
            setStyle("-fx-background-color: #ffe066; -fx-border-color: #333; -fx-border-width: 2; -fx-font-weight: bold; -fx-background-insets: 0; -fx-background-radius: 6; -fx-cursor: hand;");
        } else {
            setStyle("-fx-background-insets: 0; -fx-background-radius: 6; -fx-cursor: not-allowed; -fx-opacity: 0.5;");
        }
    }
}
