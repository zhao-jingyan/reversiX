package io.github.zhaojingyan.ui.gui;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.ui.gui.buttons.CustomButton;
import io.github.zhaojingyan.ui.interfaces.InputInterface;

/**
 * GUI输入处理类，将按钮点击转换为InputInformation
 */
public class GuiInput implements InputInterface {
    private final BlockingQueue<InputInformation> inputQueue = new LinkedBlockingQueue<>();
    
    /**
     * 处理按钮点击，将按钮关联的InputInformation加入队列
     * @param button 被点击的CustomButton
     */
    public void handleButtonClick(CustomButton button) {
        if (button != null && button.hasInputInfo()) {
            InputInformation info = button.getInputInfo();
            inputQueue.offer(info); // 非阻塞式添加
        }
    }
    
    /**
     * 手动添加输入信息到队列
     * @param info 输入信息
     */
    public void addInputInfo(InputInformation info) {
        if (info != null) {
            inputQueue.offer(info);
        }
    }

    @Override
    public InputInformation getInput() {
        try {
            // 阻塞等待直到有输入
            return inputQueue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }
}
