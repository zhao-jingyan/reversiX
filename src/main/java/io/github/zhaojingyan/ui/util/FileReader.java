package io.github.zhaojingyan.ui.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

    public String getOneRawString(){
        return file[currentline++];
    }

    public boolean isEndOfFile(){
        return currentline == file.length;
    }
}
