# Client - Design Plan

## Overview
The **Client** class is responsible for managing the player's connection to the server. 
It establishes a network socket, handles communication with the 
**ClientHandler**, and transmits data to the **ClientHandler**. 

## Responsibilities
- Establishes a connection with the **ClientHandler**.
- Sends messages to the **ClientHandler** along with the **PlayerID**.
- Receives and processes responses from the **ClientHandler**.
- Ensures proper resource management and closes connections when needed.
- Implements a main function to initialize the client and handle networking operations.

## Key Components
### 1. Client Communication
- Establishes a connection to the **ClientHandler**.
- Sends the **PlayerID** along with each message to the **ClientHandler**.
- Handles messages between client and **ClientHandler**.

### 2. Message Handling
- Sends messages to the **ClientHandler** via `SendMessageToHandler(String message)`.
- Waits for and returns responses from the **ClientHandler** using `ReturnMessage()`.

### 3. Connection Management
- Manages and closes network resources using `main(String[] args)` and `CloseEverything()`.
- Ensures the connection is properly closed upon errors or completion.

## Functionality Breakdown
| Function Name                                                                                  | Description                                                                                                    |
|------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------|
| `SendMessageToHandler(String message)`                                                         | Sends a message along with the PlayerID to the ClientHandler and returns the response.                         |
| `ReturnMessage()`                                                                              | Waits for and retrieves a response message from the server.                                                    |
| `CloseEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter)` | Closes the socket and buffered streams to release resources.                                                   |
| `main(String[] args)`                                                                          | Entry point for the client application. Initializes connection, retrieves PlayerID, and handles communication. |

## Message Flow Example
1. **Client starts and connects to the server at port `5050`.**
2. **Server assigns a `PlayerID` and sends it to the Client.**
3. **Client initializes with the received `PlayerID`.**
4. **Client reads a message from the input stream.**
5. **Client sends the message to the ClientHandler along with the `PlayerID`.**
6. **Client waits for a response from the ClientHandler.**
7. **Client receives the response and processes it.**
8. **If an error occurs or the connection ends, the Client closes all resources.**

