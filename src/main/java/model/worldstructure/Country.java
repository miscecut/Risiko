package model.worldstructure;

import model.gamemechanics.Player;
import model.utils.exceptions.NonExistingTerritoryException;

import java.util.*;

public class Country {
    private final String name;
    private final Map<String,Territory> territories;
    private final int bonus;

    public Country(String name, Set<String> territoryNames, int bonus) {
        this.name = name;
        this.bonus = bonus;
        territories = new HashMap<>();
        territoryNames.forEach(territoryName -> territories.put(territoryName,new Territory(territoryName)));
    }

    public String getName() {
        return name;
    }

    public int getBonus() {
        return bonus;
    }

    public Territory getTerritory(String name) throws NonExistingTerritoryException {
        if(territories.containsKey(name))
            return territories.get(name);
        throw new NonExistingTerritoryException();
    }

    public Optional<Player> getOwner() {
        Optional<Territory> randomTerritory = territories.values().stream().findAny();
        if(randomTerritory.isPresent()) {
            Optional<Player> randomOwner = randomTerritory.get().getOwner();
            if(randomOwner.isPresent()) {
                for(Territory territory : territories.values()) {
                    if(territory.getOwner().isPresent() && !territory.getOwner().get().equals(randomOwner.get()))
                        return Optional.empty();
                }
                return randomOwner;
            }
            return Optional.empty();
        }
        return Optional.empty();
    }

    public Set<String> getOwnedTerritoryNames(Player player, int minimumArmies) {
        Set<String> ownedTerritories = new HashSet<>();
        for(Territory territory : territories.values()) {
            Optional<Player> territoryOwner = territory.getOwner();
            if(territoryOwner.isPresent() && territoryOwner.get().equals(player) && territory.getArmies() >= minimumArmies)
                ownedTerritories.add(territory.getName());
        }
        return ownedTerritories;
    }
}
