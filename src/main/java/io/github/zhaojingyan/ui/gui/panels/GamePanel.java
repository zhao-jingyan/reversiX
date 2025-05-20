package io.github.zhaojingyan.ui.gui.panels;

import io.github.zhaojingyan.model.output.OutputInformation;

/**
 * 面板通用接口，便于统一管理和扩展
 */
public interface GamePanel {
    /**
     * 刷新/渲染面板内容
     * @param data 任意类型的数据对象（如OutputInformation等）
     */
    void render(OutputInformation data);
}
