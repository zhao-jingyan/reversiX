package io.github.zhaojingyan.ui.gui.buttons;

public class NewGameButton extends CustomButton {
    private final String gameName;

    private NewGameButton(String gameName, ButtonManager buttonManager) {
        super(gameName);
        this.gameName = gameName;
        setMinWidth(80);

        setOnAction(event -> {
            buttonManager.getString(gameName);
            buttonManager.refreshAllButtons();
        });
    }

    public static NewGameButton create(String gameName, ButtonManager buttonManager) {
        NewGameButton button = new NewGameButton(gameName, buttonManager);
        buttonManager.addButton(button);
        return button;
    }

    @Override
    public void updateAppearance() {
        //null
    }
}