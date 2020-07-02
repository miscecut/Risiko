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
        territoryCountryMap.values().forEach(country -> countries.putIfAbsent(country.getName(),country));
        territoryBoundaries = dm.generateBoundariesMap();
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

    public Set<String> fromWhereCanPlayerMove(Player player) {
        Set<String> requestedTerritories = new HashSet<>();
        countries.values().forEach(country -> requestedTerritories.addAll(country.getOwnedTerritoryNames(player,2)));
        requestedTerritories.removeAll(getTerritoriesSurroundedByOnlyEnemies(player,new HashSet<>(requestedTerritories)));
        return requestedTerritories;
    }

    public Set<String> whereCanPlayerMove(Player player, String departureTerritory) throws NonExistingTerritoryException {
        Set<String> requestedTerritories = new HashSet<>();
        if(!territoryBoundaries.containsKey(departureTerritory))
            throw new NonExistingTerritoryException();
        territoryBoundaries.get(departureTerritory).stream().filter(boundary -> {
            Optional<Player> boundaryOwner = territoryCountryMap.get(boundary).getOwner();
            return boundaryOwner.isEmpty() || boundaryOwner.get().equals(player);
        }).forEach(requestedTerritories::add);
        return requestedTerritories;
    }

    private Set<String> getTerritoriesSurroundedByOnlyEnemies(Player owner, Set<String> territories) {
        Set<String> requestedTerritories = new HashSet<>();
        territories.stream().filter(territory -> {
            for(String boundary : territoryBoundaries.get(territory)) {
                if(territoryCountryMap.get(boundary).getOwner().isEmpty() || territoryCountryMap.get(boundary).getOwner().get().equals(owner))
                    return false;
            }
            return true;
        }).forEach(requestedTerritories::add);
        return requestedTerritories;
    }
}
