package model.gamemechanics;

import data.DataManager;
import model.Player;
import model.cards.Deck;
import model.cards.ObjectiveCard;
import model.cards.TerritoryCard;
import model.utils.exceptions.EmptyDeckException;
import model.utils.exceptions.NonExistingTerritoryException;
import model.worldstructure.World;

import java.util.Collection;
import java.util.List;

public class GameSession {
    private final List<Player> players;
    private final World world = new World();
    private final Deck<ObjectiveCard> objectiveCardDeck;
    private final Deck<TerritoryCard> territoryCardDeck;

    public GameSession(List<Player> players) {
        this.players = players;
        DataManager dm = new DataManager();
        objectiveCardDeck = dm.generateObjectiveCardDeck();
        territoryCardDeck = dm.generateTerritoryCardDeck();
    }

    public void startGame() {
        giveObjectiveCards();
        giveTerritoryCards();
        fillWorldWithSingleArmies();
        putDownInitialArmies()
    }

    private void giveObjectiveCards() {
        players.forEach(player -> {
            try {
                player.giveCard(objectiveCardDeck.pickCard());
            } catch (EmptyDeckException ignored) { }
        });
    }

    private void giveTerritoryCards() {
        int index = 0;
        int playersSize = players.size();
        while(true) {
            try {
                players.get(index % playersSize).giveCard(territoryCardDeck.pickCard());
                index++;
            } catch (EmptyDeckException e) {
                break;
            }
        }
    }

    private void fillWorldWithSingleArmies() {
        for(Player player : players) {
            Collection<TerritoryCard> cards = player.getHand().removeAllTerritoryCards();
            cards.stream().filter(card -> card.getTerritory().isPresent()).map(card -> card.getTerritory().get()).forEach(territory -> {
                try {
                    world.getTerritory(territory).addArmies(player,1);
                } catch (NonExistingTerritoryException ignored) { }
            });
            territoryCardDeck.addCards(cards);
        }
    }

    private void putDownInitialArmies() {

    }
}
