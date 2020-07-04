package model.worldstructure;

import jdk.swing.interop.SwingInterOpUtils;
import model.gamemechanics.ArmyColor;
import model.gamemechanics.Player;
import model.utils.exceptions.NonExistingTerritoryException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorldTest {

    @Test
    void getTerritory() {
        World earth = new World();
        try {
            assert earth.getTerritory("Nowhere").getArmies() == 1;
        } catch (NonExistingTerritoryException e) {
            assert true;
        }
        try {
            assert earth.getTerritory("Great Britain").getName().equals("Great Britain");
        } catch (NonExistingTerritoryException e) {
            assert false;
        }
    }

    @Test
    void getArmyBonus() {
        World earth = new World();
        Player player = new Player(ArmyColor.RED,"Jonas");
        try {
            earth.getTerritory("Venezuela").addArmies(player,2);
            earth.getTerritory("Argentina").addArmies(player,2);
            assert earth.getArmyBonus(player) == 0;
            earth.getTerritory("Peru").addArmies(player,1);
            earth.getTerritory("Alaska").addArmies(player,2);
            assert earth.getArmyBonus(player) == 1;
            earth.getTerritory("Congo").addArmies(player,3);
            earth.getTerritory("Brazil").addArmies(player,1);
            assert earth.getArmyBonus(player) == 4;
        } catch (NonExistingTerritoryException e) {
            assert false;
        }
    }

    @Test
    void getOwnedCountryNames() {
    }

    @Test
    void fromWhereCanPlayerMove() {
    }

    @Test
    void whereCanPlayerMove() {
    }

    @Test
    void fromWhereCanPlayerAttack() {
    }

    @Test
    void whereCanPlayerAttack() {
    }

    @Test
    void getOwnedTerritoryNames() {
        World earth = new World();
        Player martha = new Player(ArmyColor.RED,"Martha");
        Player jonas = new Player(ArmyColor.YELLOW,"Jonas");
        try {
            earth.getTerritory("Alaska").addArmies(martha,1);
            earth.getTerritory("Siberia").addArmies(martha,1);
            earth.getTerritory("Mongolia").addArmies(martha,2);
            assert earth.getOwnedTerritoryNames(martha).size() == 3;
            assert earth.getOwnedTerritoryNames(martha,2).contains("Mongolia");
            earth.getTerritory("Argentina").addArmies(jonas,1);
            assert earth.getOwnedTerritoryNames(jonas,3).isEmpty();
            assert earth.getOwnedTerritoryNames(jonas).contains("Argentina");
        } catch (NonExistingTerritoryException e) {
            assert false;
        }
    }
}