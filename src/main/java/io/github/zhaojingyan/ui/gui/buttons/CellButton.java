package io.github.zhaojingyan.ui.gui.buttons;

public final class CellButton extends CustomButton {
    private final String coord;

    private CellButton(int row, int col, ButtonManager buttonManager) {
        super("");
        coord = String.format("%X%c", row + 1, (char)('A' + col));
        setMinSize(40, 40);
        setMaxSize(40, 40);
        setPrefSize(40, 40);
        setFocusTraversable(false);
        // 关键：去除按钮的凸起/阴影/边框圆角等视觉效果，完全平面
        setStyle("-fx-background-radius: 0; -fx-background-insets: 0; -fx-padding: 0; -fx-border-radius: 0; -fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-box-shadow: none; -fx-effect: none; -fx-background-color: transparent;");
        this.setOnAction(event -> {
            buttonManager.getString(coord);
            buttonManager.refreshAllButtons();
        });
    }

    public static CellButton create(int row, int col, ButtonManager buttonManager) {
        CellButton button = new CellButton(row, col, buttonManager);
        buttonManager.addButton(button);
        return button;
    }

    @Override
    public void updateAppearance() {
    }

}
