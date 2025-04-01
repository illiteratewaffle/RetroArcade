package server_main.AuthenticationAndProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * FriendsList class handles operations to do with the profile friends list and friend requests
 * @author Abigail Mullen
 */
public class FriendsList {
    private List<Long> friends = new ArrayList<>(); // stores list of friends as ids
    private List<Long> friendRequests = new ArrayList<>(); // stores list of friend requests as ids
    private long id;
    private String username;

    public FriendsList(List<Long> friends, List<Long> friendRequests) {
        this.friends = friends;
        this.friendRequests = friendRequests;
    }

    /**
     * getFriendsList returns the list of friends associated with the profile
     * @return
     */
    public List<Long> getFriendsList() {
        return friends;
    }

    /**
     * getFriendsRequests returns the list of friend requests associated with the profile
     * @return
     */
    public List<Long> getFriendRequests() {
        return friendRequests;
    }

    /**
     * addFriends adds a profile to a users friend list based on their username
     * @param username
     */
    public void addFriend(String username){
        long id = idFromUsername(username);
        if (!friends.contains(id)){
            friends.add(id);
            // update the csv
        }
    }

    /**
     * removeFriend removes a profile from a users friend list based on their username
     * @param username
     */
    public void removeFriend(String username){
        long id = idFromUsername(username);
        friends.remove(id);
        //update the csv
    }

    /**
     * acceptFriendRequest adds a friend
     * @param username
     */
    public void acceptFriendRequest(String username){
        long id = idFromUsername(username);
        if (friendRequests.contains(id)){
            friendRequests.remove(id);
            addFriend(id);
            //update the csv
        }

    }

    public void sendFriendRequest(String username){
        if (!friendRequests.contains(username) && !friends.contains(username)){
            friendRequests.add(username);
            // update the csv
        }
    }

    public Integer idFromUsername(String username){
        ArrayList<String[]> profiles = ProfileCSVReader.openProfilesFile("profiles_expoert.csv");
        for(String[] profile : profiles){
            if (profile.length > Profile.CSVReader.USER_INDEX){
                String foundUsername = profile[ProfileCSVReader.USER_INDEX]; // ensures enough elements in array
                if (foundUsername.equals(username)){
                    return Integer.parseInt(profileCSVReader.ID_INDEX]); // returns ID associated with given username
                }
            }
        }
        return null; // if username is not found in the database
    }

    public void setFriends(List<Long> friends) {
        this.friends = friends;
    }

    public void setFriendRequests(List<Long> friendRequests) {
        this.friendRequests = friendRequests;
    }
}

// String username = Profile.exportUsername(id)
// Long id = idFromUsername(username)