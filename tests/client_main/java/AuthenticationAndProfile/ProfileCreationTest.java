package client_main.java.AuthenticationAndProfile;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import server.player.PlayerManager;
import java.util.UUID;

class ProfileCreationTest {

    @Test
    void validHashedPassword() {
        String password = "1234567";
        String hashedPassword = ProfileCreation.hashedPassword(password);
        assertEquals("8BB0CF6EB9B17D0F7D22B456F121257DC1254E1F01665370476383EA776DF414", hashedPassword);
    }

    @Test
    void blankHashedPassword() {
        String password = "";
        String hashedPassword = ProfileCreation.hashedPassword(password);
        assertEquals("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855".toUpperCase(), hashedPassword);
    }

    @Test
    void createNewProfile() {
        // ChatGPT was used to generate the following 4 lines of code for randomly generating usernames, emails, and passwords.
        // The prompt used was: How can I make it so every time this test is run, a new username email and password are generated and it's id is used to identify it in the assertEquals statement
        // ChatGPT suggested using UUID.randomUUID() to generate a unique identifier for the username, email, and password.
        // UUID.randomUUID() generates a random universally unique identifier using a strong pseudo random number generator.
        // The identifier is then converted to a string using .toString. This ensures every time this test is ran, a unique profile is created.
        // The rest of the code in this test case (ProfileCreation.createNewProfile and following) was written without any AI assistance.


        String uniqueSuffix = UUID.randomUUID().toString().substring(0, 8);
        String username = "User_" + uniqueSuffix;
        String email = username + "@example.com";
        String password = "Pass_" + uniqueSuffix;

        ProfileCreation.createNewProfile(username, email, password);
        int newProfileID = Authentication.getProfileLoggedIn().getID();

        assertEquals(username, PlayerManager.getAttribute(newProfileID, "username"));
    }
}