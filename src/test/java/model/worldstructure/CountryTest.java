package model.worldstructure;

import model.gamemechanics.ArmyColor;
import model.gamemechanics.Player;
import model.utils.exceptions.NonExistingTerritoryException;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {

    @Test
    void getTerritory() {
        Country southAmerica = new Country("South America",new HashSet<>(){{add("Argentina");add("Brazil");add("Venezuela");add("Peru");}},2);
        try {
            southAmerica.getTerritory("Japan");
        } catch (NonExistingTerritoryException e) {
            assert true;
        }
        try {
            assert southAmerica.getTerritory("Argentina").getName().equals("Argentina");
        } catch (NonExistingTerritoryException e) {
            assert false;
        }
    }

    @Test
    void getOwner() {
        Country southAmerica = new Country("South America",new HashSet<>(){{add("Argentina");add("Brazil");add("Venezuela");add("Peru");}},2);
        try {
            Player mary = new Player(ArmyColor.YELLOW,"Mary");
            Player alice = new Player(ArmyColor.GREEN,"Alice");
            southAmerica.getTerritory("Argentina").addArmies(mary,1);
            southAmerica.getTerritory("Peru").addArmies(mary,2);
            southAmerica.getTerritory("Brazil").addArmies(mary,2);
            assert southAmerica.getOwner().isEmpty();
            southAmerica.getTerritory("Venezuela").addArmies(mary,3);
            assert southAmerica.getOwner().isPresent() && southAmerica.getOwner().get().equals(mary);
            southAmerica.getTerritory("Peru").addArmies(alice,3);
            assert southAmerica.getOwner().isEmpty();
        } catch (NonExistingTerritoryException e) {
            assert false;
        }
    }

    @Test
    void getOwnedTerritoryNames() {
        Country southAmerica = new Country("South America",new HashSet<>(){{add("Argentina");add("Brazil");add("Venezuela");add("Peru");}},2);
        try {
            Player mary = new Player(ArmyColor.YELLOW,"Mary");
            Player alice = new Player(ArmyColor.GREEN,"Alice");
            southAmerica.getTerritory("Argentina").addArmies(mary,1);
            southAmerica.getTerritory("Peru").addArmies(mary,2);
            assert southAmerica.getOwnedTerritoryNames(mary,1).size() == 2;
            southAmerica.getTerritory("Brazil").addArmies(mary,2);
            southAmerica.getTerritory("Venezuela").addArmies(mary,3);
            assert southAmerica.getOwnedTerritoryNames(mary,1).contains("Brazil") && southAmerica.getOwnedTerritoryNames(mary,1).size() == 4;
            assert southAmerica.getOwnedTerritoryNames(mary,2).size() == 3;
            southAmerica.getTerritory("Peru").addArmies(alice,1);
            assert southAmerica.getOwnedTerritoryNames(alice,2).size() == 0;
            assert southAmerica.getOwnedTerritoryNames(alice,1).size() == 1;
        } catch (NonExistingTerritoryException e) {
            assert false;
        }
    }
}