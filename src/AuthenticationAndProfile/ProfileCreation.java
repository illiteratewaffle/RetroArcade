package AuthenticationAndProfile;

/**
 * ProfileCreation Class handles converting and verify Create Account Menu parameters to create a new Profile and Update ProfileDatabase.
 * @author Alessia Flaig
 */
public class ProfileCreation {
    /**
     * Takes email, username, and password entered in on Create Account Menu. Checks that the entered email and username are not associated with
     * another account. Exceptions with messages detailing issue will be thrown. If valid email and username, a new Profile will be created and
     * added to the ProfileDatabase.
     * @param email String
     * @param username String
     * @param password String
     * @throws Exception "Email is already associated with an account." from addEmail method and Exception "Username already exists"
     */
    private void createNewProfile(String email, String username, String password) throws Exception{
        if (ProfileDatabase.getAllProfiles().containsKey(username)){
            throw new Exception("Username already exists");
            } else {
            if (ProfileDatabase.addEmailToDatabase(email)) {
                //We might want to check that the password is a decent password?
                String hashedPassword = hashedPassword(password);
                Profile newProfile = new Profile(email, username, hashedPassword);
                ProfileDatabase.addProfile(username, newProfile);
            }
        }
    }

    /**
     * Hashing Algorithm for Password. Takes the entered password and encrypts it to save profile credentials to the ProfileDatabase.
     * @param unhashedPassword
     * @return
     */
    public String hashedPassword(String unhashedPassword){
        String hashed = "";
        return hashed;

    }

}