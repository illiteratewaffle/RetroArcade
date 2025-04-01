package server.management;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JsonConverter {
    /**
     * Converts a given HashMap into a json string
     * @param map the given HashMap that you want to convert to json
     * @return the json string
     */
    public static String toJson(HashMap<String,Object> map) {
        // Create a new StringBuilder
        StringBuilder builder = new StringBuilder();
        // Start with a "{"
        builder.append("{");

        // Loop through the list and convert to json
        int size = map.size();
        int i = 0;
        for (HashMap.Entry<String, Object> entry : map.entrySet()) {
            builder.append("\"").append(entry.getKey()).append("\":");
            builder.append(serializeObject(entry.getValue()));
            // If there is another item, add a comma
            if (i < size - 1) {
                builder.append(",");
            }
            i++;
        }
        // Return the json string
        return builder.append("}").toString();
    }

    /**
     * Converts a given object to the corresponding json string
     * @param object the given Object that you want to convert to json
     * @return the json string
     */
    private static String serializeObject(Object object) {
        if (object == null) {
            return "null";
        } else if (object instanceof String) {
            return "\"" + object.toString() + "\"";
        } else if (object instanceof Number || object instanceof Boolean) {
            return object.toString();
        } else if (object instanceof Map) {
            // Intellij is mad at me :(
            return toJson((HashMap<String, Object>) object);
        } else if (object instanceof List) {
            // Intellij is mad at me :(
            return serializeList((List<Object>) object);
        } else {
            // A fallback prolly isn't a good idea
            throw new RuntimeException("Unsupported type: " + object.getClass());
        }
    }

    /**
     * Converts a given list to the corresponding json string
     * @param list the given List that you want to convert to json
     * @return the json string
     */
    private static String serializeList(List<Object> list) {
        // Create a new StringBuilder
        StringBuilder builder = new StringBuilder();
        // Start the list with a "["
        builder.append("[");

        // Loop through the list and convert to json
        int size = list.size();
        for (int i = 0; i < size; i++) {
            // Get the item
            Object item = list.get(i);
            builder.append(serializeObject(item));
            // If there is another item, add a comma
            if (i < size - 1) {
                builder.append(",");
            }
        }
        // Return the json string
        return builder.append("]").toString();
    }

    public static void main(String[] args) {
        HashMap<String,Object> map = new HashMap<>();
//        map.put("name","John");
//        map.put("age",23);
//        map.put("drunk",true);
//        // test the inner hash map
//        Map<String, Object> innerMap = new HashMap<>();
//        innerMap.put("inner hash", "value of inner hash!");
//        map.put("nested", innerMap);
        // test a list
        List<Object> list = new ArrayList<>();
        list.add("first item");
        list.add("second item");
        List<Object> innerList = new ArrayList<>();
        innerList.add(list);
        innerList.add(list);
        map.put("list", innerList);
        System.out.println(toJson(map));
    }

    /**
     * Converts a given json string to a HashMap
     * @param json
     * @return
     */
    public static Map<String, Object> fromJson(String json) {
        return null;
    }

    private static HashMap<String, Object> parseObject() {
        return null;
    }

    private static List<Object> parseList(String json) {
        return null;
    }
}
