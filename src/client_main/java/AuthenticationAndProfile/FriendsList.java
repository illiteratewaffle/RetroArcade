package AuthenticationAndProfile;


import java.util.ArrayList;
import java.util.List;

/**
 * FriendsList class handles operations related to a users friends list and friend requests
 * The class includes methods to add friends, remove friends, accept and send requests
 * @author Abigail Mullen
 */
public class FriendsList {
    private List<Long> friends = new ArrayList<>(); // stores list of friends as ids
    private List<Long> friendRequests = new ArrayList<>(); // stores list of friend requests as ids
    private long id;
    private String username;

    /**
     * Constructor initializing the friends and friend request lists
     * @param friends
     * @param friendRequests
     */
    public FriendsList(List<Long> friends, List<Long> friendRequests) {
        this.friends = friends;
        this.friendRequests = friendRequests;
    }

    public FriendsList(){

    }

    /**
     * Sets the list of friends for the profile
     * @param friends List of friends ids to set
     */
    public void setFriends(List<Long> friends) {
        this.friends = friends;
    }

    /**
     * Sets the list of friend requests for this profile
     * @param friendRequests List of the friend request ids to set
     */
    public void setFriendRequests(List<Long> friendRequests) {
        this.friendRequests = friendRequests;
    }

    /**
     * Retrieves the list of friends associated with the profile
     * @return A list of the friends id numbers
     */
    public List<Long> getFriendsList() {
        return friends;
    }

    /**
     * Retrieves the list of friend requests associated with the profile
     * @return A list of the friend request id numbers
     */
    public List<Long> getFriendRequests() {
        return friendRequests;
    }

    /**
     * Finds and returns the id associated with a profile using the username
     * @param username The username used to search
     * @return The id associated with the username or -1 if not found
     */
    public long idFromUsername(String username){
        ArrayList<ArrayList<String>> profiles = ProfileCSVReader.openProfilesFile("profiles_export.csv");
        for(ArrayList<String> profile : profiles){
            if (profile.size() > ProfileCSVReader.USER_INDEX){
                String foundUsername = profile.get(ProfileCSVReader.USER_INDEX); // ensures enough elements in array
                if (foundUsername.equals(username)){ // if the username matches the username of a current profile
                    return Long.parseLong(profile.get(ProfileCSVReader.ID_INDEX)); // returns ID associated with given username
                }
            }
        }
        return -1; // if username is not found in the database
    }

    /**
     * Updates the CSV file adter any changes to the users friends or friend requests
     */
    public void updateCSV() {
        ArrayList<ArrayList<String>> profiles = ProfileCSVReader.openProfilesFile("profiles_export.csv");

        for (ArrayList<String> profile : profiles) {
            if (profile.size() > ProfileCSVReader.ID_INDEX) {
                long profileId = Long.parseLong(profile.get(ProfileCSVReader.ID_INDEX)); // ensures enough elements in array
                if (profileId == id) {
                    // update friends list and friends request list to account for changes
                    profile.set(ProfileCSVReader.FRIENDS_INDEX, friends.toString().replace("[", "").replace("]", "")); // csv file contains comma seperated list of ids (DOUBLE CHECK)
                    profile.set(ProfileCSVReader.FREQUEST_INDEX, friendRequests.toString().replace("[", "").replace("]", "")); // DOUBLE CHECK -> saves as 123,456,789
                    break; // stops loop when the required profile has been updated
                }
            }
        }
        ProfileCSVReader.writeProfilesFile("profiles_export.csv", profiles); // writes the changes to the CSV
    }


    /**
     * Adds a profile to the users friend list based on the provided username
     * @param username The username of the profile to add as a friend
     */
    public void addFriend(String username){
        long id = idFromUsername(username);
        if (id != -1 && !friends.contains(id)){
            friends.add(id); // adds the id to the list of friends

            updateCSV(); // updates the CSV to reflect changes
        }
    }

    /**
     * Removes a profile from the users friend list based on the provided username
     * @param username The username of the profile to remove as a friend
     */
    public void removeFriend(String username){
        long id = idFromUsername(username);
        friends.remove(id); // removes the id from the list of friends

        updateCSV(); // updates the CSV to reflect changes
    }

    /**
     * Accept a friend request from a user and adds them to the friends list
     * The request is removes from the friend request list
     * @param username The username of the user sending the friend request
     */
    public void acceptFriendRequest(String username){
        long id = idFromUsername(username);
        if (friendRequests.contains(id)){
            friendRequests.remove(id); // removes the id from the friend request list
            addFriend(username); // adds the friend to the friend list

            updateCSV(); // updates the CSV to reflect changes
        }

    }

    /**
     * Sends a friend request to the profile specified by the username
     * @param username The username of the profile to send the request to
     */
    public void sendFriendRequest(String username){ // username is of the profile to send the request to
        long recievingId = idFromUsername(username); // id to send the request to
        String sendingUsername = Profile.getUsername(); // username of the user sending the request
        long sendingId = idFromUsername(sendingUsername); // id of the user sending the request

        ArrayList<ArrayList<String>> profiles = ProfileCSVReader.openProfilesFile("profiles_export.csv");
        for (ArrayList<String> profile : profiles){
            if (profile.size() > ProfileCSVReader.ID_INDEX){
                long id = Long.parseLong(profile.get(ProfileCSVReader.ID_INDEX));
                if (id == recievingId){
                    String friendRequests = profile.get(ProfileCSVReader.FREQUEST_INDEX); // get existing friend request string
                    if (!friendRequests.contains(String.valueOf(sendingId))){
                        if (!friendRequests.isEmpty()){
                            friendRequests += ","; // adds a comma if the list is not empty
                        }
                        friendRequests += sendingId; // adds the id of the sender to the friend request list of the recieving user
                        profile.set(ProfileCSVReader.FREQUEST_INDEX, friendRequests); // update the friend request list in the profile
                    }
                    break; // stop loop when friends list has been updated
                }
            }
        }
        ProfileCSVReader.writeProfilesFile("profiles_export.csv", profiles); // write the updates back to the CSV
    }
}

// String username = Profile.exportUsername(id)
// Long id = idFromUsername(username)