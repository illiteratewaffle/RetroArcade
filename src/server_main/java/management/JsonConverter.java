package management;

import java.util.*;

public class JsonConverter {
    /**
     * Converts a given HashMap into a json string
     * @param map the given HashMap that you want to convert to json
     * @return the json string
     */
    public static String toJson(Map<String,Object> map) throws IllegalArgumentException {
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
    private static String serializeObject(Object object) throws IllegalArgumentException {
        if (object == null) {
            return "null";
        } else if (object instanceof String) {
            return "\"" + object.toString().replace("\\","\\\\").replace("\"","\\\"")
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
            throw new IllegalArgumentException("Unsupported type: " + object.getClass());
        }
    }

    /**
     * Converts a given list to the corresponding json string
     * @param list the given List that you want to convert to json
     * @return the json string
     */
    private static String serializeList(List<Object> list) throws IllegalArgumentException {
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

    /**
     * Converts a given json string to a HashMap
     * @param json the json string
     * @return the converted HashMap
     */
    public static Map<String, Object> fromJson(String json) throws IllegalArgumentException {
        // Create a IntWrapper that will be used to track the index.
        IntWrapper index = new IntWrapper(0);
        String jsonStripped = json.strip();
        // If the first character of the json string is not a curley bracket, throw an exception
        if (jsonStripped.charAt(index.value) != '{')
            // TODO: create my own exception type?
            throw new IllegalArgumentException("Json string must start with a curly bracket.");
        return parseMap(jsonStripped, index);
    }

    /**
     * Converts a json string to the corresponding Object
     * @param json the json string
     * @param index the current index of the string
     * @return the corresponding Object
     */
    private static Object parseObject(String json, IntWrapper index) throws IllegalArgumentException {
        char c = json.charAt(index.value);
        if (c == '{') {
            return parseMap(json, index);
        } else if (c == '[') {
            return parseList(json, index);
        } else if (c == '"') {
            return parseString(json, index);
        } else if (c == 't' || c == 'f' || c == 'n') {
            return parseBoolean(json, index);
        } else if (Character.isDigit(c)) {
            return parseNumber(json, index);
        }
        // If none match, throw an exception
        throw new IllegalArgumentException("Unsupported character " + c + " : " + json.substring(0, index.value) + "\t<-HERE\t" + json.substring(index.value));
    }

    /**
     * Converts a json string to a HashMap
     * @param json the json string
     * @param index the current index of the string
     * @return the corresponding HashMap
     */
    private static Map<String,Object> parseMap(String json, IntWrapper index) throws IllegalArgumentException {
        if (json.charAt(index.value) != '{') {
            throw new IllegalArgumentException("Expected opening curly bracket for the HashMap: " + json.substring(0, index.value) + "\t<-HERE\t" + json.substring(index.value));
        }
        // Skip the opening curly bracket
        index.value++;
        // Create a new HashMap
        HashMap<String,Object> map = new HashMap<>();
        // Loop through the HashMap
        // TODO: This has a possibility of crashing if index.value extends the size of the json string
        while (index.value < json.length()) {
            // TODO: skipWhitespace()

            // Handle empty hashmaps
            if (json.charAt(index.value) == '}') {
                index.value++;
                return map;
            }

            // First, get the key
            String key = parseString(json, index);

            // TODO: skipWhitespace()

            if (index.value >= json.length() || json.charAt(index.value) != ':') {
                throw new IllegalArgumentException("Key missing corresponding value: " + json.substring(0, index.value) + "\t<-HERE\t" + json.substring(index.value));
            }
            // Move past the colon
            index.value++;

            // TODO: skipWhitespace()

            // Get the value and add to the HashMap
            Object value = parseObject(json, index);
            map.put(key, value);

            // TODO: skipWhitespace()

            if (json.charAt(index.value) == ',') {
                index.value++;
                // TODO: skipWhitespace()
            } else if (json.charAt(index.value) == '}') {
                index.value++;
                return map;
            } else {
                throw new IllegalArgumentException("Expected ',' or '}' after hashmap item: " + json.substring(0, index.value) + "\t<-HERE\t" + json.substring(index.value));
            }
        }
        return map;
    }

    /**
     * Converts a json string to a List
     * @param json the json string
     * @param index the current index of the string
     * @return the corresponding List
     */
    private static List<Object> parseList(String json, IntWrapper index) throws IllegalArgumentException {
        if (json.charAt(index.value) != '[') {
            throw new IllegalArgumentException("Expected opening square bracket for the List: " + json.substring(0, index.value) + "\t<-HERE\t" + json.substring(index.value));
        }
        // Skip the opening square bracket
        index.value++;
        // Create a new List
        List<Object> list = new ArrayList<>();
        // Loop through the List
        while (index.value < json.length()) {
            // TODO: skipWhitespace() method that skips the whitespace?

            // Handle empty array
            if (json.charAt(index.value) == ']') {
                // Skip the closing square bracket
                index.value++;
                return list;
            }

            // Get the object
            Object item = parseObject(json, index);
            list.add(item);

            // TODO: skipWhitespace()!

            if (json.charAt(index.value) == ',') {
                // If there is a comma, skip the comma
                index.value++;
                // TODO: skipWhitespace()!
            } else if (json.charAt(index.value) == ']') {
                // If there is the closing square bracket, skip the bracket and return the list
                index.value++;
                return list;
            } else {
                // If there is any other character following the object, give an error
                throw new IllegalArgumentException("Expected ',' or ']' after list item: " + json.substring(0, index.value) + "\t<-HERE\t" + json.substring(index.value));
            }
        }
        throw new IllegalArgumentException("Expected closing square bracket for the List: " + json.substring(0, index.value) + "\t<-HERE\t" + json.substring(index.value));
    }

    /**
     * Converts a json string to a String
     * @param json the json string
     * @param index the current index of the string
     * @return the corresponding String
     */
    private static String parseString(String json, IntWrapper index) throws IllegalArgumentException {
        if (json.charAt(index.value) != '"') {
            throw new IllegalArgumentException("Expected opening quote for the string: " + json.substring(0, index.value) + "\t<-HERE\t" + json.substring(index.value));
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
                    throw new IllegalArgumentException("Unterminated escape sequence: " + json.substring(0, index.value) + "\t<-HERE\t" + json.substring(index.value));
                }
                // Get the next escape sequence character that we moved too
                char escapeChar = json.charAt(index.value);
                switch (escapeChar) {
                    case '\\': builder.append('\\'); break;
                    case '"': builder.append('"'); break;
                    case 'n': builder.append('\n'); break;
                    case 't': builder.append('\t'); break;
                    default: throw new IllegalArgumentException("Unsupported escape sequence character: \\" + escapeChar + ": " + json.substring(0, index.value) + "\t<-HERE\t" + json.substring(index.value));
                }
                index.value++;
                continue;
            }
            // Append the character and move to the next one
            builder.append(c);
            index.value++;
        }
        throw new IllegalArgumentException("Expected closing quote for the string: " + json.substring(0, index.value) + "\t<-HERE\t" + json.substring(index.value));
    }

