# Client Networking Method Reference

This document provides usage examples for the `client.networkingMethod(String type, String message, String game, String extra, String extraObject)` method. Each call sends a structured JSON payload to the server to perform a specific action.

---

| Type                  | Purpose                                                   | Example Code                                                                                                     |
|-----------------------|-----------------------------------------------------------|------------------------------------------------------------------------------------------------------------------|
| `chat`                | Send a chat message to another player                     | `client.networkingMethod("chat", "Hello world!", "checkers", "null", "null");`                                   |
| `game-move`           | Send a player's move in a game                            | `client.networkingMethod("game-move", "LEFT4,UP3", "connect4", "null", "null");`                                 |
| `game-move` (win)     | Notify server that the player won the game through a move | `client.networkingMethod("game-move", "E2,E4", "checkers", "Victory", "True");`                                  |
| `error`               | Report an error to the server                             | `client.networkingMethod("error", "Invalid input format", "null", "errorCode", "INVALID_SYNTAX");`               |
| `profile-info-request`| Request profile information of another player             | `client.networkingMethod("profile-info-request", "Requesting profile data", "null", "targetUser", "player123");` |
| `exit-game`           | Inform server that the user exited the game               | `client.networkingMethod("exit-game", "User disconnected", "chess", "null", "null");`                            |
| `login`               | Attempt to log in                                         | `client.networkingMethod("login", "Trying to login again", "null", "username", "exampleUser");`                  |
| `register`            | Attempt to register a new user                            | `client.networkingMethod("register", "Registering new user", "null", "email", "newuser@example.com");`           |

---

### Notes:
- Use `"null"` (as a string) if a parameter does not apply.
- The `game` parameter refers to the context (e.g., "chess", "checkers").
- The `extra` and `extraObject` fields are flexible and can carry game-specific metadata or user identifiers.

