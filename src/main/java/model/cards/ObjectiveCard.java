package model.cards;

import model.cards.winconditionstrategies.WinCondition;

public class ObjectiveCard extends Card {
    private final WinCondition winCondition;

    public ObjectiveCard(String name, WinCondition winCondition) {
        super(name);
        this.winCondition = winCondition;
    }

    public WinCondition getWinCondition() {
        return winCondition;
    }
}
