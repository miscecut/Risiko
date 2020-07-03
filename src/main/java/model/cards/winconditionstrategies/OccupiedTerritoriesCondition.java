package model.cards.winconditionstrategies;

import model.gamemechanics.Player;
import model.worldstructure.World;

public class OccupiedTerritoriesCondition implements WinCondition {
    private final int ownedTerritoryAmount;
    private final int minimumArmies;

    public OccupiedTerritoriesCondition(int ownedTerritoryAmount) {
        this.ownedTerritoryAmount = ownedTerritoryAmount;
        this.minimumArmies = 1;
    }

    public OccupiedTerritoriesCondition(int ownedTerritoryAmount, int minimumArmies) {
        this.ownedTerritoryAmount = ownedTerritoryAmount;
        this.minimumArmies = minimumArmies;
    }

    @Override
    public boolean hasPlayerWon(Player cardOwner, World world) {
        return world.getOwnedTerritoryNames(cardOwner,minimumArmies).size() >= ownedTerritoryAmount;
    }
}
