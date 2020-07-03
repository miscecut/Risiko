package model.gamemechanics;

import model.cards.Hand;
import model.cards.ObjectiveCard;
import model.cards.TerritoryCard;
import model.cards.winconditionstrategies.ConqueredCountriesCondition;
import model.cards.winconditionstrategies.WinCondition;

import java.util.HashSet;
import java.util.Objects;

public class Player {
    private final ArmyColor armyColor;
    private final String name;
    private final Hand hand = new Hand();
    private ObjectiveCard objectiveCard;

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

    public void giveCard(TerritoryCard card) {
        hand.addCard(card);
    }

    public void giveCard(ObjectiveCard card) {
        objectiveCard = card;
    }

    public Hand getHand() {
        return hand;
    }

    public WinCondition getWinCondition() {
        try {
            return objectiveCard.getWinCondition();
        } catch (NullPointerException e) {
            return new ConqueredCountriesCondition(new HashSet<>(),6);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return armyColor == player.armyColor && Objects.equals(name, player.name);
    }
}
