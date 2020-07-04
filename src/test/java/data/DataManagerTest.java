package data;

import model.worldstructure.Country;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DataManagerTest {

    @Test
    void generateWorldStructure() {
        DataManager dm = new DataManager();
        Map<String, Country> territoryCountryMap = dm.generateWorldStructure();
        assert territoryCountryMap.containsKey("Alaska");
        assert territoryCountryMap.get("Southern Europe").getName().equals("Europe");
        assert territoryCountryMap.get("Venezuela").getName().equals("Southern America");
    }


}