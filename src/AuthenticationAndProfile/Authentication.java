package AuthenticationAndProfile;

import java.util.HashMap;
/**
 * Authentication Class handles Profile Login and Logout
 * @author Alessia Flaig
 */
public class Authentication {
    private Profile profileLoggedIn;

    /**
     * Constructor to be created when program runs?
     */
    public Authentication() {

    }
    /**
     * logIn(String username, String password)
     * This method takes a provided username and password from the LogIn page and authenticates the LogIn credentials.
     * When successfully logged in, profile's isOnline is set to true.
     * @param username String
     * @param password String
     * @return true if login is successful. Throws exceptions for incorrect username or password if
     * login fails.
     */
    public boolean logIn(String username, String password) throws Exception{
        //NEW: call databaseConnector method to find profile with matching username and password
        //hash algorithm on password
        //send hashedPassword with username for database querie -> returns profile with database profile info
        //if correct logs in
        //pull Profile object - or build with csv info?
        //update isOnline and update database with push/profileExtraction method?
        if (!authenticateUsername(username)){
            throw new Exception("Invalid Username.");
        } else {
            if(!authenticatePassword(username, password)){
                throw new Exception("Incorrect password for username.");
            }
            Profile profile = ProfileDatabase.getProfile(username);
            setProfileLoggedIn(profile);
            profile.setOnline(true);
            return true;
        }
    }

    /**
     * logOut() Sets profile's isOnline to false and sets the profileLoggedIn to null so that the previous profile information is no longer accessed.
     */
    public void logOut(){
        //push update for setOnline
        //change local profile
        getProfileLoggedIn().setOnline(false);
        profileLoggedIn = null;
    }

    /**
     * authenticateUsername(String username) is to be used for logging into an account. Checks if the inputted username is
     * associated with an existing account.
     * @param username String
     * @return true if the provided username is a username of an account.
     */
    public boolean authenticateUsername(String username){
        //might no longer be needed if queries pull entire row of profile info
        HashMap<String, Profile> profiles = ProfileDatabase.getAllProfiles();
        return profiles.containsKey(username);
    }

    /**
     * authenticatePassword(String password) is to be used for logign into an accoutn. Checks if the inputted password matches
     * the password of the provided username.
     * @param password String
     * @return true if password is correct. Returns false if incorrect.
     */
    public boolean authenticatePassword(String username, String password){
        HashMap<String, Profile> profiles = ProfileDatabase.getAllProfiles();
        Profile profile = profiles.get(username);
        //Include hashing algorithm used in Password Creation in ProfileCreation
        if(profile.getHashedPassword().equals(password)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * getProfileLoggedIn() to access Profile object of the profile currently logged in.
     * @return Profile currently logged in to access profile information.
     */
    public Profile getProfileLoggedIn(){
        return this.profileLoggedIn;
    }

    /**
     * Sets the Profile currently logged into on the program.
     * @param profile Profile
     */
    public void setProfileLoggedIn(Profile profile){
        profileLoggedIn = profile;
    }
}