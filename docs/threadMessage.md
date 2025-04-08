# Thread Message Types Documentation

This document describes the various message types used for inter-thread communication within the system. Every message is constructed as a `Map<String, Object>` using the `ThreadMessage` class. Each message **must** include a `"type"` key to identify the message type and other required keys as specified below. All keys listed below are mandatory and include the expected data type for their associated value.

This documentation is intended to help developers maintain or extend the messaging system, ensuring consistency when constructing, parsing, and handling messages.

---

## Message Format Overview

Every thread message follows this basic structure:
- **`type`** (String): Identifies the message type.
- **Other keys**: Specific to the message type as described in the table below. Each key includes its expected data type.

---

## Message Types

| **Message Type**          | **Required Keys (Key: Data Type)**                                      | **Description**                                                                                                                                                                          |
|---------------------------|-------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **error**                 | `type`: String, `message`: String                                       | Indicates an error. The `message` key holds a descriptive error message (e.g., authentication failure error details).                                                                    |
| **login**                 | `type`: String, `username`: String, `password`: String                  | Used during authentication when a client logs in.                                                                                                                                        |
| **register**              | `type`: String, `username`: String, `password`: String, `email`: String | Used during client registration. Contains the necessary credentials and email address for the new user.                                                                                  |
| **enqueue**               | `type`: String, `game-type`: Integer                                    | Sent by a client to request joining the matchmaking queue. The `game-type` value indicates the game mode (e.g., 0 for Tic Tac Toe, 1 for Connect Four, etc.).                            |
| **send-friend-request**   | `type`: String, `ID`: Integer                                           | Initiates a friend request. The `ID` key specifies the recipient's identifier.                                                                                                           |
| **accept-friend-request** | `type`: String, `ID`: Integer                                           | Accepts a friend request from another user. The `ID` key specifies the sender's identifier.                                                                                              |
| **send-game-request**     | `type`: String, `ID`: Integer, `game-type`: Integer                     | Sent to player handler to command an invite to another client for a game session. The `ID` key holds the recipient's identifier and `game-type` specifies the type of game to be played. |
| **game-request**          | `type`: String, `ID`: Integer, `game-type`: Integer                     | Sent by sendGameRequest to another player handler to invite that client to a friend game. Used as the input for acceptGameRequest.                                                       |
| **chat**                  | `type`: String, `message`: String                                       | Used for in-game chat messages. The `message` key holds the chat text.                                                                                                                   |
| **disconnection**         | `type`: String                                                          | Indicates that a client has disconnected from the server. No other keys are required.                                                                                                    |
| **game**                  | `type`: String                                                          | A generic type for in-game actions (such as moves). Additional keys will be defined by game-specific protocols.                                                                          |


---

## Guidelines for Message Construction

- **Mandatory Type Field:** Always include the `"type"` key to indicate the message type.
- **Include All Required Keys:** For each message type, include every key listed above with the specified data type.
- **Consistency:** Use the exact key names and data types as defined to ensure reliable parsing and handling across the system.
- **Updating Documentation:** Whenever new message types are introduced or existing ones are modified, update this document accordingly.

---

*Keep this document updated as the project evolves to ensure consistent and robust inter-thread communication across the system!*


