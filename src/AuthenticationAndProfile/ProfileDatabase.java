package AuthenticationAndProfile;
import leaderboard.Leaderboard;

import java.util.HashSet;

/**
 * ProfileDatabase is the Class containing all the information stored on the Network for all registered accounts.
 */
public class ProfileDatabase {
    private ProfileEmailSet<String> profileEmailSet = new ProfileEmailSet<String>();

    public HashMap<String, Profile> getProfiles() {
        return profiles;
    }

    private HashMap<String, Profile> profiles = new HashMap<String, Profile>();
    private Leaderboard leaderboard = new Leaderboard();

    public ProfileDatabase() {

    }

    /**
     * addProfile(String username, Profile profile) adds the username key and its associated profile to the HashMap of all
     * registered accounts.
     * @param username
     * @param profile
     */
    public void addProfile(String username, Profile profile){
        profiles.put(username, profile);

    }

    /**
     * removeProfile(String username) takes the provided username key to remove the profile from the Database.
     * @param username
     */
    public void removeProfile(String username){
        profiles.remove(username);

    }

    /**
     * getProfile(String username) is used to obtain the Profile object for the provided username.
     * @param username
     * @return Profile
     */
    public Profile getProfile(String username){
        return profiles.get(username);
    }

    /**
     * Method to search for Profiles with usernames similar to the searched term.
     * @param username
     */
    public void searchForProfile(String search){

    }

    /**
     * Method to obtain the profile information required to view a profile.
     * @param profile
     */
    public void viewProfile(Profile profile){

    }

    /**
     * get the HashSet of all emails associated to existing accounts.
     * @return
     */
    public ProfileEmailSet<String> getProfileEmailSet() {
        return profileEmailSet;
    }

    /**
     * Method to obtain the Leaderboard from ranks of all profiles in the database.
     */
    public Leaderboard viewLeaderboard() {

    }

    /**
     * Method to obtain the Leaderboard consisting of only a profile's Friends List.
     * @return
     */
    public Leaderboard viewLeaderboard(Profile, FriendsList){

    }

    /**
     * ProfileEmailSet Class for the HashSet ProfileEmailSet to access all emails currently associated with an account.
     * @author Alessia Flaig
     */
    private class ProfileEmailSet {
        private HashSet<String> profileEmailSet = new HashSet<String>();

        public ProfileEmailSet(HashSet<String> profileEmailSet) {
            this.profileEmailSet = profileEmailSet;
        }

        /**
         * isValidEmail(String email) checks if the provided email address can be used for an account.
         * @param email String
         * @return true if email is not already not in the HashSet.
         * Returns false if email is already associated with an account in the database.
         */
        public boolean isValidEmail(String email){
            return !profileEmailSet.contains(email);
        }

        /**
         * removeEmail(String email) removes the provided email from the ProfileEmailSet HashSet.
         * @param email String
         */
        public void removeEmail(String email){
            profileEmailSet.remove(email);
        }

        public void

    }

}
