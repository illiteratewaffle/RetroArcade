//package server.management;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.*;
//import java.nio.file.*;
//import java.time.ZonedDateTime;
//import java.time.format.DateTimeFormatter;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * WARNING! Testing the ServerLogger log function will delete today's log text file.
// */
//class ServerLoggerTest {
//    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
//
//    @BeforeEach
//    public void setUp() {
//        // Delete the current log before testing
//        String filename = "logs_" + ZonedDateTime.now(java.time.ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE);
//        try {
//            Path path = Paths.get(getDIR() + "\\" + filename);
//            Files.deleteIfExists(path);
//        } catch (Exception e) {
//            fail("Failed to delete today's log file, is the file currently open?: " + e.getMessage());
//        }
//        // Create a new PrintSteam for testing printing to console
//        System.setOut(new PrintStream(outputStreamCaptor));
//    }
//
//    @Test
//    void logTest() {
//        // Start the ServerLogger, log a message, then close it
//        startServerLogger();
//        log("Hello World!", 10);
//        closeServerLogger();
//
//        // We want to test that the log function prints to the console
//        assertTrue(outputStreamCaptor.toString().contains("Hello World! 10"), "The message was not printed to console.");
//
//        // Test that the log function records to a text file
//        String filename = "logs_" + ZonedDateTime.now(java.time.ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE);
//        // Create the reader object
//        boolean foundMessage = false;
//        try (BufferedReader reader = new BufferedReader(new FileReader(getDIR() + "\\" + filename))) {
//            String line;
//            // Check if the message is in the log file
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//                if (line.contains("Hello World! 10")) {
//                    foundMessage = true;
//                }
//            }
//        } catch (IOException e) {
//            // If it fails to read the file, fail
//            fail("The log file failed to read.");
//        }
//        // If "Hello World! 10" is not in the file,
//        assertTrue(foundMessage, "Hello World! 10 was not found in the log file.");
//    }
//}