# Connection Manager - Design Plan

## Overview
The **Connection Manager** is responsible for handling all incoming client connections. It listens on a specified port and accepts new connections, spawning a Virtual Thread for each connection. Each new connection is wrapped into a Player Handler and then delegated to the Server Controller for further processing. By offloading connection handling, the Connection Manager allows the Server Controller to concentrate solely on game session management and other high-level operations.

## Responsibilities
- **Listen for Incoming Connections:**  
  Open a ServerSocket on a designated port and continuously wait for new client connections.

- **Accept and Delegate Connections:**  
  Accept each incoming connection, create a Virtual Thread, and instantiate a Player Handler. Then, hand off the new connection to the Server Controller for further processing (such as session assignment).

- **Lightweight Thread Management:**  
  Leverage Java Virtual Threads to efficiently manage numerous concurrent connections without blocking the core server logic.

## Key Components
1. **ServerSocket:**  
   The underlying mechanism to listen for new client connections.

2. **Virtual Threads:**  
   Utilizes Java Virtual Threads (via `Thread.startVirtualThread()`) so that each accepted connection is handled on its own lightweight thread.

3. **Player Handler Integration:**  
   Wraps the accepted socket into a Player Handler that will manage client communication.

4. **Delegation to Server Controller:**  
   Forwards newly created Player Handlers to the Server Controller, which acts as the “brain” of the server for game and session management.

## Functionality Breakdown
| Function Name                                | Description |
|----------------------------------------------|-------------|
| `startListening(int port)`                   | Opens a ServerSocket on the specified port and continuously listens for incoming connections. |
| `acceptConnection()`                         | Accepts an incoming client connection, returning the connected Socket. |
| `handleNewConnection(Socket clientSocket)`   | Creates a new Virtual Thread to wrap the connection in a Player Handler and delegates it to the Server Controller. |

## Message Flow Example
1. **Startup:**  
   The Connection Manager starts and calls `startListening()` on the designated port.
2. **Connection Acceptance:**  
   A client initiates a connection; `acceptConnection()` accepts it.
3. **Player Handler Creation:**  
   A virtual thread is spawned to create a new Player Handler for the connection.
4. **Delegation:**  
   The new Player Handler is forwarded to the Server Controller for session assignment and further processing.
5. **Concurrent Processing:**  
   Multiple connections are processed concurrently without blocking the Server Controller's primary responsibilities.
