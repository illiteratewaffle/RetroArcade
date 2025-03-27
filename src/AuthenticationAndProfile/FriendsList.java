package AuthenticationAndProfile;

import java.util.ArrayList;
import java.util.List;

public class FriendsList {
    private List<String> friends = new ArrayList<>();
    private List<String> friendRequests = new ArrayList<>();

    public FriendsList(List<String> friends, List<String> friendRequests) {
        this.friends = friends;
        this.friendRequests = friendRequests;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public List<String> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(List<String> friendRequests) {
        this.friendRequests = friendRequests;
    }
}
