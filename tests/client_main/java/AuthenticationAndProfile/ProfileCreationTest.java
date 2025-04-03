package client_main.java.AuthenticationAndProfile;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import server.player.PlayerManager;

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
        ProfileCreation.createNewProfile("ProfileCreationTest", "ProfileCreationTest@email.com", "ProfileCreationTestPassword");
        assertEquals("ProfileCreationTest", PlayerManager.getAttribute(10, "username"));
    }
}