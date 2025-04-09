package server_tests;


import AuthenticationAndProfile.FriendsList;
import AuthenticationAndProfile.GameHistory;
import AuthenticationAndProfile.PlayerRanking;
import AuthenticationAndProfile.Profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//TODO: Create Before and after to handle the creation of a bunch of profiles through ProfileCreation.createNewProfile()
public class fakeProfileMaker {
static int id = 0;

    public static Profile createFakeProfile() {
        String email = "test@example.com";
        String hashedPassword = "hashed_password_123";
        String nickname = "TestPlayer";
        String bio = "This is a test player.";
        boolean isOnline = true;
        String currentGame = "TestGame";
        FriendsList friendsList = new FriendsList(); // assuming default constructor
        PlayerRanking playerRanking = new PlayerRanking(); // assuming default constructor
        List<String> hist = new ArrayList<>();
        HashMap<String, Double> achievement = new HashMap<>();
        GameHistory gameHistory = new GameHistory(hist, achievement, id); // assuming default constructor
        String profilePicFilePath = "/tmp/test_profile_pic.png";
        String username = "TestUsername";
        int id = 999;

        return new Profile(
                email,
                hashedPassword,
                nickname,
                bio,
                isOnline,
                currentGame,
                friendsList,
                playerRanking,
                gameHistory,
                profilePicFilePath,
                username,
                id
        );

    }
}
