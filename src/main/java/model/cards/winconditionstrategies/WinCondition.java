package model.cards.winconditionstrategies;

import model.gamemechanics.Player;
import model.worldstructure.World;

public interface WinCondition {
    boolean hasPlayerWon(Player cardOwner, World world);
}
