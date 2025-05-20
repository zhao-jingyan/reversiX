package io.github.zhaojingyan.ui.gui;

import java.util.Queue;

import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.input.InputInformationFactory;
import io.github.zhaojingyan.ui.interfaces.InputInterface;
import io.github.zhaojingyan.ui.util.InputParseUtil;

public class GuiInput implements InputInterface {
    private final Queue<String> pendingQueue = new java.util.LinkedList<>();
    private static boolean isBombMode = false;
    private boolean isReadingFromFile = false;
    private final io.github.zhaojingyan.ui.util.FileReader fileReader = new io.github.zhaojingyan.ui.util.FileReader();

    private final static GuiInput INSTANCE = new GuiInput();

    public static GuiInput getInstance() {
        return INSTANCE;
    }

    // 迁移自 ButtonManager，处理按钮点击事件
    public static void handleButtonInput(String text) {
        GuiInput instance = getInstance();
        synchronized (instance) {
            System.out.println("[DEBUG] handleButtonInput 收到: " + text);
            instance.pendingQueue.offer(text);
            instance.notifyAll();
        }
    }

    @Override
    public InputInformation getInput() {
        System.out.println("[DEBUG] getInput() called, isReadingFromFile=" + isReadingFromFile);
        if (!isReadingFromFile) {
            String input = waitForInput();
            System.out.println("[DEBUG] getInput() waitForInput返回: " + input);
            String rawInput = input;
            InputType infoType = InputParseUtil.determineType(rawInput);
            if (infoType == InputType.PLAYBACK) {
                // JavaFX 模式下弹出文件选择器
                final String[] filePath = {null};
                final Object lock = new Object();
                javafx.application.Platform.runLater(() -> {
                    javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
                    fileChooser.setTitle("请选择一个.cmd文件");
                    fileChooser.getExtensionFilters().add(
                        new javafx.stage.FileChooser.ExtensionFilter("命令文件", "*.cmd")
                    );
                    javafx.stage.Window window = null;
                    try {
                        window = javafx.stage.Window.getWindows().stream().filter(javafx.stage.Window::isShowing).findFirst().orElse(null);
                    } catch (Exception e) {
                        // ignore
                    }
                    java.io.File selectedFile = fileChooser.showOpenDialog(window);
                    if (selectedFile != null) {
                        filePath[0] = selectedFile.getAbsolutePath();
                    }
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                });
                synchronized (lock) {
                    boolean fileChosen = false;
                    while (!fileChosen) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        fileChosen = true;
                    }
                }
                if (filePath[0] == null) {
                    System.out.println("[DEBUG] 用户取消了文件选择，恢复普通输入");
                    isReadingFromFile = false;
                    return getInput();
                }
                boolean opened = fileReader.openLocalFile(filePath[0]);
                if (!opened) {
                    System.out.println("[DEBUG] 选择的文件无法打开或不存在，仅支持本地文件，请重新选择");
                    // 只允许本地文件，失败时重新弹出选择器
                    return getInput();
                }
                isReadingFromFile = true;
                System.out.println("[DEBUG] getInput() 进入文件读取模式");
                rawInput = "playback " + filePath[0];
            }

            InputInformation inputInfo = InputInformationFactory.create(infoType, rawInput);
            System.out.println("[DEBUG] InputInformation: " + inputInfo.toString());
            return inputInfo;
        } else {
            try {
                System.out.println("[DEBUG] getInput() 文件模式，读取一行...");
                java.util.concurrent.TimeUnit.MILLISECONDS.sleep(100);
                String rawInput = fileReader.getOneRawString();
                System.out.println("[DEBUG] getInput() 文件读取: " + rawInput);
                java.util.concurrent.TimeUnit.MILLISECONDS.sleep(30);
                isReadingFromFile = !fileReader.isEndOfFile();
                InputType infoType = InputParseUtil.determineType(rawInput);
                InputInformation inputInfo = InputInformationFactory.create(infoType, rawInput);
                System.out.println("[DEBUG] InputInformation: " + inputInfo.toString());
                return inputInfo;
            } catch (InterruptedException e) {
                System.err.println("Thread sleep err! Exiting...");
                System.exit(1);
                return null;
            }
        }
    }

    public synchronized String waitForInput() {
        System.out.println("[DEBUG] waitForInput 阻塞等待输入...");
        while (pendingQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        String input = pendingQueue.poll();
        System.out.println("[DEBUG] waitForInput 收到输入: " + input);
        if (isBombMode) {
            isBombMode = false;
            return InputParseUtil.isCoordinate(input) ? "@" + input : input;
        }
        return input;
    }

    public static void toggleBombMode() {
        isBombMode = !isBombMode;
    }

    public boolean isBombMode() {
        return isBombMode;
    }

}
