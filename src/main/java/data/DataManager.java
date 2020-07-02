package data;

import model.worldstructure.Country;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DataManager {
    private final JSONParser parser = new JSONParser();

    public Map<String,Country> generateWorldStructure() {
        Map<String,Country> worldMap = new HashMap<>();
        try {
            JSONArray countries = (JSONArray) parser.parse(new FileReader("countriesmap.json"));
            for(Object country : countries ) {
                Set<String> territoryNames = generateTerritoryNames((JSONArray)((JSONObject)country).get("territories"));
                Country generatedCountry = new Country((String)((JSONObject)country).get("name"),territoryNames,(int)((JSONObject)country).get("bonus"));
                territoryNames.forEach(territory -> worldMap.put(territory,generatedCountry));
            }
        } catch (IOException | ParseException ignored) { }
        return worldMap;
    }

    public Map<String,Set<String>> generateBoundariesMap() {
        Map<String,Set<String>> boundariesMap = new HashMap<>();
        try {
            JSONArray territories = (JSONArray) parser.parse(new FileReader("boundaries.json"));
            for(Object territoryInfo : territories)
                boundariesMap.put((String)((JSONObject)territoryInfo).get("name"), generateTerritoryNames((JSONArray)((JSONObject)territoryInfo).get("territories")));
        } catch (IOException | ParseException ignored) { }
        return boundariesMap;
    }

    private Set<String> generateTerritoryNames(JSONArray territories) {
        Set<String> territoryNames = new HashSet<>();
        for(Object territory : territories)
            territoryNames.add((String)territory);
        return territoryNames;
    }
}
