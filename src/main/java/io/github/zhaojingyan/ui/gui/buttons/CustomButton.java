package io.github.zhaojingyan.ui.gui.buttons;

import io.github.zhaojingyan.model.input.InputInformation;
import javafx.scene.control.Button;

/**
 * 自定义按钮，包含InputInformation
 * 每个按钮都维护一个InputInformation，用于发送到service层处理
 */
public class CustomButton extends Button {
    private InputInformation inputInfo;
    
    public CustomButton() {
        super();
    }
    
    public CustomButton(String text) {
        super(text);
    }
    
    /**
     * 设置按钮关联的输入信息
     * @param inputInfo 输入信息对象
     */
    public void setInputInfo(InputInformation inputInfo) {
        this.inputInfo = inputInfo;
    }
    
    /**
     * 获取按钮关联的输入信息
     * @return 输入信息对象
     */
    public InputInformation getInputInfo() {
        return inputInfo;
    }
    
    /**
     * 判断按钮是否有关联的输入信息
     * @return 是否有输入信息
     */
    public boolean hasInputInfo() {
        return inputInfo != null;
    }
}
