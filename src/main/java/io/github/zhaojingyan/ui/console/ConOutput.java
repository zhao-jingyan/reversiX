package io.github.zhaojingyan.ui.console;


import io.github.zhaojingyan.model.output.OutputInformation;
import io.github.zhaojingyan.ui.interfaces.OutputInterface;
import io.github.zhaojingyan.model.service.GameException;

public class ConOutput implements OutputInterface {
    private final ScreenBuilder screenBuilder = new ScreenBuilder();
    private final PromptBuilder promptBuilder = new PromptBuilder();
    public ConOutput() {
    }

    @Override
    public void print(OutputInformation output) {
        Screen[] screens = new Screen[4];
        screens[0] = screenBuilder.constructBoardPanel(output);
        screens[1] = screenBuilder.constructPlayerPanel(output);
        screens[2] = screenBuilder.constructGameInfoPanel(output);
        screens[3] = screenBuilder.constructGameListPanel(output);
        screens[0].extend();
        Screen outputScreen = new Screen(0, 0);
        for(Screen item : screens) {
            outputScreen = Screen.mergeScreens(outputScreen, item);
        }
        outputScreen.display();
        System.out.print("\n" + promptBuilder.buildPrompt(output) + " ");
    }

    @Override
    public void printError(GameException e, OutputInformation output) {
        System.err.printf("%s\n\n", e.getMessage());
        System.out.print(promptBuilder.buildPrompt(output) + " ");
    }
}
