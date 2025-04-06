package AuthenticationAndProfile;

//import org.postgresql.util.PSQLException;
import player.PlayerManager;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * ProfileCreation Class handles converting and verify Create Account Menu parameters to create a new Profile and Update ProfileDatabase.
 * @author Alessia Flaig
 */
//Considering moving within Profile database because the hashPassword function is also used for logIn?
public class ProfileCreation {
//    /**
//     * Takes email, username, and password entered in on Create Account Menu. Checks that the entered email and username are not associated with
//     * another account. Exceptions with messages detailing issue will be thrown. If valid email and username, a new Profile will be created and
//     * added to the ProfileDatabase.
//     * @param email String
//     * @param username String
//     * @param password String
//     * @throws Exception "Email is already associated with an account." from addEmail method and Exception "Username already exists"
//     */
//    private void createNewProfile(String email, String username, String password) throws Exception{
//        if (ProfileDatabase.getAllProfiles().containsKey(username)){
//            throw new Exception("Username already exists");
//            } else {
//            if (ProfileDatabase.addEmailToDatabase(email)) {
//                //We might want to check that the password is a decent password?
//                String hashedPassword = hashedPassword(password);
//                Profile newProfile = new Profile(email, username, hashedPassword);
//                if (!PlayerManager.registerPlayer(username, hashedPassword)) { //handle register player errors
//                    throw new Exception("Database could not register player. Log in Credentials are not unique.");
//                    PlayerManager.updatePlayer(newProfile);
//                }
//            }
//        }
//    }

    /**
     * Takes email, username, and password entered in on Create Account Menu. The password is hashed for security purposes before
     * the parameters are used to register a new profile to the database. Exceptions are thrown if the submitted username or email
     * are already associated with an existing profile.
     * @param email
     * @param username
     * @param password
     * @return boolean to indicate if registration was successful
     */
    public static Profile createNewProfile(String username, String email, String password) throws SQLException {
        String hashedPassword = hashedPassword(password);
        try {
            int id = PlayerManager.registerPlayer(username, email, hashedPassword); //handle register player errors
            System.out.println(id);
//            if (id == -1) {
//                System.out.println("Invalid email or username. Credentials already associated with an existing account.");
//                return false;
//            } else {
                System.out.println("Profile registered correctly. Trying to Log In.");
                Profile loggedInProfile = Authentication.logIn(username, password);
                return loggedInProfile;
            //}
            } catch(SQLException s){
                throw new SQLException("Login Unsuccessful: " + s.getMessage());
            }
    }
//            Profile profile = ProfileDatabaseAccess.obtainProfile(id);
//            Authentication.setProfileLoggedIn(profile);
//            if(Authentication.getProfileLoggedIn() == profile){
//                return true;
//            } else {
//                return false;
//            }
//        }catch () {
//            System.out.println("Invalid email or username. Credentials already associated with an existing account.");
//            return null;
//    }

        /**
         * Hashing Algorithm for Password. Takes the entered password and encrypts it to save profile credentials to the ProfileDatabase.
         * @param unhashedPassword
         * @return
         */
        public static String hashedPassword (String unhashedPassword){
            String hashed = "";
            //inbuilt MessageDigest class for SHA-256 hashing
            //Source: https://www.baeldung.com/sha-256-hashing-java
            //Convert bytes to hex Source: https://www.geeksforgeeks.org/java-program-to-convert-byte-array-to-hex-string/
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] encodedHash = digest.digest(unhashedPassword.getBytes(StandardCharsets.UTF_8));
                for (int i = 0; i < encodedHash.length; i++) {
                    hashed += String.format("%02X", encodedHash[i]);
                }
            } catch (NoSuchAlgorithmException e) {
                System.out.println("No such SHA-256 algorithm");
            }
            return hashed;
        }


//    /**
//     * Method to delete the profile currently logged inm from the ProfileDatabase and then log them out.
//     * @param authenticationSession
//     */
//    private static void deleteProfile(Authentication authenticationSession){
//        ProfileDatabase.removeEmailFromDatabase(authenticationSession.getProfileLoggedIn().getEmail());
//        ProfileDatabase.removeProfile(authenticationSession.getProfileLoggedIn().getUsername());
//        authenticationSession.logOut();
//    }
        public static void main (String[]args) {
            try {
                Profile profile = ProfileCreation.createNewProfile("NewBranchDatabase", "newbranchdatabase@email.com", "1234586ass8sword");
                System.out.println(profile.getID());
                //should print true
                System.out.println(profile.getOnlineStatus());
                //System.out.println(ProfileDatabaseAccess.obtainProfile(5).getOnlineStatus());
            }catch (SQLException s) {
                System.out.println(s.getMessage());

            //Email should be: "EmailShould be in index 3@email.com"
            //System.out.println(Authentication.getProfileLoggedIn().getHashedPassword());
            //System.out.printf("HashedPassword should equal %s\n", hashedPassword("hashedPasswordInIndex4"));
            //System.out.println(Authentication.getProfileLoggedIn().getUsername());
            //Username should be : "username should be in index 1"
        }
        }
        }

