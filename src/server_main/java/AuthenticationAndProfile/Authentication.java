package AuthenticationAndProfile;

import player.PlayerManager;
import static management.ServerLogger.log;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
/**
 * Authentication Class handles Profile Login and Logout
 * @author Alessia Flaig
 */
public class Authentication {
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
    public static Profile logIn(String username, String password) throws SQLException, NoSuchAlgorithmException, IOException {
        try {
            String hashedPassword = ProfileCreation.hashedPassword(password);
            Profile profile;
            int id = PlayerManager.authenticatePlayer(username, hashedPassword);
            if (id != -1) {
                boolean isOnline = Boolean.parseBoolean(PlayerManager.getAttribute(id, "is_online"));
                if (isOnline) {
                    throw new SQLException("Profile is already logged in.");
                }
                else
                {
                    profile = ProfileDatabaseAccess.obtainProfile(id);
                    profile.setOnlineStatus(true);
                    log(String.format("Player %d is setOnline\n", id));
                    return profile;
                }
            } else {
                throw new SQLException("Incorrect Username or Password");
            }
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException(e.getMessage());
        } catch (IOException i) {
            throw new IOException(i.getMessage());
        }
    }

    /**
     * logOut() Sets profile's isOnline to false and sets the profileLoggedIn to null so that the previous profile information is no longer accessed.
     */
    public static void logOut(int id) throws SQLException, IOException {
        try {
            ProfileDatabaseAccess.obtainProfile(id).setOnlineStatus(false);
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

//    public static String forgotPassword(String username, String email) {
//        String password;
//        try {
//            password = PlayerManager.findPassword(username, email);
//            log("Password sent to email: " + email);
//            log("Password: " + password);
//        } catch (SQLException s) {
//            log("Username or Email do not exist. " + s.getMessage());
//        }
//        return password;
//    }
}
