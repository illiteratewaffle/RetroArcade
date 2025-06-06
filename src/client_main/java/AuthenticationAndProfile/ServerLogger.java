package AuthenticationAndProfile;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

// TODO: this should be critiqued by someone else
/**
 * This class will be responsible for logging all information
 */
public class ServerLogger {
    private static final String DIR = "logs";
    private static PrintWriter writer;
    private static String filename = "logs_" + ZonedDateTime.now(java.time.ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE);

    /**
     * Starts the ServerLogger!
     */
    public static void startServerLogger() {
        updateFilename();
        createPrintWriter();
    }

    /**
     * Creates a PrintWriter object
     */
    private static void createPrintWriter() {
        try {
            // Make sure the log file has the necessary directory
            Files.createDirectories(Paths.get(DIR));
            // Create the writer object
            writer = new PrintWriter(new FileWriter(DIR + "\\" + filename, true));
            // Ensures the log file is saved properly if the server crashes or closed incorrectly
            Runtime.getRuntime().addShutdownHook(new Thread(ServerLogger::closeServerLogger));
        } catch (IOException e) {
            // This should be changed later to be more robust
            System.err.println("!!! CRITICAL ERROR: ServerLogger failed to start. Logging disabled. !!!");
        }
    }

    /**
     * Updates the filename if different and creates a new PrintWriter object
     */
    private static void updateFilename() {
        // Get the new filename
        String updatedFilename = "logs_" + ZonedDateTime.now(java.time.ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE);
        // If the filenames do not match, update the filename, close the old log file and create a new one
        if (!filename.equals(updatedFilename)) {
            filename = updatedFilename;
            closeServerLogger();
            createPrintWriter();
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
        // Combine the arguments ino a String and add a timestamp
        String timestamp = ZonedDateTime.now(java.time.ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String string = "[" + timestamp + "] " + String.join(" ", stringArgs);
        // Print the output to the console
        System.out.println(string);

        // Log the information in to a text file
        updateFilename();
        if (writer != null) {
            writer.println(string);
            // Ensure immediate writing
            writer.flush();
        }
    }

    /**
     * This function closes the PrintWriter.
     */
    public static synchronized void closeServerLogger() {
        // Check that the writer is not null
        if (writer != null) {
            writer.close();
            writer = null;
        }
    }

    /**
     * Get the DIR
     * @return the directory that the logs will be in
     */
    public static String getDIR() {
        return DIR;
    }
}
