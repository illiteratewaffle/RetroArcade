package AuthenticationAndProfile;
import leaderboard.Leaderboard;

import java.util.HashSet;
import java.util.HashMap;
/**
 * ProfileDatabase is the Class containing all the information stored on the Network for all registered accounts.
 * @author Alessia Flaig
 */
public class ProfileDatabase {
    private static ProfileEmailSet<String> profileEmailSet;
    private static HashMap<String, Profile> profiles = new HashMap<String, Profile>();
    private Leaderboard leaderboard = new Leaderboard();

    /**
     * ProfileDatabase constructor
     */
    public ProfileDatabase() {

    }

    /**
     * addProfile(String username, Profile profile) adds the username key and its associated profile to the HashMap of all
     * registered accounts.
     * @param username
     * @param profile
     */
    public static void addProfile(String username, Profile profile){
        profiles.put(username, profile);
    }

    /**
     * removeProfile(String username) takes the provided username key to remove the email from the ProfileEmailSet and the profile from the Database.
     * @param username
     */
    public static void removeProfile(String username){
        profileEmailSet.removeEmail(profiles.get(username).getEmail());
        profiles.remove(username);
    }

    /**
     * getProfile(String username) is used to obtain the Profile object for the provided username.
     * @param username
     * @return Profile
     */
    public static Profile getProfile(String username){
        return profiles.get(username);
    }

    /**
     * getAllProfiles() is used to obtain the HashMap of all username keys and all Profile values.
     * @return HashMap<String, Profile>
     */
    public static HashMap<String, Profile> getAllProfiles() {
        return profiles;
    }

    /**
     * Method to search for Profiles with usernames similar to the searched term.
     * @param search String
     */
    public static void searchForProfile(String search){

    }

    /**
     * Method to obtain the profile information required to view a profile.
     * @param profile
     */
    public static void viewProfile(Profile profile){

    }
    /**
     * Method to obtain the Leaderboard from ranks of all profiles in the database.
     */
    public static Leaderboard viewLeaderboard() {

    }

    /**
     * Method to obtain the Leaderboard consisting of only a profile's Friends List.
     * @return
     */
    public static Leaderboard viewLeaderboard(Profile profile, FriendsList friendsList){

    }

    /**
     * Email is verified to not be already associated to a profile and then is added to the profileEmailSet.
     * @param email
     * @return
     * @throws Exception "Email is already associated with an account"
     */
    public static boolean addEmailToDatabase(String email) throws Exception{
        if (profileEmailSet.isValidEmail(email)) {
            profileEmailSet.addEmail(email);
            return true;
        } else{
            throw new Exception("Email is already associated with an account.");
        }
    }

    /**
     * ProfileEmailSet Class for the HashSet ProfileEmailSet to access all emails currently associated with an account.
     * @author Alessia Flaig
     */
    private static class ProfileEmailSet<String> {
        private HashSet<String> profileEmailSet = new HashSet<String>();

        public ProfileEmailSet() {
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

        /**
         * Adds email to the ProfileEmailSet HashSet.
         * @param email
         */
        public void addEmail(String email){
            profileEmailSet.add(email);
        }
    }
}
