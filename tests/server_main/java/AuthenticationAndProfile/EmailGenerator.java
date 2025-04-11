package server_main.java.AuthenticationAndProfile;

import java.util.Random;

public class EmailGenerator {

    private static Integer id = 0;
    private static Integer rand = new Random().nextInt();

    public static String getNewEmail(){
        id += 1;
        return "genEmail" + id.toString() + "_" + rand.toString() + "@email.com";
    }

}
