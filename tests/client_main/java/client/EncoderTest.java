package client;

import org.junit.jupiter.api.Test;

public class EncoderTest {

    @Test
    void encodeToJSON() {
        String id = "12345";
        String username = "Alice";
        boolean isActionAccepted = true;
        String action = "sendMessage";
        String message = "Hello Bob!";
        String recipient = "Bob";

        String jsonOutput = Encoder.encodeToJSON(id, username, isActionAccepted, action, message, recipient);

        assert jsonOutput.contains("\"ID\": \"12345\"") : "ID encoding failed";
        assert jsonOutput.contains("\"Username\": \"Alice\"") : "Username encoding failed";
        assert jsonOutput.contains("\"acceptedAction\": \"true\"") : "Accepted action encoding failed";
        assert jsonOutput.contains("\"Action\": \"sendMessage\"") : "Action encoding failed";
        assert jsonOutput.contains("\"message\": \"Hello Bob!\"") : "Message encoding failed";
        assert jsonOutput.contains("\"to\": \"Bob\"") : "Recipient encoding failed";
        assert jsonOutput.contains("\"from\": \"Alice\"") : "Sender encoding failed";
    }

    @Test
    void decodeJSON() {
        String jsonInput = "{\"ID\":\"12345\",\"Username\":\"Alice\",\"acceptedAction\":\"true\",\"Action\":\"sendMessage\",\"message\":\"Hello Bob!\",\"to\":\"Bob\",\"from\":\"Alice\"}";

        String[][] decodedArray = Encoder.decodeJSON(jsonInput);

        assert decodedArray.length > 0 : "Decoding failed: Empty array";
        assert "ID".equals(decodedArray[0][0]) && "12345".equals(decodedArray[0][1]) : "ID decoding failed";
        assert "Username".equals(decodedArray[1][0]) && "Alice".equals(decodedArray[1][1]) : "Username decoding failed";
        assert "acceptedAction".equals(decodedArray[2][0]) && "true".equals(decodedArray[2][1]) : "Accepted action decoding failed";
        assert "Action".equals(decodedArray[3][0]) && "sendMessage".equals(decodedArray[3][1]) : "Action decoding failed";
        assert "message".equals(decodedArray[4][0]) && "Hello Bob!".equals(decodedArray[4][1]) : "Message decoding failed";
        assert "to".equals(decodedArray[5][0]) && "Bob".equals(decodedArray[5][1]) : "Recipient decoding failed";
        assert "from".equals(decodedArray[6][0]) && "Alice".equals(decodedArray[6][1]) : "Sender decoding failed";
    }
}
