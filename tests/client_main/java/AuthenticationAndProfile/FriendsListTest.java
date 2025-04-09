package client_main.java.AuthenticationAndProfile;

import AuthenticationAndProfile.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.PlayerManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FriendsListTest {
    private FriendsList friendsList;
    private Profile profileA;
    private Profile profileB;

    @BeforeEach
    void setUp(){
        try {
            Files.copy(Paths.get("profiles_export.csv"), Paths.get("test_profiles_export.csv"), StandardCopyOption.REPLACE_EXISTING);
            profileA = ProfileCreation.createNewProfile(EmailGenerator.getNewEmail(), EmailGenerator.getNewEmail(), "PasswordA");
            profileB = ProfileCreation.createNewProfile(EmailGenerator.getNewEmail(), EmailGenerator.getNewEmail(), "PasswordB");

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
        try {
            PlayerManager.deleteProfile(profileA.getID());
        } catch (SQLException s) {
            fail("deleteProfile error: " + s.getMessage());
        }
        try {
            PlayerManager.deleteProfile(profileB.getID());
        } catch (SQLException s) {
            fail("deleteProfile error: " + s.getMessage());
        }
    }

    @Test
    void addFriend() throws SQLException, NoSuchAlgorithmException, IOException {
        try{
            FriendsList friendsList = profileA.getFriendsList();
            friendsList.addFriend(profileB.getID());
            List<Integer> friends = friendsList.getFriends();
            System.out.println(friends);
            System.out.println(friendsList);
            assertTrue(friends.contains(profileB.getID()), String.format("friends list should contain user with id %d", profileA.getID()));

        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

//    @Test
//    void removeFriend() throws IOException {
//        String usernameToAdd = "friend_username";
//        Integer idToAdd = 123;
//        friends.addFriend(usernameToAdd);
//
//
//    }
    @Test
    void acceptFriendRequest(){

    }
    @Test
    void sendFriendRequest(){

    }
}