package networking_main.management;

import org.junit.jupiter.api.Test;
import server.management.JsonConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static server.management.JsonConverter.fromJson;

class JsonConverterTest {

    @Test
    void jsonTest() {
        HashMap<String, Object> map = new HashMap<>();
        // Test strings
        map.put("name", "John");
        // Test integers
        map.put("age", 25);
        // Test string with escape sequence characters
        map.put("birthday", "birthday:\n1990\t\\t01\t\\02");
        // Testing boolean true
        map.put("male", true);
        // Testing boolean false
        map.put("female", false);
        // Testing null
        map.put("happy", null);
        // Testing doubles
        map.put("money", 5023.42);
        // Testing 2d arrays / arrays
        ArrayList<String> innerList = new ArrayList<>();
        innerList.add("John");
        innerList.add("Stacey");
        ArrayList<ArrayList<String>> outerList = new ArrayList<>();
        outerList.add(innerList);
        outerList.add(innerList);
        map.put("list", outerList);
        // Testing hashmaps
        HashMap<String, Object> innerMap = new HashMap<>();
        innerMap.put("Taylor Swift", "Our Song");
        map.put("hashmap", innerMap);
        String json = JsonConverter.toJson(map);
        // System.out.println(json);
        Map<String, Object> convertedJson = fromJson(json);
        assertEquals(map, convertedJson);
    }
}