package GUI_client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Manages chat and prevents inappropriate language during gameplay.
 * Takes in message and checks against text file containing inappropriate language
 * (from the internet, we did not write all of these words out).
 */
public class ChatManager {
    private static final Set<String> bannedWords = new HashSet<>();

    static {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(ChatManager.class.getResourceAsStream("/chatManagerAssets/banned_words.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                bannedWords.add(line.trim().toLowerCase());
            }
        } catch (Exception e) {
            System.err.println("Could not load banned words list.");
        }
    }
    public boolean isAppropriate(String message) {
        String lowerCaseMessage = message.toLowerCase();
        for (String banned : bannedWords) {
            if (lowerCaseMessage.contains(banned)) {
                return false;
            }
        }
        return true;
    }
}

