package server.management;

import com.fasterxml.jackson.core.JsonParseException;

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
            return "\"" + object.toString().replace("\"","\\\"").replace("\\","\\\\")
                    .replace("\n","\\n").replace("\t", "\\t") + "\"";
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
        map.put("name","John\\n");
//        map.put("age",23);
//        map.put("drunk",true);
//        // test the inner hash map
//        Map<String, Object> innerMap = new HashMap<>();
//        innerMap.put("inner hash", "value of inner hash!");
//        map.put("nested", innerMap);
        // test a list
//        List<Object> list = new ArrayList<>();
//        list.add("first item");
//        list.add("second item");
//        List<Object> innerList = new ArrayList<>();
//        innerList.add(list);
//        innerList.add(list);
//        map.put("list", innerList);
//        System.out.println(toJson(map));
        // System.out.println(toJson(map));
        // System.out.println(parseString("hi\"Hello World!hi\\\\\"buttwhole hehe", new IntWrapper(2)));
    }

    /**
     * Converts a given json string to a HashMap
     * @param json the json string
     * @return the converted HashMap
     */
    public static Map<String, Object> fromJson(String json) {
        // Create a IntWrapper that will be used to track the index.
        IntWrapper index = new IntWrapper(0);
        String jsonStripped = json.strip();
        // If the first character of the json string is not a curley bracket, throw an exception
        if (jsonStripped.charAt(index.value) != '{')
            // TODO: create my own exception type?
            throw new RuntimeException("Json string must start with a curly bracket.");
        return parseMap(jsonStripped, index);
    }

    /**
     * Converts a json string to the corresponding Object
     * @param json the json string
     * @param index the current index of the string
     * @return the corresponding Object
     */
    private static Object parseObject(String json, IntWrapper index) {
        char c = json.charAt(index.value);
        if (c == '{') {
            return parseMap(json, index);
        } else if (c == '[') {
            return parseList(json, index);
        } else if (c == 't' || c == 'f' || c == 'n') {
            return parseBoolean(json, index);
        } else if (Character.isDigit(c)) {
            return parseNumber(json, index);
        }
        // If none match, throw an exception
        throw new RuntimeException("Unsupported character: " + c);
    }

    private static HashMap<String,Object> parseMap(String json, IntWrapper index) {
        index.value++;
        HashMap<String,Object> map = new HashMap<>();
        // Loop through the HashMap
        while (json.charAt(index.value) != '}') {
            // parseString()
        }
        return null;
    }

    private static List<Object> parseList(String json, IntWrapper index) {
        return null;
    }

    private static String parseString(String json, IntWrapper index) {
        if (json.charAt(index.value) != '"') {
            throw new RuntimeException("Expected opening quote for the string: " + json + ":" + String.valueOf(index.value));
        }
        // Skip the opening quote
        index.value++;
        // Create a new StringBuilder
        StringBuilder builder = new StringBuilder();
        // Loop through the string
        while (index.value < json.length()) {
            char c = json.charAt(index.value);
            if (c == '"') {
                // Check how many backslashes are directly before this quote
                int backslashCount = 0;
                int j = index.value - 1;
                while (j >= 0 && json.charAt(j) == '\\') {
                    backslashCount++;
                    j--;
                }
                // If there is an even number of backslashes, then the quote is final
                if (backslashCount % 2 == 0) {
                    // Move past the closing quote
                    index.value++;
                    // Return completed string
                    return builder.toString();
                }
            } else if (c == '\\') {
                // Move to the next escape sequence character
                index.value++;
                if (index.value >= json.length()) {
                    throw new RuntimeException("Unterminated escape sequence: " + json + ":" + String.valueOf(index.value));
                }
                // Get the next escape sequence character that we moved too
                char escapeChar = json.charAt(index.value);
                switch (escapeChar) {
                    case '"':
                        builder.append('"');
                        break;
                    case '\\':
                        builder.append('\\');
                        break;
                    case 'n':
                        builder.append('\n');
                        break;
                    case 't':
                        builder.append('\t');
                        break;
                    default:
                        throw new RuntimeException("Unsupported escape sequence character: \\" + escapeChar);
                }
            }
            // Append the character and move to the next one
            builder.append(c);
            index.value++;
        }
        throw new RuntimeException("Expected closing quote for the string: " + json + ":" + String.valueOf(index.value));
    }

    private static Boolean parseBoolean(String json, IntWrapper index) {
        return null;
    }

    private static Number parseNumber(String json, IntWrapper index) {
        return null;
    }

    /**
     * This class behaves like a pointer to an int. It allows the ability to mutate the value across recursive calls.
     */
    private static class IntWrapper {
        public int value;
        public IntWrapper(int value) {
            this.value = value;
        }
    }
}
