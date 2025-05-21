package io.github.zhaojingyan.ui.console;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.input.imple.InputInformationFactory;
import io.github.zhaojingyan.ui.interfaces.InputInterface;
import io.github.zhaojingyan.ui.util.FileReader;
import io.github.zhaojingyan.ui.util.InputParseUtil;

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
        while (true) {
            if (!isReadingFromFile) {
                while (!scanner.hasNextLine()) {
                }
                rawInput = scanner.nextLine();
                if (rawInput.length() >= 8 && rawInput.substring(0, 8).toLowerCase().equals("playback")) {
                    String filePath = rawInput.length() > 9 ? rawInput.substring(9).trim() : "";
                    if (!filePath.isEmpty()) {
                        // Use the unified openFile method with isResource flag
                        boolean fileOpened = fileReader.openFile(filePath, true);
                        if (!fileOpened) {
                            return InputInformationFactory.create(InputType.INVALID, rawInput);
                        } else {
                            isReadingFromFile = true;
                        }
                    } else {
                        // Use the unified openFile method for local files
                        String chosen = FileReader.pickFileWithJavaFX("Please select a .cmd file", "*.cmd");
                        if (chosen != null) {
                            boolean fileOpened = fileReader.openFile(chosen, false);
                            if (!fileOpened) {
                                return InputInformationFactory.create(InputType.INVALID, rawInput);
                            } else {
                                isReadingFromFile = true;
                            }
                        } else {
                            return InputInformationFactory.create(InputType.INVALID, rawInput);
                        }
                    }
                }
            } else {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                    rawInput = fileReader.getOneRawString();
                    System.out.println(rawInput); // 在控制台上显示这个输入
                    TimeUnit.MILLISECONDS.sleep(30);
                    isReadingFromFile = !fileReader.isEndOfFile();
                } catch (InterruptedException e) {
                    System.err.println("Thread sleep err! Exiting...");
                    System.exit(1);
                }
            }
            break;
        }

        // 第二步：判断输入类型
        InputType infoType = InputParseUtil.determineType(rawInput);

        // 第三步：根据输入类型创建对应的信息对象
        return InputInformationFactory.create(infoType, rawInput);
    }
}
