package AuthenticationAndProfile;

import leaderboard.PlayerRanking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {

    private Profile profile;

    @BeforeEach
    void setUp() {
        //profile = new Profile("test@example.com", "hashedPassword", "nickname", "bio", true, "currentGame", new FriendsList(), new PlayerRanking(), new GameHistory(), new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB), "username", 1);
        profile = new Profile("test@example.com", "hashedPassword", "nickname", "bio", true, "currentGame", new FriendsList(), new PlayerRanking(), new GameHistory(), "C:/Pictures/Pictures/profile.png", "username", 1);

    }

    @Test
    void getEmail() {
        assertEquals("test@example.com", profile.getEmail());
    }

    @Test
    void setEmail() {
        profile.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", profile.getEmail());
    }

    @Test
    void getHashedPassword() {
        assertEquals("hashedPassword", profile.getHashedPassword());
    }

    @Test
    void setHashedPassword() {
        profile.setHashedPassword("newHashedPassword");
        assertEquals("newHashedPassword", profile.getHashedPassword());
    }

    @Test
    void getNickname() {
        assertEquals("nickname", profile.getNickname());
    }

    @Test
    void setNickname() {
        profile.setNickname("newNickname");
        assertEquals("newNickname", profile.getNickname());
    }

    @Test
    void getBio() {
        assertEquals("bio", profile.getBio());
    }

    @Test
    void setBio() {
        profile.setBio("newBio");
        assertEquals("newBio", profile.getBio());
    }

    @Test
    void setOnlineStatus() {
        profile.setOnlineStatus(false);
        assertFalse(profile.getOnlineStatus());
    }

    @Test
    void getCurrentStatus() {
        profile.setOnlineStatus(true);
        profile.setCurrentGame("newGame");
        profile.getCurrentStatus();
    }

    @Test
    void getCurrentGame() {
        assertEquals("currentGame", profile.getCurrentGame());
    }

    @Test
    void setCurrentGame() {
        profile.setCurrentGame("newGame");
        assertEquals("newGame", profile.getCurrentGame());
    }

    @Test
    void getGamesPlayed() {
        assertNotNull(profile.getGameHistory());
    }

    @Test
    void setGamesPlayed() {
        GameHistory newGamesPlayed = new GameHistory();
        profile.setGameHistory(newGamesPlayed);
        assertEquals(newGamesPlayed, profile.getGameHistory());
    }

    @Test
    void getProfilePic() {
        assertNotNull(profile.getProfilePicFilePath());
    }

//    @Test
//    void setProfilePic() {
//        BufferedImage newProfilePic = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
//        profile.setProfilePicFilePath("C:/Pictures/Pictures/profile.png");
//        assertEquals(newProfilePic, profile.getProfilePicFilePath());
//    }

    @Test
    void exportUsername() {
        assertEquals("username", Profile.exportUsername(1));
    }

    @Test
    void updateUsername() {
        profile.updateUsername("newUsername");
        assertEquals("newUsername", profile.getUsername());
    }
}