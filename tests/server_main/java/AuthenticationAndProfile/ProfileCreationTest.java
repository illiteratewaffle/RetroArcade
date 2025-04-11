package server_main.java.AuthenticationAndProfile;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import player.PlayerManager;
import AuthenticationAndProfile.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

class ProfileCreationTest {

    @Test
    void validHashedPassword() {
        String password = "1234567";
        try {
            String hashedPassword = ProfileCreation.hashedPassword(password);
            assertEquals("8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414".toUpperCase(), hashedPassword);
        } catch (NoSuchAlgorithmException n) {
            fail(n.getMessage());
        }
    }

    @Test
    void blankHashedPassword() throws NoSuchAlgorithmException {
        try {
            String password = "";
            String hashedPassword = ProfileCreation.hashedPassword(password);
            assertEquals("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855".toUpperCase(), hashedPassword);
        } catch (NoSuchAlgorithmException n) {
            fail(n.getMessage());
        }
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

            assertEquals(Integer.toString(newProfileID), PlayerManager.getAttribute(newProfileID, "id"));
            assertEquals(username, PlayerManager.getAttribute(newProfileID, "username"));
            assertEquals(email, PlayerManager.getAttribute(newProfileID, "email"));
            assertEquals(hashedPassword, PlayerManager.getAttribute(newProfileID, "hashed_password"));
        } catch (SQLException s1) {
            fail("Error with getAttribute: " + s1.getMessage());
        } catch (NoSuchAlgorithmException | IOException n) {
            fail(n.getMessage());
        }
    }

    @Test
    void createNewProfile2() {
        String username = "login4";
        String email = username + "@example.com";
        String password = "password";
        Profile newProfile;
        int newProfileID;
        try {
            newProfile = ProfileCreation.createNewProfile(username, email, password);
            String hashedPassword = ProfileCreation.hashedPassword(password);
            newProfileID = newProfile.getID();

            assertEquals(Integer.toString(newProfileID), PlayerManager.getAttribute(newProfileID, "id"));
            assertEquals(username, PlayerManager.getAttribute(newProfileID, "username"));
            assertEquals(email, PlayerManager.getAttribute(newProfileID, "email"));
            assertEquals(hashedPassword, PlayerManager.getAttribute(newProfileID, "hashed_password"));
            PlayerManager.deleteProfile(newProfileID);
        } catch (SQLException s1) {
            fail("Error with getAttribute: " + s1.getMessage());
        } catch (NoSuchAlgorithmException | IOException n) {
            fail(n.getMessage());
        }
    }
}