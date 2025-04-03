package client_main.java.AuthenticationAndProfile;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import server.player.PlayerManager;

class ProfileCreationTest {

    @Test
    void hashedPassword() {
    }

    @Test
    void createNewProfile() {
        ProfileCreation.createNewProfile("ProfileCreationTest", "ProfileCreationTest@email.com", "ProfileCreationTestPassword");
        assertEquals("ProfileCreationTest", PlayerManager.getAttribute(10, "username"));
    }
}