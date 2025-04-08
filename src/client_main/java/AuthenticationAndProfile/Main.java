package AuthenticationAndProfile;

import server.player.PlayerManager;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import static server.management.ServerLogger.log;
public class Main {
    /**
     * Main Method to start the server
     */
    public static void main(String[] args) {
//        ServerController controller = new ServerController();
//        int port = 5050;
//        controller.startServer(port);
//        // Create an infinite while loop so the server does not exit
//        // TODO: the main thread should be used for a proper task
//        Scanner sc = new Scanner(System.in);
//        boolean running = true;
//        while (running) {
//            if (sc.nextLine().equalsIgnoreCase("exit"))
//                running = false;
//        }

//        //Clearing Profiles:
//        for (int i = 0; i < 300; i++) {
//            try {
//                PlayerManager.deleteProfile(i);
//                ProfileDatabaseAccess.getAllProfiles();
//            } catch (SQLException | IOException s){
//                log(s.getMessage());
//            }
//        }
//        try {
//            ProfileCreation.createNewProfile("username1", "email@email.com", "password");
//            int id;
//            System.out.println(id = PlayerManager.getProfileID("username1"));
//            PlayerManager.deleteProfile(id);
//            System.out.println("Profile deleted.");
//            System.out.println(ProfileDatabaseAccess.obtainFriendsList(id));
//        } catch (IOException | SQLException | NoSuchAlgorithmException s) {
//            System.out.println(s.getMessage());
//        }
//        try {
//            Profile profile = ProfileCreation.createNewProfile("usernameGUITest", "emailGUITest@gmail.com", "password");
//            int profileID = profile.getID();
//            System.out.println(profile.getCurrentStatus());
//            System.out.println(profile.getUsername());
//            ProfileDatabaseAccess.obtainProfile(profileID);
//            Authentication.logOut(profileID);
//            ProfileDatabaseAccess.removeProfile(profileID);
//        } catch (SQLException | IOException | NoSuchAlgorithmException s) {
//            System.out.println(s.getMessage());
//        }
        try {
            PlayerManager.getProfileTable();
        }
        catch (SQLException s){
            System.out.println(s.getMessage());
        }
    }
}
