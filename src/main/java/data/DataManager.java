package data;

import model.cards.CardSymbol;
import model.cards.Deck;
import model.cards.ObjectiveCard;
import model.cards.TerritoryCard;
import model.cards.winconditionstrategies.ConqueredCountriesCondition;
import model.cards.winconditionstrategies.OccupiedTerritoriesCondition;
import model.worldstructure.Country;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataManager {
    private final JSONParser parser = new JSONParser();

    public Map<String,Country> generateWorldStructure() {
        Map<String,Country> worldMap = new HashMap<>();
        try {
            JSONArray countries = (JSONArray) parser.parse(new FileReader(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("jsonfiles/countriesmap.json")).getFile())));
            for(Object country : countries ) {
                Set<String> territoryNames = generateTerritoryNames((JSONArray)((JSONObject)country).get("territories"));
                Country generatedCountry = new Country((String)((JSONObject)country).get("name"),territoryNames,(int)((long)((JSONObject)country).get("bonus")));
                territoryNames.forEach(territory -> worldMap.put(territory,generatedCountry));
            }
        } catch (IOException | ParseException ignored) { }
        return worldMap;
    }

    public Map<String,Set<String>> generateBoundariesMap() {
        Map<String,Set<String>> boundariesMap = new HashMap<>();
        try {
            JSONArray territories = (JSONArray) parser.parse(new FileReader(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("jsonfiles/boundaries.json")).getFile())));
            for(Object territoryInfo : territories)
                boundariesMap.put((String)((JSONObject)territoryInfo).get("name"), generateTerritoryNames((JSONArray)((JSONObject)territoryInfo).get("boundaries")));
        } catch (IOException | ParseException ignored) { }
        return boundariesMap;
    }

    public Deck<TerritoryCard> generateTerritoryCardDeck() {
        Deck<TerritoryCard> deck = new Deck<>();
        try {
            JSONArray cards = (JSONArray) parser.parse(new FileReader(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("jsonfiles/territorycards.json")).getFile())));
            for(Object card : cards)
                deck.addCard(new TerritoryCard((((JSONObject)card).get("territory")) + " card",(String)((JSONObject)card).get("territory"), CardSymbol.valueOf((String)((JSONObject)card).get("symbol"))));
        } catch (IOException | ParseException ignored) { }
        deck.shuffle();
        return deck;
    }

    public Deck<ObjectiveCard> generateObjectiveCardDeck() {
        Deck<ObjectiveCard> deck = new Deck<>();
        deck.addCard(new ObjectiveCard("oc01",new OccupiedTerritoriesCondition(18,2)));
        deck.addCard(new ObjectiveCard("oc02",new OccupiedTerritoriesCondition(24)));
        deck.addCard(new ObjectiveCard("oc03",new ConqueredCountriesCondition(new HashSet<>(){{add("Europe");add("Oceania");}},1)));
        deck.addCard(new ObjectiveCard("oc04",new ConqueredCountriesCondition(new HashSet<>(){{add("Europe");add("Southern America");}},1)));
        deck.addCard(new ObjectiveCard("oc05",new ConqueredCountriesCondition(new HashSet<>(){{add("Africa");add("Asia");}})));
        deck.addCard(new ObjectiveCard("oc06",new ConqueredCountriesCondition(new HashSet<>(){{add("Africa");add("Northern America");}})));
        deck.addCard(new ObjectiveCard("oc07",new ConqueredCountriesCondition(new HashSet<>(){{add("Asia");add("Southern America");}})));
        deck.addCard(new ObjectiveCard("oc08",new ConqueredCountriesCondition(new HashSet<>(){{add("Northern America");add("Oceania");}})));
        deck.shuffle();
        return deck;
    }

    private Set<String> generateTerritoryNames(JSONArray territories) {
        Set<String> territoryNames = new HashSet<>();
        for(Object territory : territories)
            territoryNames.add((String)territory);
        return territoryNames;
    }
}
