package io.github.zhaojingyan.ui.gui.buttons;

import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.input.InputInformationFactory;

/**
 * 棋盘棋子按钮，维护坐标信息
 */
public final class CellButton extends CustomButton {
    private final int row;
    private final int col;
    private final String coord;
    
    /**
     * 创建棋盘格子按钮
     * @param row 行坐标
     * @param col 列坐标
     */
    public CellButton(int row, int col) {
        super();
        this.row = row;
        this.col = col;
        this.coord = String.format("%X%c", col + 1, (char)('A' + row));
        setMinSize(40, 40);
        setMaxSize(40, 40);
        setPrefSize(40, 40);
        setFocusTraversable(false);
        
        // 创建坐标输入信息并关联到按钮
        updateInputInfo();
    }
    
    /**
     * 根据当前坐标更新输入信息
     */
    public void updateInputInfo() {
        InputInformation inputInfo = InputInformationFactory.create(InputType.COORDINATES, coord);
        setInputInfo(inputInfo);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
