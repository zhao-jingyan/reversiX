package io.github.zhaojingyan.ui.interfaces;

import io.github.zhaojingyan.model.service.GameException;
import io.github.zhaojingyan.model.output.OutputInformation;


public interface OutputInterface {
    void print(OutputInformation output);
    void printError(GameException e, OutputInformation output);
}
