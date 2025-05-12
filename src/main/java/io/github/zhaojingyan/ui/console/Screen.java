package io.github.zhaojingyan.ui.console;

import java.io.IOException;

public class Screen {
    private int width;
    private int height;
    private char[][] buffer;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        this.buffer = new char[height][width];
        clearBuffer();
    }

    private void clearBuffer() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                buffer[i][j] = ' ';
            }
        }
    }

    public void display() {
        clear();
        System.out.flush();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(buffer[i][j]);
            }
            System.out.println();
        }
    }

    private void adjustHeight(int height2) {
        if (height2 > this.height) {
            char[][] newBuffer = new char[height2][this.width];
            int topPadding = (height2 - this.height) / 2;

            for (int i = 0; i < height2; i++) {
                if (i >= topPadding && i < topPadding + this.height) {
                    newBuffer[i] = this.buffer[i - topPadding];
                } else {
                    newBuffer[i] = new char[this.width];
                    for (int j = 0; j < this.width; j++) {
                        newBuffer[i][j] = ' ';
                    }
                }
            }
            this.buffer = newBuffer;
            this.height = height2;
        }
    }
    
    private static void clear() {
        try {
            // 获取操作系统名称
            String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                // 在 Windows 系统中，使用 cls 命令清除控制台
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // 在其他系统中，使用 clear 命令清除控制台
                new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
        }
    }

    public static Screen mergeScreens(Screen leftScreen, Screen rightScreen) {
        int height = Math.max(leftScreen.getHeight(), rightScreen.getHeight());

        // 调整每个屏幕的高度以匹配最大高度
        leftScreen.adjustHeight(height);
        rightScreen.adjustHeight(height);

        // 创建一个新的屏幕用于粘合
        Screen mergedScreen = new Screen(leftScreen.getWidth() + rightScreen.getWidth(), height);

        for (int i = 0; i < height; i++) {
            StringBuilder row = new StringBuilder();
            row.append(leftScreen.getRow(i));
            row.append(rightScreen.getRow(i));
            mergedScreen.setRow(i, row.toString());
        }

        return mergedScreen;
    }

    public void extend(){
        char[][] newBuffer = new char[height][width * 2];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
            newBuffer[i][j * 2] = buffer[i][j];
            newBuffer[i][j * 2 + 1] = ' ';
            }
        }
        buffer = newBuffer;
        width *= 2;
    }

    public int getWidth() { return width;}
    public int getHeight() { return height;}
    public void setPixel(int x, int y, char c) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            buffer[y][x] = c;
        }
    }

    public void setRow(int index, String row) {
        if (index >= 0 && index < height) {
            for (int i = 0; i < width && i < row.length(); i++) {
                buffer[index][i] = row.charAt(i);
            }
        }
    }
    
    public String getRow(int index) {
        if (index >= 0 && index < height) {
            return new String(buffer[index]);
        }
        return null;
    }


}
