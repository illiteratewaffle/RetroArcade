package server_main.AuthenticationAndProfile;

import server.player.PlayerManager;

import java.io.IOException;
//import java.util.HashMap;
/**
 * Authentication Class handles Profile Login and Logout
 * @author Alessia Flaig
 */
public class Authentication {
    private static Profile profileLoggedIn;

//    /**
//     * Constructor to be created when program runs?
//     */
//    public Authentication() {
//
//    }
    /**
     * logIn(String username, String password)
     * This method takes a provided username and password from the LogIn page and authenticates the LogIn credentials.
     * When successfully logged in, profile's isOnline is set to true.
     * @param username String
     * @param password String
     * @return true if login is successful. Throws exceptions for incorrect username or password if
     * login fails.
     */
    public boolean logIn(String username, String password) throws Exception {
        String hashedPassword = ProfileCreation.hashedPassword(password);
        //try {
            int id = PlayerManager.authenticatePlayer(username, hashedPassword);
            Profile profile = ProfileDatabaseAccess.obtainProfile(id);
            setProfileLoggedIn(profile);
            profile.setOnlineStatus(true);
            return true;
//        } catch (IOException e) {
//            System.out.println("Wrong username or password.");
//            return false;
//        }
    }

    /**
     * logOut() Sets profile's isOnline to false and sets the profileLoggedIn to null so that the previous profile information is no longer accessed.
     */
    public static void logOut(){
        profileLoggedIn.setOnlineStatus(false);
        profileLoggedIn = null;
    }

    /**
     * getProfileLoggedIn() to access Profile object of the profile currently logged in.
     * @return Profile currently logged in to access profile information.
     */
    public Profile getProfileLoggedIn(){
        return profileLoggedIn;
    }

    /**
     * Sets the Profile currently logged into on the program.
     * @param profile Profile
     */
    public void setProfileLoggedIn(Profile profile){
        profileLoggedIn = profile;
    }
}