package server;

/**
 * This class will be responsible for logging all information
 */
public class ServerLogger {
    /**
     * Prints information to the console as well as logs in a text file
     * @param args the arguments that you want to be printed.
     */
    public static void log(Object... args) {
        // Convert the Object arguments into Strings
        String[] stringArgs = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            stringArgs[i] = args[i].toString();
        }
        // Combine the arguments ino a String
        String string = String.join(" ", stringArgs);
        // Print the output to the console
        System.out.println(string);

        // Log the information in to a text file
    }
}
