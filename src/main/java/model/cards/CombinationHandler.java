package model.cards;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static model.cards.CardSymbol.*;

public class CombinationHandler {

    public int getMaxCombinationValue(Collection<TerritoryCard> territoryCards) {
        if(territoryCards.size() < 3)
            return 0;
        Map<CardSymbol,Integer> symbolMap = new HashMap<>();
        Arrays.asList(CardSymbol.values()).forEach(symbol -> symbolMap.put(symbol, 0));
        territoryCards.forEach(card -> symbolMap.replace(card.getSymbol(),symbolMap.get(card.getSymbol()) + 1));
        if(symbolMap.get(JOLLY) >= 1) {
            if(symbolMap.get(KNIGHT) >= 2 || symbolMap.get(BISHOP) >= 2 || symbolMap.get(CANNON) >= 2)
                return 12;
        }
        if(symbolMap.get(KNIGHT) >= 1 && symbolMap.get(BISHOP) >= 1 && symbolMap.get(CANNON) >= 1)
            return 10;
        if(symbolMap.get(KNIGHT) >= 3)
            return 8;
        if(symbolMap.get(BISHOP) >= 3)
            return 6;
        if(symbolMap.get(CANNON) >= 3)
            return 4;
        return 0;
    }
}
