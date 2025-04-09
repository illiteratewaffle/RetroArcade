package server_tests;

import management.ServerLogger;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServerLoggerTest {

    @Test
    public void testLoggerCreatesLogFile() throws Exception {
        ServerLogger.startServerLogger();
        ServerLogger.log("This is a test log entry");
        ServerLogger.closeServerLogger();

        File logDir = new File(ServerLogger.getDIR());
        assertTrue(logDir.exists() && logDir.isDirectory(), "Log directory should exist");

        boolean foundLog = Files.list(Paths.get(ServerLogger.getDIR()))
                .anyMatch(path -> path.getFileName().toString().startsWith("logs_"));

        assertTrue(foundLog, "A log file should be created");
    }
}