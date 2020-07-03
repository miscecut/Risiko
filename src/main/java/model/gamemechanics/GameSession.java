package model.gamemechanics;

import controller.IOController;
import data.DataManager;
import model.cards.CardSymbol;
import model.cards.Deck;
import model.cards.ObjectiveCard;
import model.cards.TerritoryCard;
import model.utils.exceptions.EmptyDeckException;
import model.utils.exceptions.NonExistingTerritoryException;
import model.worldstructure.World;

import java.util.*;

public class GameSession {
    private final List<Player> players;
    private final World world = new World();
    private final Deck<ObjectiveCard> objectiveCardDeck;
    private final Deck<TerritoryCard> territoryCardDeck;
    private final IOController ioController;

    public GameSession(List<Player> players, IOController ioController) {
        this.players = players;
        this.ioController = ioController;
        DataManager dm = new DataManager();
        objectiveCardDeck = dm.generateObjectiveCardDeck();
        territoryCardDeck = dm.generateTerritoryCardDeck();
    }

    public void startGame() {
        giveObjectiveCards();
        giveTerritoryCards();
        fillWorldWithSingleArmies();
        generateJollies();
        putDownInitialArmies();
        startRegularGame();
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

    private void generateJollies() {
        territoryCardDeck.addCard(new TerritoryCard("jolly1", CardSymbol.JOLLY));
        territoryCardDeck.addCard(new TerritoryCard("jolly2",CardSymbol.JOLLY));
        territoryCardDeck.shuffle();
    }

    private void putDownInitialArmies() {
        Map<Player, Set<String>> ownedTerritoriesPerPlayer = new HashMap<>();
        Map<Player, Integer> remainingArmiesPerPlayer = new HashMap<>();
        players.forEach(player -> ownedTerritoriesPerPlayer.put(player,world.getOwnedTerritoryNames(player)));
        players.forEach(player -> remainingArmiesPerPlayer.put(player,50 - players.size() * 5 - ownedTerritoriesPerPlayer.get(player).size()));
        while(true) {
            for(Player player : players) {
                int armiesToPutDown = Math.max(3,remainingArmiesPerPlayer.get(player));
                remainingArmiesPerPlayer.replace(player,remainingArmiesPerPlayer.get(player) - armiesToPutDown);
                for(int i = 0; i < armiesToPutDown; i++){
                    try {
                        world.getTerritory(ioController.askForATerritory(player,ownedTerritoriesPerPlayer.get(player),"Select a territory you own"));
                    } catch (NonExistingTerritoryException e) {
                        i--;
                    }
                }
            }
            boolean breakCondition = true;
            for(Integer remainingArmy : remainingArmiesPerPlayer.values()) {
                if (remainingArmy != 0) {
                    breakCondition = false;
                    break;
                }
            }
            if(breakCondition)
                break;
        }
    }

    private void startRegularGame() {
        TurnManager tm = new TurnManager(world,territoryCardDeck,ioController);
        int index = 0;
        int playersInGame = players.size();
        while(true) {
            if(tm.startTurn(players.get(index % playersInGame)))
                break;
        }
    }
}
