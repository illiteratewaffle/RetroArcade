package AuthenticationAndProfile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import static AuthenticationAndProfile.ServerLogger.log;

/**
 * ProfileCreation Class handles converting and verifying Create Account Menu parameters to create a new Profile and Update ProfileDatabase.
 * @author Alessia Flaig
 */
public class ProfileCreation {
    /**
     * Takes email, username, and password entered in on Create Account Menu. The password is hashed for security purposes before
     * the parameters are used to register a new profile to the database. Exceptions are thrown if the submitted username or email
     * are already associated with an existing profile.
     * @param email
     * @param username
     * @param password
     * @return boolean to indicate if registration was successful
     */
    public static Profile createNewProfile(String username, String email, String password) throws SQLException, NoSuchAlgorithmException, IOException {
        try {
            String hashedPassword = hashedPassword(password);
            int id = PlayerManager.registerPlayer(username, email, hashedPassword); //handle register player errors
            if (id == -1) {
                throw new SQLException("Invalid email or username. Credentials are already associated with an existing account.");
            } else {
                log("Profile registered correctly. Trying to Log In.");
                Profile loggedInProfile = Authentication.logIn(username, password);
                return loggedInProfile;
            }
        } catch (SQLException s) {
            throw new SQLException("Profile creation failed: " + s.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException(e.getMessage());
        } catch (IOException i) {
            throw new IOException(i.getMessage());
        }
    }

    /**
     * Hashing Algorithm for Password. Takes the entered password and encrypts it to save profile credentials to the ProfileDatabase.
     * @param unhashedPassword
     * @return hashedPassword String
     */
    public static String hashedPassword(String unhashedPassword) throws NoSuchAlgorithmException {
        String hashed = "";
        //inbuilt MessageDigest class for SHA-256 hashing
        //Source: https://www.baeldung.com/sha-256-hashing-java
        //Converts array of bytes to hexadecimal string
        // Source: https://www.geeksforgeeks.org/java-program-to-convert-byte-array-to-hex-string/
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(unhashedPassword.getBytes(StandardCharsets.UTF_8));
            for (int i = 0; i < encodedHash.length; i++) {
                hashed += String.format("%02X", encodedHash[i]);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException("No such SHA-256 algorithm");
        }
        return hashed;
    }
}

