# Database Connector - Design Plan

## Overview
The **Database Connector** is responsible for managing database connections for the system. It provides a centralized way to establish, manage, and close database connections while ensuring proper security through configuration file loading. The **Database Connector** serves as an intermediary between the server and the PostgreSQL database, handling authentication and query execution.

## Responsibilities
- Loads database connection details (URL, user credentials) from a configuration file.
- Establishes and maintains a connection to the PostgreSQL database.
- Provides a reusable connection instance to prevent unnecessary reconnections.
- Closes database connections safely when they are no longer needed.
- Handles database-related exceptions and logs errors to prevent system crashes.

## Key Components
### 1. Configuration Loading
- Loads database credentials (URL, username, password) from a **`.properties`** file.
- Uses a `Properties` object to read key-value pairs from the file.
- Prevents hardcoding credentials in the codebase for security reasons.

### 2. Connection Management
- **Singleton-style connection management** ensures only one connection is active at a time.
- Calls `DriverManager.getConnection(URL, USER, PASSWORD)` to connect to PostgreSQL.
- Returns the **existing connection** if one is already established.
- Allows the server to reuse the same connection across multiple queries.

### 3. Connection Closing
- Ensures that the database connection is **properly closed** when the server shuts down.
- Prevents memory leaks by explicitly closing the connection.

## Functionality Breakdown

| Function Name                 | Description |
|------------------------------|-------------|
| `loadConfiguration(String filename)` | Reads database credentials from a `.properties` file and stores them. |
| `connect()` | Establishes a connection to the database and returns the connection object. |
| `disconnect()` | Closes the active database connection if one exists. |

## Implementation Details

### **1. Configuration File Loading**
- The database credentials are stored in a `.properties` file.
- The `loadConfiguration()` method reads this file using an `InputStream` to fetch the database URL, user, and password.
- The credentials are stored in private static variables (`URL`, `USER`, `PASSWORD`).

### **2. Connecting to the Database**
- The `connect()` method attempts to connect using `DriverManager.getConnection()`.
- If a connection already exists, it returns the **same connection instance** to avoid redundant connections.

### **3. Closing the Database Connection**
- The `disconnect()` method ensures the database connection is properly closed when no longer needed.
- If no connection exists, the method does nothing.

## Usage Example
```java
// Load database configuration
databaseConnector.loadConfiguration("db-config.properties");

// Establish connection
Connection conn = databaseConnector.connect();

// Perform database operations here...

// Close connection when finished
databaseConnector.disconnect();