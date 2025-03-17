# Server Controller - Design Plan (Updated)

## Overview
The **Server Controller** acts as the central hub for managing game sessions and coordinating core server operations for the online multiplayer board game platform. It handles game session creation, player assignment, disconnections, and database interactions. Importantly, connection listening is delegated to the **Connection Manager**, allowing the Server Controller to focus solely on higher-level game logic and management tasks. Additionally, the Server Controller keeps track of every virtual thread associated with player connections and game sessions to manage lifecycle events and potential cleanup.

## Responsibilities
- **Game Session Management:**
    - Create and manage game sessions.
    - Assign players to game sessions upon request.
    - Remove players from sessions once the game is complete.
    - Return players to the Network Manager after a game session ends.

- **Player Disconnections & Reconnections:**
    - Handle player disconnections by removing them from active sessions.
    - Manage player reconnections to either resume or safely terminate sessions.

- **Database Integration:**
    - Update match history and leaderboards after games.
    - Validate authentication and retrieve player data when requested via the Network Manager.

- **Thread Management:**
    - Keep track of every virtual thread for each player connection (via Player Handlers).
    - Monitor and manage virtual threads running game sessions.

- **Delegation and Coordination:**
    - Collaborate with the **Connection Manager** for new client connections.
    - Process higher-level game logic while leaving low-level I/O tasks (such as connection handling) to specialized components.

## Key Components
### 1. Delegation from Connection Manager
- **Integration with Connection Manager:**  
  The Connection Manager accepts new client connections and delegates the corresponding Player Handlers to the Server Controller.

### 2. Game Session Management
- **Session Creation:**  
  Receives requests (e.g., "JOIN_GAME") from the Network Manager or Player Handlers, and pairs players to create new game sessions.

- **Session Lifecycle:**  
  Manages active game sessions, ensuring that game state is maintained, players are assigned appropriately, and sessions are terminated correctly.

### 3. Database Integration
- **Match History & Leaderboards:**  
  Interacts with the Database Connector to update game results and player statistics after a session ends.

- **Authentication & Reconnection Data:**  
  Validates player credentials and manages reconnection information when needed.

### 4. Thread Management
- **Player Threads:**  
  Maintains a list (or mapping) of virtual threads for every Player Handler. This enables coordinated shutdown, error handling, or re-assignment if needed.

- **Session Threads:**  
  Keeps track of virtual threads for active game sessions, allowing the Server Controller to monitor session lifecycles and perform cleanup after a session ends.

## Functionality Breakdown
| Function Name                              | Description |
|--------------------------------------------|-------------|
| `createGameSession(Player p1, Player p2)`  | Creates a new game session when two players are matched and registers its virtual thread. |
| `assignToSession(Player player, GameSession session)` | Assigns a player to an active game session and updates their Player Handler accordingly. |
| `removeFromSession(Player player)`         | Removes a player from their game session after the session ends, and updates the corresponding thread tracking. |
| `returnToNetworkManager(Player player)`    | Delegates the player back to the Network Manager once their session concludes. |
| `handleDisconnection(Player player)`       | Manages player disconnections, updating thread tracking and session state as needed. |
| `updateDatabaseAfterGame(GameSession session)` | Updates the database with match history and leaderboard data following a completed game session. |
| `registerPlayerThread(Thread t, Player player)` | Registers the virtual thread handling a player connection for lifecycle management. |
| `registerSessionThread(Thread t, GameSession session)` | Registers the virtual thread running a game session for monitoring and cleanup. |

## Message Flow Example
1. **New Connection Delegation:**  
   The **Connection Manager** accepts a new client connection, creates a Player Handler on a Virtual Thread, registers that thread with the Server Controller, and forwards the Player Handler.
2. **Session Assignment:**  
   Upon receiving a Player Handler, the Server Controller processes the "JOIN_GAME" request, pairs players, and creates a game session while registering the session's thread.
3. **Game Session Management:**  
   The Server Controller monitors the lifecycle of both player and session threads. When a session concludes, it removes the threads from its tracking list and returns players to the Network Manager.
4. **Database Update:**  
   Finally, the Server Controller updates match history and leaderboard data by interacting with the Database Connector.
