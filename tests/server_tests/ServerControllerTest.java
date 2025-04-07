package server_tests;

import management.ServerController;
import management.ThreadRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

public class ServerControllerTest {
    private ServerController controller;

    @BeforeEach
    public void setup() {
        controller = new ServerController();
    }

    @Test
    public void testThreadIsRegistered() {
        BlockingQueue<?> queue = ThreadRegistry.getQueue(Thread.currentThread());
        assertNotNull(queue, "Thread should be registered with a message queue");
    }

    @Test
    public void testStartServerDoesNotCrash() {
        assertDoesNotThrow(() -> controller.startServer(5050), "startServer should not throw any exceptions");
    }
}
