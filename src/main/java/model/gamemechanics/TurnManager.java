package model.gamemechanics;

import controller.IOController;
import model.Player;
import model.cards.CombinationHandler;
import model.cards.Deck;
import model.cards.TerritoryCard;
import model.utils.exceptions.NonExistingTerritoryException;
import model.utils.exceptions.NotACombinationException;
import model.worldstructure.Territory;
import model.worldstructure.World;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public class TurnManager {
    private final World world;
    private final Deck<TerritoryCard> territoryCardDeck;
    private final IOController ioController;

    public TurnManager(World world, Deck<TerritoryCard> territoryCardDeck, IOController ioController) {
        this.world = world;
        this.territoryCardDeck = territoryCardDeck;
        this.ioController = ioController;
    }

    public boolean startTurn(Player player) {
        putReinforces(player);
        return false;
    }

    private void putReinforces(Player player) {
        int basicReinforces = world.getArmyBonus(player);
        if(player.getHand().hasCombination() && ioController.askYesOrNo(player,"Do you want to use a combination in your hand?"))
            basicReinforces += useCombination(player);
        Set<String> ownedTerritories = world.getOwnedTerritoryNames(player);
        if(ownedTerritories.size() > 0) {
            for(int i = 0; i < basicReinforces; i++) {
                try {
                    world.getTerritory(ioController.askForATerritory(player,ownedTerritories,(basicReinforces - i) + " armies left to put down, where do you want to put the next army?")).addArmies(player,1);
                } catch (NonExistingTerritoryException e) {
                    i--;
                }
            }
        }
    }

    private int useCombination(Player player) {
        Collection<TerritoryCard> cardsToDiscard;
        while(true) {
            try {
                cardsToDiscard = player.getHand().removeCardsForCombination(ioController.askForThreeCards(player,player.getHand().getCardNames()));
                break;
            } catch (NotACombinationException ignored) { }
        }
        territoryCardDeck.addCards(cardsToDiscard);
        return (new CombinationHandler()).getMaxCombinationValue(cardsToDiscard) + getOwnedTerritoryBonus(player,cardsToDiscard);
    }

    private int getOwnedTerritoryBonus(Player player, Collection<TerritoryCard> discardedCards) {
        Set<String> ownedTerritories = world.getOwnedTerritoryNames(player);
        int bonus = 0;
        for(TerritoryCard card : discardedCards) {
            if(card.getTerritory().isPresent() && ownedTerritories.contains(card.getTerritory().get()))
                bonus += 2;
        }
        return bonus;
    }
}
