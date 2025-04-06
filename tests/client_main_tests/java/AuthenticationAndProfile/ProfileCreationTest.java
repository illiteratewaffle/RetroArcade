package client_main_tests.java.AuthenticationAndProfile;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import player.PlayerManager;
import AuthenticationAndProfile.*;

import java.sql.SQLException;

class ProfileCreationTest {

    @Test
    void validHashedPassword() {
        String password = "1234567";
        String hashedPassword = ProfileCreation.hashedPassword(password);
        assertEquals("8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414".toUpperCase(), hashedPassword);
    }

    @Test
    void blankHashedPassword() {
        String password = "";
        String hashedPassword = ProfileCreation.hashedPassword(password);
        assertEquals("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855".toUpperCase(), hashedPassword);
    }

    @Test
    void createNewProfile() {
        String username = Profile.generateUsername();
        String email = username + "@example.com";
        String password = Profile.generatePassword();
        Profile newProfile;
        int newProfileID;
        try {
            newProfile = ProfileCreation.createNewProfile(username, email, password);
            String hashedPassword = ProfileCreation.hashedPassword(password);
            newProfileID = newProfile.getID();

            assertEquals(Integer.toString(newProfileID), PlayerManager.getAttribute(newProfileID, "ID"));
            assertEquals(username, PlayerManager.getAttribute(newProfileID, "username"));
            assertEquals(email, PlayerManager.getAttribute(newProfileID, "email"));
            assertEquals(hashedPassword, PlayerManager.getAttribute(newProfileID, "hashed_password"));
        } catch (SQLException s1) {
            System.out.println("Error with getAttribute: " + s1.getMessage());
            ProfileDatabaseAccess.removeProfile(newProfileID);
        }
        ProfileDatabaseAccess.removeProfile(newProfileID);
    }
}