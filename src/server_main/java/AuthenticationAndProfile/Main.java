package AuthenticationAndProfile;

import player.PlayerManager;

import java.io.IOException;
import java.sql.SQLException;
import static management.ServerLogger.log;
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

        //Clearing Profiles:
        for (int i = 0; i < 200; i++) {
            try {
                PlayerManager.deleteProfile(i);
                ProfileDatabaseAccess.getAllProfiles();
            } catch (SQLException | IOException s){
                log(s.getMessage());
            }
        }
    }
}
