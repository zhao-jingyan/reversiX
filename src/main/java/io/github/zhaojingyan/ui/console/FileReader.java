package io.github.zhaojingyan.ui.console;

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

    public String getOneRawString(){
        return file[currentline++];
    }

    public boolean isEndOfFile(){
        return currentline == file.length;
    }
}
