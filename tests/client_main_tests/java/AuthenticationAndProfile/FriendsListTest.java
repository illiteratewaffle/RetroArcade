package client_main_tests.java.AuthenticationAndProfile;

import AuthenticationAndProfile.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.PlayerManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FriendsListTest {

    private static Profile profileA;
    private static Profile profileB;
    @BeforeEach
    void setUp(){
        HashMap<String, Double> achievementProgress = new HashMap<>();
        List<String> gameHistory = new ArrayList<>();
        String password = "1234567";
        String hashedPassword = ProfileCreation.hashedPassword(password);
        profileA = new Profile("email1@email.com", hashedPassword,"bert", "Hi", false, "null", null, new PlayerRanking(), new GameHistory(gameHistory, achievementProgress), "C:profileA/pic/path.png", "username1", 2 );
        profileB = new Profile("email2@email.com", hashedPassword, "ernie", "Hello", false, null, null, new PlayerRanking(), new GameHistory(gameHistory, achievementProgress), "C:profileB/pic/path.png", "username2", 3 );
    }

    @AfterEach
    void tearDown(){
        try {
            PlayerManager.deleteProfile(profileA.getID());
        } catch (SQLException s){
            System.out.println("deleteProfile error: " + s.getMessage());
        }
        try {
            PlayerManager.deleteProfile(profileB.getID());
        } catch (SQLException s){
            System.out.println("deleteProfile error: " + s.getMessage());
        }
    }

    @Test
    void addFriend(){
//        FriendsList friendsOfA = new FriendsList(new ArrayList<>(), new ArrayList<>(), profileA.getID());
//        profileA.setFriendsList(friendsOfA);
//
//        int profileBID = profileB.getID();
//        friendsOfA.addFriend(profileB.getUsername());
//
//        assertTrue(friendsOfA.getFriends().contains(profileBID));
    }
    @Test
    void removeFriend(){
//        List<Integer> existingFriends = new ArrayList<>();
//        existingFriends.add(profileB.getID());
//        FriendsList friendsOfA = new FriendsList(existingFriends, new ArrayList<>(), profileA.getID());
//        profileA.setFriendsList(friendsOfA);
//
//        int profileBID = profileB.getID();
//        friendsOfA.removeFriend(profileB.getUsername());
//
//        assertFalse(friendsOfA.getFriends().contains(profileBID));
    }
    @Test
    void acceptFriendRequest(){

    }
    @Test
    void sendFriendRequest(){

    }
}