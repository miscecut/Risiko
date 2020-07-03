package model.cards.winconditionstrategies;

import model.gamemechanics.Player;
import model.worldstructure.World;

import java.util.Set;

public class ConqueredCountriesCondition implements WinCondition {
    private final Set<String> obligatoryCountries;
    private final int nonObligatoryCountries;

    public ConqueredCountriesCondition(Set<String> obligatoryCountries) {
        this.obligatoryCountries = obligatoryCountries;
        nonObligatoryCountries = 0;
    }

    public ConqueredCountriesCondition(Set<String> obligatoryCountries, int nonObligatoryCountries) {
        this.obligatoryCountries = obligatoryCountries;
        this.nonObligatoryCountries = nonObligatoryCountries;
    }

    @Override
    public boolean hasPlayerWon(Player cardOwner, World world) {
        Set<String> ownedCountries = world.getOwnedCountryNames(cardOwner);
        return ownedCountries.containsAll(obligatoryCountries) && ownedCountries.size() >= obligatoryCountries.size() + nonObligatoryCountries;
    }
}
