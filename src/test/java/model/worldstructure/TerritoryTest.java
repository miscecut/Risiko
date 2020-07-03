package model.worldstructure;

import model.gamemechanics.ArmyColor;
import model.gamemechanics.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TerritoryTest {

    @Test
    void getName() {
        assert (new Territory("China")).getName().equals("China");
    }

    @Test
    void getArmies() {
        Territory indonesia = new Territory("Indonesia");
        assert indonesia.getArmies() == 0;
        indonesia.addArmies(new Player(ArmyColor.BLUE,"Dio"),2);
        assert indonesia.getArmies() == 2;
        indonesia.removeArmies(1);
        assert indonesia.getArmies() == 1;
    }

    @Test
    void getOwner() {
        Territory siberia = new Territory("Siberia");
        assert siberia.getOwner().isEmpty();
        siberia.addArmies(new Player(ArmyColor.BLACK,"Jotaro"),1);
        assert siberia.getOwner().isPresent() && siberia.getOwner().get().getName().equals("Jotaro");
        siberia.removeArmies(1);
        assert siberia.getOwner().isEmpty();
        siberia.addArmies(new Player(ArmyColor.BLACK,"Jotaro"),1);
        siberia.addArmies(new Player(ArmyColor.RED,"Dio"),2);
        assert siberia.getOwner().isPresent() && siberia.getOwner().get().getName().equals("Dio");
    }

    @Test
    void addArmies() {
        Territory madagascar = new Territory("Madagascar");
        madagascar.addArmies(new Player(ArmyColor.GREEN,"Marra"),3);
        assert madagascar.getArmies() == 3;
        madagascar.addArmies(new Player(ArmyColor.GREEN,"Marra"),3);
        assert madagascar.getArmies() == 6;
    }

    @Test
    void removeArmies() {
        Territory china = new Territory("China");
        china.addArmies(new Player(ArmyColor.GREEN,"Marra"),3);
        china.removeArmies(2);
        assert china.getArmies() == 1;
        china.removeArmies(5);
        assert china.getArmies() == 0;
    }
}