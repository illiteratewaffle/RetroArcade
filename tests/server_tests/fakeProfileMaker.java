package server_tests;


import AuthenticationAndProfile.FriendsList;
import AuthenticationAndProfile.GameHistory;
import AuthenticationAndProfile.PlayerRanking;
import AuthenticationAndProfile.Profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class fakeProfileMaker {

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
        GameHistory gameHistory = new GameHistory(hist, achievement); // assuming default constructor
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
