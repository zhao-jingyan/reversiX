package io.github.zhaojingyan.ui.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileReader {
    private String[] file;
    private int currentline;

    public FileReader(){
        file = null;
        currentline = 0;
    }

    public boolean openFile(String path, boolean isResource) {
        file = null;
        currentline = 0;
        try {
            InputStream is = isResource
                ? getClass().getClassLoader().getResourceAsStream("testcmds/" + path)
                : new FileInputStream(new File(path));
            if (is == null) {
                System.err.println("Error: File not found - " + path);
                return false;
            }

            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }

            file = lines.toArray(String[]::new);
            return true;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return false;
        }
    }

    /**
     * 用 JavaFX FileChooser 弹窗选择文件，返回绝对路径。控制台/GUI 通用。
     */
    public static String pickFileWithJavaFX(String title, String... extensions) {
        final String[] result = new String[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Runnable fxTask = () -> {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle(title != null ? title : "Choose");
                if (extensions != null && extensions.length > 0) {
                    fileChooser.getExtensionFilters().add(
                        new FileChooser.ExtensionFilter("file", extensions)
                    );
                }
                File file = fileChooser.showOpenDialog(new Stage());
                result[0] = (file != null) ? file.getAbsolutePath() : null;
            } finally {
                latch.countDown();
            }
        };
        if (Platform.isFxApplicationThread()) {
            fxTask.run();
        } else {
            try {
                Platform.startup(() -> {}); // 若未启动则启动
            } catch (IllegalStateException ignore) {}
            Platform.runLater(fxTask);
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return result[0];
    }

    public String getOneRawString(){
        return file[currentline++];
    }

    public boolean isEndOfFile(){
        return currentline == file.length;
    }
}
