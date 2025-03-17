package AuthenticationAndProfile;

public class ProfileCreation {
    private Profile createNewProfile(String email, String username, String password){
        ProfileEmailSet set = ProfileDatabase.getProfileEmailSet();
        set.isValidEmail(email);

    }

    /**
     * Hashing Algorithm for Password.
     * @param unhashedPassword
     * @return
     */
    public String hashPassword(String unhashedPassword){

    }

}