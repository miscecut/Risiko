package model.cards;

import model.Observable;
import model.Observer;

import java.util.*;

public class Hand implements Observable {
    private final Collection<TerritoryCard> cards = new ArrayList<>();
    private final CombinationHandler ch = new CombinationHandler();
    private final Collection<Observer> observers = new ArrayList<>();

    public void addCard(TerritoryCard card) {
        cards.add(card);
    }

    public boolean hasCombination() {
        return ch.getMaxCombinationValue(new ArrayList<>(cards)) > 0;
    }

    public Collection<TerritoryCard> removeCardsForCombination(Set<String> cardNames) {
        //TODO
        return null;
    }

    public Collection<String> getCardNames() {
        Collection<String> cardNames = new ArrayList<>();
        cards.stream().map(Card::getName).forEach(cardNames::add);
        return cardNames;
    }

    @Override
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }
}
