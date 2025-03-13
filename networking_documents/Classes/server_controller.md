# Server Controller - Design Plan

## Overview
The **Server Controller** is responsible for managing the core server-side operations of the online multiplayer board game platform. It acts as the central hub for handling client connections and game session management. The server runs continuously, listening for incoming player connections and facilitating session creation and management.

## Responsibilities
- Accepts incoming client connections.
- Spawns a **Virtual Thread** for each connected client to handle communication.
- Manages **game session creation and destruction**.
- Assigns players to game sessions upon request.
- Handles **player disconnections and reconnections**, ensuring players are removed from game sessions appropriately.
- Returns players to **Network Manager** after a game session ends.
- Communicates with the **database layer** to retrieve and update player information after receiving requests from **Network Manager**.
- Has **no direct message routing responsibilities**, aside from database-related operations.
- All requests to the **Server Controller** must go through **Network Manager**.

## Key Components
### 1. Connection Handling
- Listens for new client connections.
- Creates a Virtual Thread for each client connection.
- Manages a list of active connections.
- Works with **Player Handler** for encoding and decoding messages.

### 2. Game Session Management
- Receives game session requests from **Network Manager**.
- Assigns players to **new or existing game sessions**.
- Transfers message routing responsibility to **Game Session Manager** once a session is created.
- Removes players from game sessions once they finish playing.
- Returns players to **Network Manager** after a session concludes.
- Handles disconnections by either **pausing a session** (if reconnecting soon) or **ending it**.

### 3. Database Integration
- Stores **match history** and updates leaderboards after games.
- Handles **authentication validation** by checking login credentials.
- Saves and retrieves **player reconnection data**.
- Only interacts with the database when a request is sent via **Network Manager**.

## Functionality Breakdown
| Function Name       | Description |
|--------------------|-------------|
| `startServer()`    | Initializes the server and listens for client connections. |
| `handleClient(Socket client)` | Assigns a Virtual Thread to each connected client and listens for messages. |
| `createGameSession(Player p1, Player p2)` | Creates a new game session when two players match. |
| `assignToSession(Player player, GameSession session)` | Moves a player into an active game session. |
| `removeFromSession(Player player)` | Removes a player from their game session after it ends. |
| `returnToNetworkManager(Player player)` | Sends a player back to Network Manager after their session ends. |
| `handleDisconnection(Player player)` | Manages player reconnection or removes them from the game. |
| `updateDatabaseAfterGame(GameSession session)` | Updates the database with match history and leaderboard rankings. |

## Message Flow Example
1. **Player A connects to the server.**
2. **Server assigns a Virtual Thread and a Player Handler to handle Player A.**
3. **Player A sends a request to join a game via Player Handler.**
4. **Network Manager receives the request and forwards it to Server Controller.**
5. **Server Controller checks for an available opponent and creates a Game Session.**
6. **Game Session Manager takes over message routing for players in the session.**
7. **Game completes, and the Server Controller removes players from the session.**
8. **Server Controller returns players to Network Manager.**
9. **Server Controller updates the database with match history.**
