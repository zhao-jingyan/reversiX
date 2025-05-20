package io.github.zhaojingyan.ui.console;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.JFileChooser;

import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.input.InputInformationFactory;
import io.github.zhaojingyan.ui.interfaces.InputInterface;
import io.github.zhaojingyan.ui.util.FileReader;

public class ConInput implements InputInterface {

    private String rawInput;
    private boolean isReadingFromFile;
    private final FileReader fileReader;
    private static final Scanner scanner = new Scanner(System.in);

    public ConInput() {
        fileReader = new FileReader();
        isReadingFromFile = false;
        scanner.useDelimiter("\\n");
    }

    @Override
    public InputInformation getInput() {
        // 第一步：读取输入
        if(!isReadingFromFile){
            while (!scanner.hasNextLine()) {}
            rawInput = scanner.nextLine();
            if(rawInput.length() >= 8 && rawInput.substring(0,8).toLowerCase().equals("playback")){
                String filePath = rawInput.length() > 9 ? rawInput.substring(9).trim() : "";
                boolean fileOpened = false;
                if (!filePath.isEmpty()) {
                    fileOpened = fileReader.openFile(filePath);
                }
                if (!fileOpened) {
                    // 弹出文件选择器
                    ConInput self = this;
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("请选择一个.cmd文件");
                        int result = fileChooser.showOpenDialog(null);
                        if (result == JFileChooser.APPROVE_OPTION) {
                            File selectedFile = fileChooser.getSelectedFile();
                            fileReader.openFile(selectedFile.getAbsolutePath());
                            synchronized (self) {
                                isReadingFromFile = true;
                                self.notifyAll();
                            }
                        }
                    });
                    // 等待用户选择
                    synchronized (this) {
                        while (!isReadingFromFile) {
                            try {
                                wait();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                } else {
                    isReadingFromFile = true;
                }
            }
        }
        else{
            try{
                TimeUnit.MILLISECONDS.sleep(100);
                rawInput = fileReader.getOneRawString();
                System.out.println(rawInput);   //在控制台上显示这个输入
                TimeUnit.MILLISECONDS.sleep(30);
                isReadingFromFile = !fileReader.isEndOfFile();
            } catch(InterruptedException e) {
                System.err.println("Thread sleep err! Exiting...");
                System.exit(1);
            }
        }
        // 第二步：判断输入类型
        InputType infoType = io.github.zhaojingyan.ui.util.InputParseUtil.determineType(rawInput);
        // 第三步：根据输入类型创建对应的信息对象
        return InputInformationFactory.create(infoType, rawInput);
    }
}
