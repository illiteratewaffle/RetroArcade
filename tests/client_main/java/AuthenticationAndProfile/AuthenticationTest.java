package client_main.java.AuthenticationAndProfile;

import leaderboard.PlayerRanking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.player.PlayerManager;
import static org.junit.jupiter.api.Assertions.*;

class AuthenticationTest {
    private Profile profile;
    @BeforeEach
    void setUp() {
        String password = "1234567";
        String hashedPassword = ProfileCreation.hashedPassword(password);
        profile = new Profile("email1@email.com", hashedPassword,"nick", "This is bio.", false, "null", new FriendsList(), new PlayerRanking(), new GameHistory(), "C:profile/pic/path.png", "username", 2 );
    }

    @Test
    void logIn() {
        PlayerManager.registerPlayer("email@email,com", profile.getHashedPassword(), "username");
        assertTrue(Authentication.logIn("username", profile.getHashedPassword()));
    }

    @Test
    void logOut() {
        Authentication.setProfileLoggedIn(profile);
        Authentication.logOut();
        assertNull(Authentication.getProfileLoggedIn());
    }

    @Test
    void getProfileLoggedIn() {
        Authentication.setProfileLoggedIn(profile);
        assertEquals(profile, Authentication.getProfileLoggedIn());
    }

    @Test
    void setProfileLoggedIn() {
        //Profile profile = new Profile("email@email.com", "46#B286734A8%367","nick", "This is bio.", false, "null", new FriendsList(), new PlayerRanking(), new GameHistory(), "C:profile/pic/path.png", "username", 2 );
        Authentication.setProfileLoggedIn(profile);
        assertEquals(profile, Authentication.getProfileLoggedIn());
    }
}