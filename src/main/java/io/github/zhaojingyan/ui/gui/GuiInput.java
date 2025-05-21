package io.github.zhaojingyan.ui.gui;

import java.util.Queue;

import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.input.imple.InputInformationFactory;
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
            instance.pendingQueue.offer(text);
            instance.notifyAll();
        }
    }

    @Override
    public InputInformation getInput() {
        if (!isReadingFromFile) {
            String input = waitForInput();
            String rawInput = input;
            InputType infoType = InputParseUtil.determineType(rawInput);
            if (infoType == InputType.PLAYBACK) {
                // JavaFX 模式下弹出文件选择器
                final String[] filePath = {null};
                final Object lock = new Object();
                javafx.application.Platform.runLater(() -> {
                    javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
                    fileChooser.setTitle("please select a command file");
                    fileChooser.getExtensionFilters().add(
                        new javafx.stage.FileChooser.ExtensionFilter("command", "*.cmd")
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
                    isReadingFromFile = false;
                    return getInput();
                }
                boolean opened = fileReader.openFile(filePath[0], false);
                if (!opened) {
                    // 只允许本地文件，失败时重新弹出选择器
                    return getInput();
                }
                isReadingFromFile = true;
                rawInput = "playback " + filePath[0];
            }

            InputInformation inputInfo = InputInformationFactory.create(infoType, rawInput);
            return inputInfo;
        } else {
            try {
                java.util.concurrent.TimeUnit.MILLISECONDS.sleep(1000);
                String rawInput = fileReader.getOneRawString();
                isReadingFromFile = !fileReader.isEndOfFile();
                InputType infoType = InputParseUtil.determineType(rawInput);
                InputInformation inputInfo = InputInformationFactory.create(infoType, rawInput);
                return inputInfo;
            } catch (InterruptedException e) {
                System.err.println("Thread sleep err! Exiting...");
                System.exit(1);
                return null;
            }
        }
    }

    public synchronized String waitForInput() {
        while (pendingQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        String input = pendingQueue.poll();
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
