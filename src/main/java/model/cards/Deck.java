package model.cards;

import model.utils.exceptions.EmptyDeckException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Deck<C extends Card> {
    private final List<C> cards = new ArrayList<>();

    public void addCard(C card) {
        cards.add(card);
    }

    public void addCards(Collection<C> newCards) {
        cards.addAll(newCards);
    }

    public C pickCard() throws EmptyDeckException {
        try {
            return cards.remove(0);
        } catch (IndexOutOfBoundsException e) {
            throw new EmptyDeckException();
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }
}
