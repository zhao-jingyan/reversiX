package io.github.zhaojingyan.ui.gui;

import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.input.InputInformationFactory;
import io.github.zhaojingyan.ui.interfaces.InputInterface;
import io.github.zhaojingyan.ui.util.InputParseUtil;

public class GuiInput implements InputInterface {
    private String rawInput;
    private boolean isReadingFromFile;
    private final io.github.zhaojingyan.ui.util.FileReader fileReader = new io.github.zhaojingyan.ui.util.FileReader();

    // 输入相关字段
    private static boolean isBombMode = false;
    // 字段isWaitingForPass可以为final的警告无需处理，因为它会被修改
    private boolean isWaitingForPass = false;
    private String pendingInput = null;

    private static GuiInput INSTANCE = new GuiInput();

    public static GuiInput getInstance() {
        return INSTANCE;
    }

    // 迁移自 ButtonManager，处理按钮点击事件
    public static void handleButtonInput(String text) {
        GuiInput instance = getInstance();
        synchronized (instance) {
            // 只有在pendingInput为null时才写入，防止丢失输入
            if (instance.pendingInput == null) {
                instance.pendingInput = text;
                instance.notifyAll();
            }
        }
    }

    @Override
    public InputInformation getInput() {
        if (!isReadingFromFile) {
            // 等待ButtonManager返回输入字符串
            String input = waitForInput();
            rawInput = input;
            if (rawInput.length() >= 8 && rawInput.substring(0, 8).toLowerCase().equals("playback")) {
                isReadingFromFile = fileReader.openFile(rawInput.substring(9));
            }
        } else {
            try {
                java.util.concurrent.TimeUnit.MILLISECONDS.sleep(100);
                rawInput = fileReader.getOneRawString();
                System.out.println(rawInput); // 在控制台上显示这个输入
                java.util.concurrent.TimeUnit.MILLISECONDS.sleep(30);
                isReadingFromFile = !fileReader.isEndOfFile();
            } catch (InterruptedException e) {
                System.err.println("Thread sleep err! Exiting...");
                System.exit(1);
            }
        }
        InputType infoType = InputParseUtil.determineType(rawInput);
        InputInformation inputInfo = InputInformationFactory.create(infoType, rawInput);
        System.out.println("[DEBUG] InputInformation: " + inputInfo.toString());
        return inputInfo;
    }

    public synchronized String waitForInput() {
        while (pendingInput == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        String input = pendingInput;
        pendingInput = null;
        if (isBombMode) {
            return "@" + input;
        } else {
            return input;
        }
    }

    public static void toggleBombMode() {
        isBombMode = !isBombMode;
    }

    public boolean isBombMode() {
        return isBombMode;
    }

    public boolean isWaitingForPass() {
        return isWaitingForPass;
    }

}
