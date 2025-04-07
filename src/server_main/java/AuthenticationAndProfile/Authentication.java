package AuthenticationAndProfile;

import player.PlayerManager;
//import java.util.HashMap;
import java.sql.SQLException;
/**
 * Authentication Class handles Profile Login and Logout
 * @author Alessia Flaig
 */
public class Authentication {
    //private static Profile profileLoggedIn;

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
     *
     * @param username String
     * @param password String
     * @return true if login is successful. Throws exceptions for incorrect username or password if
     * login fails.
     */
    //return profile object
    public static Profile logIn(String username, String password) throws SQLException {
        String hashedPassword = ProfileCreation.hashedPassword(password);
        Profile profile;
        try {
            int id = PlayerManager.authenticatePlayer(username, hashedPassword);
            //if (id != -1) {
            profile = ProfileDatabaseAccess.obtainProfile(id);
            //setProfileLoggedIn(profile);
            profile.setOnlineStatus(true);
            System.out.printf("Player %d is setOnline\n", id);
            return profile;
//        } else {
//            System.out.println("Unable to Log In.");
//
//        }
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }
    }

    /**
     * logOut() Sets profile's isOnline to false and sets the profileLoggedIn to null so that the previous profile information is no longer accessed.
     */
    public static void logOut(int id) throws SQLException {
        try {
            ProfileDatabaseAccess.obtainProfile(id).setOnlineStatus(false);
            //profileLoggedIn = null;
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }
    }
}

//    /**
//     * getProfileLoggedIn() to access Profile object of the profile currently logged in.
//     * @return Profile currently logged in to access profile information.
//     */
//    public static Profile getProfileLoggedIn(){
//        return profileLoggedIn;
//    }
//
//    /**
//     * Sets the Profile currently logged into on the program.
//     * @param profile = Profile
//     */
//    public static void setProfileLoggedIn(Profile profile){
//        profileLoggedIn = profile;
//    }
//}

//public static void main(String[] args) {
//
//}