package server_main.java.AuthenticationAndProfile;

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
    // private FriendsList friendsList;
    private Profile profileA;
    private Profile profileB;

    @BeforeEach
    void setUp(){
        try {
            String username1 = Profile.generateUsername();
            String email1 = username1 + "@example.com";
            String password1 = Profile.generatePassword();
            String username2 = Profile.generateUsername();
            String email2 = username2 + "@example.com";
            String password2 = Profile.generatePassword();
            Files.copy(Paths.get("profiles_export.csv"), Paths.get("test_profiles_export.csv"), StandardCopyOption.REPLACE_EXISTING);
            // two profiles created for testing purposes
            profileA = ProfileCreation.createNewProfile(username1, email1, password1);
            profileB = ProfileCreation.createNewProfile(username2, email2, password2);

        } catch (Exception e) {
            fail(e.getMessage()); // fail setup if any exceptions occur
        }
    }

    @AfterEach
    void tearDown() {
        // delete testing profiles after each test is done
        try {
            PlayerManager.deleteProfile(profileA.getID());
        } catch (SQLException s) {
            fail("deleteProfile error: " + s.getMessage()); // fails the test if deletion fails
        }
        try {
            PlayerManager.deleteProfile(profileB.getID());
        } catch (SQLException s) {
            fail("deleteProfile error: " + s.getMessage()); // fails the test if deletion fails
        }
    }

    @Test
    void addFriend() {
        try{
            FriendsList friendsList = profileA.getFriendsList(); // friends list of profile A
            friendsList.addFriend(profileB.getID()); // adds profile B as a friends

            List<Integer> friends = friendsList.getFriends(); // retrieves updated list of friends

            // checks to ensure that profile B is present in profile A's friend list
            assertTrue(friends.contains(profileB.getID()), String.format("friends list should contain user with id %d", profileB.getID()));

        } catch (SQLException e) {
            fail(e.getMessage()); // fails the test if any error occurs
        }
    }

    @Test
    void removeFriend() {
        try{
            FriendsList friendsList = profileA.getFriendsList(); // friends list of profile A
            friendsList.addFriend(profileB.getID()); // adds profile B as a friend

            List<Integer> friendsBefore = friendsList.getFriends(); // retrieves and prints the updated friends list
            System.out.println(friendsBefore);

            friendsList.removeFriend(profileB.getID()); // removes profile B from profile A's friends list

            List<Integer> friendsAfter = friendsList.getFriends(); // retrieves and prints the updated list
            System.out.println(friendsAfter);
            // checks to ensure that profile B is no longer present in profile A's friend list
            assertFalse(friendsAfter.contains(profileB.getID()));
        } catch (SQLException e) {
            throw new RuntimeException(e); // fails the test if any error occurs
        }
    }

    @Test
    void sendFriendRequest(){
        try{
            FriendsList friendsListA = profileA.getFriendsList();
            FriendsList friendsListB = profileB.getFriendsList();

            List<Integer> friendRequestsBefore = friendsListB.getFriendRequests();
            System.out.println("before: " + friendRequestsBefore);

            friendsListA.sendFriendRequest(profileB.getID());

            List<Integer> friendRequestsAfter = friendsListB.getFriendRequests();
            System.out.println("after: " + friendRequestsAfter);
            assertTrue(friendRequestsAfter.contains(profileA.getID()), "Friend request should be sent and appear in receiver’s list");
        } catch (Exception e) {
            fail("sendFriendRequest error: " + e.getMessage());
        }
    }

    @Test
    void acceptFriendRequest() {
        try {
            FriendsList friendsListA = profileA.getFriendsList();
            FriendsList friendsListB = profileB.getFriendsList();

            List<Integer> friendRequestsBefore = friendsListB.getFriendRequests();
            System.out.println("before: " + friendRequestsBefore);

            friendsListA.sendFriendRequest(profileB.getID());

            List<Integer> friendRequestsAfter = friendsListB.getFriendRequests();
            System.out.println("after: " + friendRequestsAfter);

            friendsListB.acceptFriendRequest(profileA.getID());

            assertTrue(friendsListB.getFriends().contains(profileA.getID()));

            // WORKS TO HERE
            System.out.println(friendsListA.getFriends());
            assertTrue(friendsListA.getFriends().contains(profileB.getID()));

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void rejectFriendRequest(){
        try {
            FriendsList listA = profileA.getFriendsList();
            FriendsList listB = profileB.getFriendsList();

            listA.sendFriendRequest(profileB.getID());
            listB.rejectFriendRequest(profileA.getID());

            // print out the friend request list to check its state
            System.out.println("Friend requests in listB after rejection: " + listB.getFriendRequests());

            // Verify that the friend request has been removed
            assertFalse(listB.getFriendRequests().contains(profileA.getID()), "Friend request should be removed after rejection");
        } catch (SQLException | IOException e) {
            fail(e.getMessage());
        }
    }

}