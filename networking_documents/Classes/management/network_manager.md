# Network Manager - Design Plan

## Overview
The **Network Manager** is responsible for routing all incoming and outgoing messages on the server. It acts as the central communication hub, ensuring that messages are sent to the correct components. The **Server Controller** does not handle direct message routing; instead, all communication must go through the Network Manager first.

## Responsibilities
- Keeps track of all active connections on the server.
- Routes messages between **Player Handlers**, **Server Controller**, and **Game Session Manager**.
- Ensures that messages reach the correct component within the system.
- Manages global message handling before players enter a game session.
- Forwards **database requests** to **Server Controller**.
- Redirects players to their correct session once assigned.
- Handles player disconnections and reconnections at the network level.

## Key Components
### 1. Message Routing
- Receives messages from **Player Handlers** and determines their destination.
- Sends game session-related messages to **Server Controller** (for session management).
- Transfers message routing responsibility to **Game Session Manager** once a player is in a session.
- Manages **non-gameplay messages** (chat, authentication, system commands).

### 2. Connection Management
- Tracks all active player connections.
- Handles players rejoining the server after disconnection.
- Directs players back to **Server Controller** after a game ends.
- Server Controller assigns **Player Handlers** to newly connected players.
- Player Handlers store the **string reference** of Network Manager for routing purposes.
- When a player joins a game session, **Server Controller updates the Player Handler** with the reference to **Game Session Manager**.

### 3. Database Request Forwarding
- Ensures all **database interactions** (e.g., leaderboard updates, match history) are handled through **Server Controller**.
- Forwards player authentication requests to **Server Controller** for validation.
- Retrieves **player reconnection data** when needed.

## Functionality Breakdown
| Function Name                 | Description |
|------------------------------|-------------|
| `routeMessage(PlayerHandler sender, String message)` | Determines where a message should be sent. |
| `sendToServerController(String message)` | Forwards messages that require game session management. |
| `sendToGameSession(String sessionId, String message)` | Routes messages to a specific game session. |
| `handleDisconnection(PlayerHandler handler)` | Manages player disconnections and possible reconnections. |
| `retrievePlayerSession(String playerId)` | Determines if a player should return to a session after reconnecting. |

## Message Flow Example
1. **Player A connects to the server.**
2. **Server Controller assigns Player A a Player Handler.**
3. **Player Handler stores the Network Manager reference for message routing.**
4. **Player A sends a request to join a game.**
5. **Network Manager routes the request to Server Controller.**
6. **Server Controller creates a Game Session and assigns Player A.**
7. **Server Controller updates Player A's Player Handler with the Game Session Manager reference.**
8. **Game Session Manager now handles all message routing for Player A.**
9. **Game completes, and Server Controller returns Player A to Network Manager.**
