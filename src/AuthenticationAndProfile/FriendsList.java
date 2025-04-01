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
     * idFromUsername finds the id associated with a profile using the username
     * @param username
     * @return the id associated withe the username
     */
    public long idFromUsername(String username){
        ArrayList<String[]> profiles = ProfileCSVReader.openProfilesFile("profiles_export.csv");
        for(String[] profile : profiles){
            if (profile.length > Profile.CSVReader.USER_INDEX){
                String foundUsername = profile[ProfileCSVReader.USER_INDEX]; // ensures enough elements in array
                if (foundUsername.equals(username)){ // if the username matches the username of a current profile
                    return Long.parseLong(ProfileCSVReader.ID_INDEX]); // returns ID associated with given username
                }
            }
        }
        return -1; // if username is not found in the database
    }

    public void updateCSV() {
        ArrayList<String[]> profiles = ProfileCSVReader.openProfilesFile("profiles_export.csv");

        for (String[] profile : profiles) {
            if (profile.length > ProfileCSVReader.ID_Index) {
                long profileId = Long.parseLong(profile[ProfileCSVReader.ID_INDEX]); // ensures enough elements in array
                if (profileId == id) {
                    profile[ProfileCSVReader.FRIENDS_INDEX] = friends.toString().replace("[", "").replace("]", ""); // csv file contains comma seperated list of ids (DOUBLE CHECK)
                    profile[ProfileCSVReader.FREQUEST_INDEX] = friendRequests.toString().replace("[", "").replace("]", "");
                    break; // stops loop when the required profile has been updated
                }
            }
        }
        ProfileCSVReader.writeProfilesFile("profiles_export.csv", profiles);
    }


    /**
     * addFriends adds a profile to a users friend list based on their username
     * @param username
     */
    public void addFriend(String username){
        long id = idFromUsername(username);
        if (id != -1 && !friends.contains(id)){
            friends.add(id);

            updateCSV();
        }
    }

    /**
     * removeFriend removes a profile from a users friend list based on their username
     * @param username
     */
    public void removeFriend(String username){
        long id = idFromUsername(username);
        friends.remove(id);

        updateCSV();
    }

    /**
     * acceptFriendRequest adds a friend
     * @param username
     */
    public void acceptFriendRequest(String username){
        long id = idFromUsername(username);
        if (friendRequests.contains(id)){
            friendRequests.remove(id);
            addFriend(username);

            updateCSV();
        }

    }

    /**
     *
     * @param username
     */
    public void sendFriendRequest(String username){ // username is of the profile to send the request to
        long recievingId = idFromUsername(username);
        String sendingUsername = Profile.getUsername();
        long sendingId = idFromUsername(sendingUsername);

        ArrayList<String[]> profiles = ProfileCSVReader.openProfilesFile("profiles_export.csv");
        for(String[] profile : profiles){
            if (profile.length > ProfileCSVReader.ID_INDEX){
                long id = Long.parseLong(profile[ProfileCSVReader.ID_INDEX]);
                if(id == recievingId){
                    String friendRequests = profile[ProfileCSVReader.FREQUEST_INDEX]; // get existing friend request string
                    if (!friendRequests.contains(String.valueOf(sendingId))){
                        if(!friendRequests.isEmpty()){
                            friendRequests += ",";
                        }
                        friendRequests += sendingId;
                        profile[ProfileCSVReader.FREQUEST_INDEX] = friendRequests;
                    }
                    break; // stop loop when friends list has been updated
                }
            }
        }
        ProfileCSVReader.writeProfilesFile("profiles_export.csv", profiles);
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