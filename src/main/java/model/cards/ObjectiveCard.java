package model.cards;

import model.Player;
import model.cards.winconditionstrategies.WinCondition;
import model.worldstructure.World;

public class ObjectiveCard extends Card {
    private final WinCondition winCondition;

    public ObjectiveCard(String name, WinCondition winCondition) {
        super(name);
        this.winCondition = winCondition;
    }

    public boolean checkVictory(Player cardOwner, World world) {
        return winCondition.hasPlayerWon(cardOwner,world);
    }
}
