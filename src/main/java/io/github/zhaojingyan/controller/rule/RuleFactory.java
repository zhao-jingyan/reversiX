package io.github.zhaojingyan.controller.rule;

import io.github.zhaojingyan.controller.rule.imple.GomokuRule;
import io.github.zhaojingyan.controller.rule.imple.PeaceRule;
import io.github.zhaojingyan.controller.rule.imple.ReversiRule;
import io.github.zhaojingyan.model.enums.GameMode;

public class RuleFactory {
    public static Rule createRule(GameMode mode) {
        switch (mode) {
            case PEACE   -> { return new PeaceRule();  }
            case GOMOKU  -> { return new GomokuRule(); }
            case REVERSI -> { return new ReversiRule();}
            default      -> { throw new IllegalArgumentException("Invalid game mode: " + mode); }
        }
    }
}
