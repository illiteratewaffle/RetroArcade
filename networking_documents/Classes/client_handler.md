# Client Handler - Design Plan

## Overview
The **ClientHandler** is responsible for managing individual client connections 
and handling communication between the client and the Server. 
It processes incoming messages, converts data between JSON format, 
and ensures that responses are correctly sent back to the client. 
The **ClientHandler** works closely with the Player Handler to relay 
messages appropriately.



## Responsibilities
- Handles direct communication with an assigned client.  
- Processes function requests from the client.
- Converts incoming and outgoing data to and from JSON format.
- Sends processed requests to Player Handler for further handling.
- Sends processed messages to client from Player Handler


## Key Components

### 1. Client Communication
- Listens for incoming client messages.
- Sends responses back to the client after processing.


### 2. Request Processing

- Converts data received by client into a structured format (JSON conversion).
- Converts data received by **Player Handler** into a format readable by client.


### 3. **Player Handler** Communication

- Passes processed requests to **Player Handler**.
- Listens for incoming responses from **Player Handler**.


## Functionality Breakdown
| Function Name                       | Description                                                  | 
|-------------------------------------|--------------------------------------------------------------| 
| run()                               | Listens for client messages and processes them.              | 
| processRequest(String request)      | Processes incoming requests from the client.                 | 
| ConvertToJson(Object data)          | Converts given data into a JSON string format.               | 
|  ConvertFromJson(String jsonData)   | Converts a JSON string into a structured function or object. | 
| sendToPlayerHandler(String message) | Forwards a message to the associated Player Handler.         |
| sendResponse(String response)       | Sends a response message back to the client.                 |
| closeConnection()                   | Terminates the client connection safely.                     |

## Message Flow Example
1. **Client A connects to the server.**
2. **ClientHandler establishes a connection with Client A.**
3. **Client A sends a request to perform an action.**
4. **ClientHandler processes the request and converts it to JSON.**
5. **ClientHandler forwards the request to Player Handler.**
6. **Player Handler processes the request and sends a response back.**
7. **ClientHandler receives the response, converts it to JSON, and sends it to Client A.**
8. **If Client A disconnects, ClientHandler closes the connection and cleans up resources.** 

 
 

 