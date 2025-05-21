package io.github.zhaojingyan.ui.interfaces;

import io.github.zhaojingyan.model.output.OutputInformation;
import io.github.zhaojingyan.model.service.GameException;

public interface OutputInterface {
    void print(OutputInformation output);

    void printError(GameException e, OutputInformation output);
}
