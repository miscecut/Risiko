package model.utils;

import java.util.ArrayList;
import java.util.List;

public class DiceSet {
    private final Die die = new Die();

    public List<Integer> rollDiceSequence(int dice) {
        List<Integer> sequence = new ArrayList<>(dice);
        for(int singleThrow = 0; singleThrow < dice; singleThrow++)
            sequence.add(die.roll());
        sequence.sort(Integer::compareTo);
        return sequence;
    }

    public int rollSingleDie() {
        return die.roll();
    }
}
