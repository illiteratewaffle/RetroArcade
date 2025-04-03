import org.junit.jupiter.api.*;
import server.database.databaseConnector;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class DatabaseConnectorTest {
    @BeforeAll
    public static void setup() {
        databaseConnector.loadConfiguration("db-config-test.properties"); // Load test DB
    }

    @Test
    public void testDatabaseConnection() {
        Connection conn = databaseConnector.connect();
        assertNotNull(conn, "Database should be connected!");
    }

    @AfterAll
    public static void tearDown() {
        databaseConnector.disconnect();
    }
}