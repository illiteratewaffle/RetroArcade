package AuthenticationAndProfile;


import player.PlayerManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static management.ServerLogger.log;

/**
 * FriendsList class handles operations related to a users friends list and friend requests
 * The class includes methods to add friends, remove friends, accept and send requests
 * @author Abigail Mullen
 */
public class FriendsList {
    private List<Integer> friends = new ArrayList<>(); // stores list of friends as ids
    private List<Integer> friendRequests = new ArrayList<>(); // stores list of friend requests as ids
    private int id;
    private String username;

    /**
     * Constructor initializing the friends and friend request lists
     *
     * @param friends
     * @param friendRequests
     */
    public FriendsList(List<Integer> friends, List<Integer> friendRequests, int id) {
        this.friends = friends;
        this.friendRequests = friendRequests;
        this.id = id;
    }

    public FriendsList() {

    }

    /**
     * Sets the list of friends for the profile
     *
     * @param friends List of friends ids to set
     */
    public void setFriends(List<Integer> friends) {
        this.friends = friends;
    }

    /**
     * Sets the list of friend requests for this profile
     *
     * @param friendRequests List of the friend request ids to set
     */
    public void setFriendRequests(List<Integer> friendRequests) {
        this.friendRequests = friendRequests;
    }

    /**
     * Retrieves the list of friends associated with the profile
     *
     * @return A list of the friends id numbers
     */
    public List<Integer> getFriends() {
        return friends;
    }

    /**
     * Retrieves the list of friend requests associated with the profile
     *
     * @return A list of the friend request id numbers
     */
    public List<Integer> getFriendRequests() {
        return friendRequests;
    }

    /**
     * Finds and returns the id associated with a profile using the username
     *
     * @param username The username used to search
     * @return The id associated with the username or -1 if not found
     */
    public int idFromUsername(String username) throws IOException {
        try {
            ArrayList<ArrayList<String>> profiles = ProfileCSVReader.openProfilesFile("profiles_export.csv");
            for (ArrayList<String> profile : profiles) {
                if (profile.size() > ProfileCSVReader.USER_INDEX) {
                    String foundUsername = profile.get(ProfileCSVReader.USER_INDEX); // ensures enough elements in array
                    if (foundUsername.equals(username)) { // if the username matches the username of a current profile
                        return Integer.parseInt(profile.get(ProfileCSVReader.ID_INDEX)); // returns ID associated with given username
                    }
                }
            }
            return -1; // if username is not found in the database
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Updates the CSV file after any changes to the users friends or friend requests
     */
    public void updateCSV() throws IOException {
        ArrayList<ArrayList<String>> profiles = ProfileCSVReader.openProfilesFile("profiles_export.csv");

        for (ArrayList<String> profile : profiles) {
            if (profile.size() > ProfileCSVReader.ID_INDEX) {
                int profileId = Integer.parseInt(profile.get(ProfileCSVReader.ID_INDEX)); // ensures enough elements in array
                if (profileId == id) {
                    // update friends list and friends request list to account for changes
                    profile.set(ProfileCSVReader.FRIENDS_INDEX, friends.toString().replace("[", "").replace("]", "")); // csv file contains comma seperated list of ids (DOUBLE CHECK)
                    profile.set(ProfileCSVReader.FREQUEST_INDEX, friendRequests.toString().replace("[", "").replace("]", ""));
                    break; // stops loop when the required profile has been updated
                }
            }
        }
        ProfileCSVReader.writeProfilesFile("profiles_export.csv", profiles); // writes the changes to the CSV
    }


    /**
     * Adds a profile to the users friend list based on the provided username
     *
     * @param friendID The ID of the profile to add as a friend
     */
    public void addFriend(int friendID) throws SQLException {
        try {
            if (friendID != -1 && !friends.contains(friendID)) {
                PlayerManager.addToFriendsList(id, friendID);// adds the id to the list of friends
                friends.add(friendID);
            }
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }
    }

    /**
     * Removes a profile from the users friend list based on the provided username
     *
     * @param friendID The ID of the profile to remove as a friend
     */
    public void removeFriend(int friendID) throws SQLException {
        try {
            friends.remove(friendID); // removes the id from the list of friends

            PlayerManager.deleteFriend(id, friendID);
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }
    }

    /**
     * Accept a friend request from a user and adds them to the friends list
     * The request is removes from the friend request list
     *
     * @param friendID The ID of the user sending the friend request
     */
    public void acceptFriendRequest(int friendID) throws IOException, SQLException{
        try {
            if (friendRequests.contains(friendID)) {
                friendRequests.remove(friendID); // removes the id from the friend request list
                addFriend(friendID); // adds the friend to the friend list
                ProfileDatabaseAccess.obtainProfile(friendID).getFriendsList().addFriend(id);//add the friend to the friend's friendsList
                PlayerManager.deleteFriendRequest(id, friendID); //remove friend request from database
            }
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }

    }

    /**
     * Sends a friend request to the profile specified by the username
     *
     * @param friendID The id of the profile to send the request to
     */
    public void sendFriendRequest(int friendID) throws SQLException, IOException { // username is of the profile to send the request to
        int recievingId = friendID; // id to send the request to
        String sendingUsername;
        try {
            sendingUsername = ProfileDatabaseAccess.obtainProfile(this.id).getUsername(); // username of the user sending the request
            int sendingId = this.id; // id of the user sending the request
            String receivingUsername = PlayerManager.getAttribute(recievingId, "username");
            ArrayList<ArrayList<String>> profiles = ProfileCSVReader.openProfilesFile("profiles_export.csv");
            PlayerManager.addToFriendRequests(recievingId, sendingId);
            log(String.format("%s has sent a friend request to %s", sendingUsername, receivingUsername));
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}

// String username = Profile.exportUsername(id)
// int id = idFromUsername(username)