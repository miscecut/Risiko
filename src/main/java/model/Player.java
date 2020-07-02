package model;

import model.cards.Hand;

import java.util.Objects;

public class Player {
    private final ArmyColor armyColor;
    private final String name;
    private final Hand hand = new Hand();

    public Player(ArmyColor armyColor, String name) {
        this.armyColor = armyColor;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArmyColor getArmyColor() {
        return armyColor;
    }

    public Hand getHand() {
        return hand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return armyColor == player.armyColor && Objects.equals(name, player.name);
    }
}
