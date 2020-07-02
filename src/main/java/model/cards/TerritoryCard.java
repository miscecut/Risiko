package model.cards;

import java.util.Optional;

public class TerritoryCard extends Card {
    private final String territory;
    private final CardSymbol symbol;

    public TerritoryCard(String cardName, String territory, CardSymbol symbol) {
        super(cardName);
        this.territory = territory;
        this.symbol = symbol;
    }

    public TerritoryCard(String cardName, CardSymbol symbol) {
        super(cardName);
        this.territory = null;
        this.symbol = symbol;
    }

    public Optional<String> getTerritory() {
        return Optional.ofNullable(territory);
    }

    public CardSymbol getSymbol() {
        return symbol;
    }
}
