package data;

import model.worldstructure.Country;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataManager {
    private final JSONParser parser = new JSONParser();

    public Map<String,Country> generateWorldStructure() {
        Map<String,Country> worldMap = new HashMap<>();
        try {
            JSONArray countries = (JSONArray) parser.parse(new FileReader("countriesmap.json"));
            for(Object country : countries ) {
                String countryName = (String)((JSONObject)country).get("name");
                int bonus = (int)((JSONObject)country).get("bonus");
                Set<String> territoryNames = generateTerritoryNames((JSONArray)((JSONObject)country).get("territories"));
                Country generatedCountry = new Country(countryName,territoryNames,bonus);
                territoryNames.forEach(territory -> worldMap.put(territory,generatedCountry));
            }
        } catch (IOException | ParseException ignored) { }
        return worldMap;
    }

    private Set<String> generateTerritoryNames(JSONArray territories) {
        //TODO
        return null;
    }
}
