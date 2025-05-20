package io.github.zhaojingyan.ui.util;

import java.io.BufferedReader;
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

    public boolean openFile(String fileName) {
        file = null;
        currentline = 0;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("testcmds/" + fileName)) {
            if (is == null) {
                System.err.println("Error: Resource not found - " + fileName);
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
            System.err.println("Error reading resource: " + e.getMessage());
            return false;
        }
    }

    // 新增：支持直接读取本地文件
    @SuppressWarnings("CollectionsToArray")
    public boolean openLocalFile(String filePath) {
        file = null;
        currentline = 0;
        java.io.File localFile = new java.io.File(filePath);
        if (!localFile.exists() || !localFile.isFile()) {
            System.err.println("Error: Local file not found - " + filePath);
            return false;
        }
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(localFile))) {
            java.util.List<String> lines = new java.util.ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            file = lines.toArray(new String[0]);
            return true;
        } catch (java.io.IOException e) {
            System.err.println("Error reading local file: " + e.getMessage());
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
                fileChooser.setTitle(title != null ? title : "请选择文件");
                if (extensions != null && extensions.length > 0) {
                    fileChooser.getExtensionFilters().add(
                        new FileChooser.ExtensionFilter("文件", extensions)
                    );
                }
                java.io.File file = fileChooser.showOpenDialog(new Stage());
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
