package model.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

class DiceSetTest {

    @Test
    void rollDiceSequence() {
        List<Integer> rollSequence = (new DiceSet()).rollDiceSequence(3);
        assert rollSequence.get(0) >= rollSequence.get(1) && rollSequence.get(1) >= rollSequence.get(2);
    }

    @Test
    void rollSingleDie() {
        assert (new Die()).roll() > 0 && (new Die()).roll() < 7;
    }
}