package io.github.zhaojingyan.ui.gui.buttons;

public final class GameSwitchButton extends CustomButton {
    private final int gameNumber;

    private GameSwitchButton(int gameNumber, String gameMode, ButtonManager buttonManager) {
        super(String.valueOf(gameNumber) + gameMode);
        this.gameNumber = gameNumber;
        setMinWidth(80);

        setOnAction(event -> {
            buttonManager.getString(String.valueOf(gameNumber));
            buttonManager.refreshAllButtons();
        });
    }

    public static GameSwitchButton create(int gameNumber, String gameMode, ButtonManager buttonManager) {
        GameSwitchButton button = new GameSwitchButton(gameNumber, gameMode, buttonManager);
        buttonManager.addButton(button);
        return button;
    }

    @Override
    public void updateAppearance() {
        if (ButtonManager.getInstance().getCurrentGameNumber() == gameNumber) {
            setStyle(
                    "-fx-background-color: #66ff66; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-insets: 0; -fx-background-radius: 6; -fx-cursor: hand;");
        } else {
            setStyle("-fx-background-insets: 0; -fx-background-radius: 6; -fx-cursor: hand;");
        }
    }

}
