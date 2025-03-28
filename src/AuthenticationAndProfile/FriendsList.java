package AuthenticationAndProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * FriendsList class handles operations to do with the profile friends list and friend requests
 * @author Abigail Mullen
 */
public class FriendsList {
    private List<String> friends = new ArrayList<>();
    private List<String> friendRequests = new ArrayList<>();
    private long id;
    private String username;

    public FriendsList(List<String> friends, List<String> friendRequests) {
        this.friends = friends;
        this.friendRequests = friendRequests;
    }

    /**
     * getFriendsList returns the list of friends associated with the profile
     * @return
     */
    public List<String> getFriendsList() {
        return friends;
    }

    /**
     * getFriendsRequests returns the list of friend requests associated with the profile
     * @return
     */
    public List<String> getFriendRequests() {
        return friendRequests;
    }

    /**
     * addFriends adds a profile to a users friend list based on their username
     * @param username
     */
    public void addFriend(String username){
        if (!friends.contains(username)){
            friends.add(username);
            // update the csv
        }
    }

    /**
     * removeFriend removes a profile from a users friend list based on their username
     * @param username
     */
    public void removeFriend(String username){
        friends.remove(username);
        //update the csv
    }

    /**
     * acceptFriendRequest adds a friend
     * @param username
     */
    public void acceptFriendRequest(String username){
        if (friendRequesnts.contains(username)){
            friendRequests.remove(usrname);
            addFriend(username);
            //update the csv
        }

    }



    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public void setFriendRequests(List<String> friendRequests) {
        this.friendRequests = friendRequests;
    }
}
// acceptRequest ?