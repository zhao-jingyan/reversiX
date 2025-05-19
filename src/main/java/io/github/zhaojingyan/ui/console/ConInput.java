package io.github.zhaojingyan.ui.console;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import io.github.zhaojingyan.model.enums.InputType;
import io.github.zhaojingyan.model.input.InputInformation;
import io.github.zhaojingyan.model.input.InputInformationFactory;
import io.github.zhaojingyan.ui.interfaces.InputInterface;


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
                isReadingFromFile = fileReader.openFile(rawInput.substring(9));
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
        InputType infoType = determineType(rawInput);
        // 第三步：根据输入类型创建对应的信息对象
        return InputInformationFactory.create(infoType, rawInput);
    }

    // 判断输入类型
    private static InputType determineType(String input) {
        if(input.length() > 9 && input.substring(0,9).toLowerCase().equals("playback ")){
            return InputType.PLAYBACK;
        }
        // 检查是否是坐标
        if(input.length() == 3 && input.charAt(0) == '@'  && isCoordinate(input.substring(1))) {
            return InputType.BOMB;
        }
        else if (isCoordinate(input)) {
            return InputType.COORDINATES;
        }
        // 检查是否是pass
        else if (input.toLowerCase().equals("pass")) {
            return InputType.PASS;
        }
        // 检查是否是quit
        else if (input.toLowerCase().equals("quit")) {
            return InputType.QUIT;
        }
        // 检查是否是newgame
        else if (input.toLowerCase().equals("peace") || input.toLowerCase().equals("reversi")
                || input.toLowerCase().equals("gomoku")) {
            return InputType.NEWGAME;
        }
        // 检查是否是boardnum
        else {
            try {
                if (Integer.parseInt(input) >= 1) {
                    return InputType.BOARDNUM;
                } else {
                    return InputType.INVALID;
                }
            } catch (NumberFormatException e) {
                return InputType.INVALID;
            }
        }
    }

    private static boolean isCoordinate(String input) {
        return input.length() == 2 &&
            ((input.charAt(1) >= 'A' && input.charAt(1) <= 'O') ||
            (input.charAt(1) >= 'a' && input.charAt(1) <= 'o')) &&
            ((input.charAt(0) >= '1' && input.charAt(0) <= '9') ||
            (input.charAt(0) >= 'A' && input.charAt(0) <= 'F') ||
            (input.charAt(0) >= 'a' && input.charAt(0) <= 'f'));
    }
}
