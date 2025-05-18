package io.github.zhaojingyan.ui.console;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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
        if(!isReadingFromFile){
            while (!scanner.hasNextLine()) {}
            rawInput = scanner.nextLine();
            if(rawInput.length() >= 8 && rawInput.substring(0,8).toLowerCase().equals("playback")){
                isReadingFromFile = fileReader.openFile(rawInput.substring(9));
            }
        }
        else{
            try{
                TimeUnit.MILLISECONDS.sleep(1000);
                rawInput = fileReader.getOneRawString();
                System.out.println(rawInput);   //在控制台上显示这个输入
                TimeUnit.MILLISECONDS.sleep(300);
                isReadingFromFile = !fileReader.isEndOfFile();
            } catch(InterruptedException e) {
                System.err.println("Thread sleep err! Exiting...");
                System.exit(1);
            }
        }

        return InputInformationFactory.create(rawInput);
    }


}
