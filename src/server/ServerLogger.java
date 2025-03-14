package server;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class will be responsible for logging all information
 */
public class ServerLogger {
    private static PrintWriter writer;

    // This could be contained in a method that starts the PrintWriter if preferred later
    static {
        try {
            // Create the writer object
            writer = new PrintWriter(new FileWriter("log.txt", true));
            // Ensures the log file is saved properly if the server crashes or closed incorrectly
            Runtime.getRuntime().addShutdownHook(new Thread(ServerLogger::close));
        } catch (IOException e) {
            // This should be changed later to be more robust
            e.printStackTrace();
        }
    }

    /**
     * Prints information to the console as well as logs in a text file
     * @param args the arguments that you want to be printed.
     */
    public synchronized static void log(Object... args) {
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
        if (writer != null) {
            writer.println(string);
            // Ensure immediate writing
            writer.flush();
        }
    }

    /**
     * This function closes the PrintWriter.
     */
    public static synchronized void close() {
        // Check that the writer is not null
        if (writer != null) {
            writer.close();
            writer = null;
        }
    }
}
