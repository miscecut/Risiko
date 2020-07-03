package model.worldstructure;

import data.DataManager;
import model.gamemechanics.Player;
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
        return getOwnedTerritoryNames(player,0);
    }

    public Set<String> getOwnedCountryNames(Player player) {
        Set<String> requestedCountries = new HashSet<>();
        countries.values().stream().filter(country -> country.getOwner().isPresent() && country.getOwner().get().equals(player)).map(Country::getName).forEach(requestedCountries::add);
        return requestedCountries;
    }

    public Set<String> fromWhereCanPlayerMove(Player player) {
        Set<String> requestedTerritories = getOwnedTerritoryNames(player,2);
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

    public Set<String> fromWhereCanPlayerAttack(Player player) {
        Set<String> requestedTerritories = getOwnedTerritoryNames(player,2);
        requestedTerritories.removeAll(getTerritoriesSurroundedByOnlyAllies(player,new HashSet<>(requestedTerritories)));
        return requestedTerritories;
    }

    public Set<String> whereCanPlayerAttack(Player player, String attackingTerritory) throws NonExistingTerritoryException{
        Set<String> requestedTerritories = new HashSet<>();
        if(!territoryBoundaries.containsKey(attackingTerritory))
            throw new NonExistingTerritoryException();
        territoryBoundaries.get(attackingTerritory).stream().filter(boundary -> {
            Optional<Player> boundaryOwner = territoryCountryMap.get(boundary).getOwner();
            return boundaryOwner.isEmpty() || !boundaryOwner.get().equals(player);
        }).forEach(requestedTerritories::add);
        return requestedTerritories;
    }

    public Set<String> getOwnedTerritoryNames(Player player, int minimumArmies) {
        Set<String> ownedTerritories = new HashSet<>();
        for(Country country : countries.values())
            ownedTerritories.addAll(country.getOwnedTerritoryNames(player,minimumArmies));
        return ownedTerritories;
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

    private Set<String> getTerritoriesSurroundedByOnlyAllies(Player owner, Set<String> territories) {
        Set<String> requestedTerritories = new HashSet<>();
        territories.stream().filter(territory -> {
            for(String boundary : territoryBoundaries.get(territory)) {
                if(territoryCountryMap.get(boundary).getOwner().isPresent() && territoryCountryMap.get(boundary).getOwner().get().equals(owner))
                    return true;
            }
            return false;
        }).forEach(requestedTerritories::add);
        return requestedTerritories;
    }
}
