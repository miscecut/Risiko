package model.worldstructure;

import data.DataManager;
import model.Player;
import model.utils.exceptions.NonExistingTerritoryException;

import java.util.*;

public class World {
    private final Map<String,Country> countries;
    private final Map<String,Country> territoryCountryMap;
    private final Map<String,Set<String>> territoryBoundaries;

    public World() {
        DataManager dm = new DataManager();
        countries = new HashMap<>();
        territoryCountryMap = dm.generateWorldStructure();
        territoryBoundaries = new HashMap<>();
    }

    public Territory getTerritory(String name) throws NonExistingTerritoryException {
        if(territoryCountryMap.containsKey(name))
            return countries.get(name).getTerritory(name);
        throw new NonExistingTerritoryException();
    }

    public int getArmyBonus(Player player) {
        int bonus = getOwnedTerritoryNames(player).size() / 3;
        for(Country country : countries.values()) {
            Optional<Player> countryOwner = country.getOwner();
            if(countryOwner.isPresent() && countryOwner.get().equals(player))
                bonus += country.getBonus();
        }
        return bonus;
    }

    public Set<String> getOwnedTerritoryNames(Player player) {
        Set<String> ownedTerritories = new HashSet<>();
        for(Country country : countries.values())
            ownedTerritories.addAll(country.getOwnedTerritoryNames(player));
        return ownedTerritories;
    }
}