    /**
     * Converts a json string to a Boolean or null value
     * @param json the json string
     * @param index the current index of the string
     * @return the corresponding Boolean or null value
     */
    private static Boolean parseBoolean(String json, IntWrapper index) throws IllegalArgumentException {
        if (json.startsWith("true", index.value)) {
            index.value += 4;
            return true;
        } else if (json.startsWith("false", index.value)) {
            index.value += 5;
            return false;
        } else if (json.startsWith("null", index.value)) {
            index.value += 4;
            return null;
        } else {
            throw new IllegalArgumentException("Expected boolean or null: " + json.substring(0, index.value) + "\t<-HERE\t" + json.substring(index.value));
        }
    }

    /**
     * Converts a json string to a Number (Integer or Double)
     * @param json the json string
     * @param index the current index of the string
     * @return the corresponding Integer or Double
     */
    private static Number parseNumber(String json, IntWrapper index) throws IllegalArgumentException {
        int start = index.value;
        boolean isFloat = false;

        while (index.value < json.length()) {
            char c = json.charAt(index.value);
            if (c == '.') {
                if (isFloat) {
                    throw new IllegalArgumentException("Number cannot contain multiple decimal points: " + json.substring(0, index.value - 1) + "\t<-HERE\t" + json.substring(index.value - 1));
                }
                isFloat = true;
                index.value++;
            } else if (Character.isDigit(c) || c == '-' || c == '+') {
                index.value++;
            } else {
                // Once it is no longer a number, break out of the while loop
                break;
            }
        }
        // Now, take the substring
        String number = json.substring(start, index.value);

        try {
            if (isFloat) {
                return Double.parseDouble(number);
            } else {
                return Integer.parseInt(number);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Number format error: " + json.substring(0, index.value) + "\t<-HERE\t" + json.substring(index.value));
        }
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
