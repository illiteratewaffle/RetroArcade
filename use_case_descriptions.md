# Use Case Descriptions
////////////Template////////////////
**Use case:**
**Iteration:**
**Primary Actor:**
**Goal in context:**
**Preconditions:**
**Trigger:**
**Scenario:**
    1.
**Post conditions:**
**Exceptions:**
    1.
**Priority:**
**When available:**
**Frequency of use:**
**Channel to actor:**
**Secondary actors:**
**Channel to secondary actors:**
**Open issues:**
///////////////////////////////////////

## Authentication and Profile Team
**Use case:** Select Create an Account

**Iteration:** 1

**Primary Actor:** New (Unregistered) Player

**Goal in context:** Begin the process of registering an account for a new profile to use Online Multiplayer board Game platform.

**Preconditions:** Program is running on the Main Menu Screen not currently logged into an existing profile.

**Trigger:** The new unregistered player wants to register for a new profile account to use the service.

**Scenario:**
1. The user clicks the Create a New Account Button.
2. The user is taken to a new Account Creation Screen.

**Post conditions:** The new Account Creation Screen prompts the user to fill in text boxes to input an E-mail Address,
a username, and a password. There is a button at the end to confirm and complete registration.

**Exceptions:**
1. A Profile is already currently logged in, so Create an Account is unavailable.
2. Program malfunctions and does not properly load the new Account Creation Screen.
3. The Create a New Account Button is unresponsive when selected.
4. The Create a New Account Button does not appear.

**Priority:** High. The creation of Profiles is required to properly track players' game history, leaderboard rankings, 
player stats and win/loss ratios for appropriate match making. Profiles also allow users to find their friends' profiles
to play against.

**When available:** On the Main Menu Screen near Log in when no profile is currently logged into the program currently.

**Frequency of use:** Once for every Profile created on the platform.

**Channel to actor:** Using a mouse to click the button on the screen.

**Secondary actors:** N/A

**Channel to secondary actors:** N/A

**Open issues:** N/A


**Use case:** Add E-mail Address

**Iteration:** 1

**Primary Actor:** New (Unregistered) Player

**Goal in context:** Add E-mail to associate with the account.

**Preconditions:** The program is on the Account Registration page.

**Trigger:** The user is prompted to enter an e-mail address that is not associated with any current account.

**Scenario:**
1. Click on text box marked e-mail.
2. User types in e-mail address.
3. Press Enter.
4. Once submitted, verified to be a unique e-mail address on the server and added to user profile.

**Post conditions:** Profile has a unique e-mail associated to it.

**Exceptions:**
1. Invalid e-mail address.
2. E-mail address is already associated with another profile.

**Priority:** Medium Priority. Often a requirement of many account creation processes, which allows the company a means of
communication with account holder for news and account changes. May also allow for account verification and permit access if password
forgotten. A username is also included, which could be used as the only means of profile identification for log in purposes
without an e-mail address.

**When available:** Available when on the Register Account Information Fill-in Page.

**Frequency of use:** Once for every profile created on the platform.

**Channel to actor:** Keyboard and mouse

**Secondary actors:** Profile Server

**Channel to secondary actors:** Entered e-mail is searched up on the Profile server. An error is thrown if the e-mail address
is already associated with an already created profile.

**Open issues:** N/A


**Use case:** Create Username

**Iteration:** 1

**Primary Actor:** New (Unregistered) Player

**Goal in context:** Create a username associated with the players account.

**Preconditions:** The program is on the Account Registration page, and ready to add a username.

**Trigger:** The user is prompted to enter a username that is not associated with any current account.

**Scenario:**
1. Click on the text box marked username.
2. User types in the username.
3. Press Enter.
4. Once submitted, verified to be a unique username in the system and then added to user profile.

**Post conditions:** Profile has a unique username attached to it .

**Exceptions:**
1. Username is already associated with another profile.
2. Invalid username.

**Priority:** High Priority. The username is the main reference to the users account and is specific to each individual account. 

**When available:** Available on the Register Account Information page.

**Frequency of use:** Once for every profile that is created on the platform.

**Channel to actor:** Keyboard and mouse

**Secondary actors:** Profile server

**Channel to secondary actors:** Entered username is searched on the Profile server and stored if valid. An error is thrown 
if the username is already associated with an already existing profile.

**Open issues:** Any length/character restrictions for a username (number of characters, no spaces, etc.)? Prevention of 
offensive usernames?


**Use case:** Create Password

**Iteration:** 1

**Primary Actor:** New (Unregistered) Player

**Goal in context:** Create a password associated with the players account.

**Preconditions:** The program is on the Account Registration page, and ready to add a password.

**Trigger:** The user is prompted to create a password for the account.

**Scenario:**
1. Click on the text box marked password.
2. User types in the password.
3. Press Enter.
4. Once submitted, the password is added to the user profile

**Post conditions:** Profile has a password attached to it. 

**Exceptions:**
1. Invalid password.

**Priority:** High Priority. The password is needed for account security, along with signing in to a users account.

**When available:** Available on the Register Account Information page.

**Frequency of use:** Once for every profile that is created on the platform.

**Channel to actor:** Keyboard and mouse

**Secondary actors:** Profile server

**Channel to secondary actors:** Entered password is stored on the Profile server.

**Open issues:** Specific requirements for a password to be valid (at least 8 characters, a number, etc.)? Password confirmation step 
to ensure it is entered correctly?


**Use case:** Search for Other Users

**Iteration:** 1

**Primary Actor:** Player

**Goal in context:** Search username of another player to view their profile.

**Preconditions:** Player is logged in.

**Trigger:** The player wants to find a profile of another player to view current status, rank, and recent matches.

**Scenario:**
1. Player clicks on Profile Search box.
2. Player types in username using a keyboard.
3. Player clicks enter.
4. Profile database is searched and returns usernames that contain the searched term. The highest results are profiles that 
most match the search term. 

**Post conditions:** Usernames of profiles that match search input are displayed. The player may select one to view the profile.

**Exceptions:**
1. Error searching the database not terminating or returning any results.
2. No results match the search term.

**Priority:** High priority. The platform is to play multiplayer games, so it is important that players can find their friends'
profiles to play against them and see profile ranks.

**When available:** Available when logged in when not in a game.

**Frequency of use:**

**Channel to actor:** Keyboard

**Secondary actors:** Profile database server

**Channel to secondary actors:** Search algorithm through all profile usernames in the database.

**Open issues:** N/A

## GUI Team

**Use case:** Allow players to customize/edit their profile.

**Iteration:** 1

**Primary Actor:** Player

**Goal in context:** The player can update a banner and profile picture.

**Preconditions:** The player has an active account.

**Trigger:** The player navigates to their profile page.

**Scenario:**
1. The player selects "Edit Profile."
2. The UI displays fields for profile picture and banner. 
3. The player updates either of these fields and saves changes. 
4. The system verifies the updates and applies them.

**Post conditions:** The player's profile is updated successfully. The changes are viewable by the player and other players.

**Exceptions:**
1. 

**Priority:** Medium. An asset to build a multiplayer community, but not detrimental to system function.

**When available:** 2nd or 3rd project iteration.

**Frequency of use:** Occasional. Once the player has created their profile and wishes to customize it.

**Channel to actor:** In the player’s profile page, under the settings menu.

**Secondary actors:** N/A

**Channel to secondary actors:** N/A

**Open issues:**  Should players be able to upload their own profile pictures/banners, or should they only be able to choose from a predefined selection?


**Use case:** Login to existing account

**Iteration:** 1

**Primary Actor:** Player

**Goal in context:** The player is able to log into their account securely in order to access their profile and play games

**Preconditions:** The player has an existing account and the system’s authentication system is responsive

**Trigger:** The player selects the “login” button on the system’s home page

**Scenario:**
1. A login screen is displayed to player prompting them to enter their username and password
2. The player enters their username and password and hits enter 
3. The system verifies the player’s credentials using the authentication database 
4. The system verifies the player’s credentials and redirects them to the page displaying their profile 
5. If the username and/or password entered by the user are invalid, the screen displays an error message and the user is prompted to re-enter their credentials, or to create an account if they do not have an existing one

**Post conditions:** The player has successfully logged in and now has access to their profile, leaderboard, and the systems various game features

**Exceptions:**
1. Username and/or password not correct. System displays error message and prompts user to try again 
2. Player has forgotten password. Screen displays “forgot password” option, enabling player to reset their password through email

**Priority:** High. Essential in order for players to access the gaming platform and its various features, some of which include playing games, checking leaderboards, and viewing their profile

**When available:** Second or third project iteration.

**Frequency of use:** Most, if not every use. Players will need to log in every time they attempt to access the platform after closing it.

**Channel to actor:** Via the touch screen interface (login screen on the homepage)

**Secondary actors:** Authentication system/database

**Channel to secondary actors:** User database

**Open issues:**
1. If the login page provides the option to access the system through third-party logins, how will this be presented on the screen? 
2. How will error messages be displayed on the screen?



**Use case:** Allow players to communicate during gameplay using in-game chat

**Iteration:** 1

**Primary Actor:** Player

**Goal in context:** Player can communicate with opponents during a match

**Preconditions:** The player is actively playing a multiplayer game

**Trigger:** The player selects the chat icon on the game interface during a match

**Scenario:**
1. Player selects the “chat” button displayed during the match 
2. A chat bar is displayed on the screen, through which players are enabled to type the message they wish to send to their opponents 
3. Player types a message to opponent 
4. Player selects “send” option on the screen to deliver their message 
5. Player may also receive messages from opponents

**Post conditions:** The player has communicated with their opponent during an ongoing multiplayer game

**Exceptions:**
1. If there is an error that prevents communication, the system will display an error message
2. Any inappropriate messages sent by a player will be filtered by the system and will not be received by opponent

**Priority:** Medium. May increase player’s sense of community and enhance their experience using the system, but not required in order to play games

**When available:** Second or third iteration

**Frequency of use:** Somewhat frequent. Different players may choose to utilize this functionality more or less frequently based on personal preference.

**Channel to actor:** The interface available to users during a game match

**Secondary actors:** N/A

**Channel to secondary actors:** N/A

**Open issues:**
1. When a player receives a message from an opponent, will the system automatically open the chat bar on the receiving player’s end, or will they receive a notification which prompts them to open the chat bar on their own (if they choose to do so)?
2. What message/warning will the system display to users who write inappropriate/offensive messages?


**Use case:** Allow player to choose game from game library

**Iteration:** 1

**Primary Actor:** Player

**Goal in context:** Player is able to view games offered by the platform and select one to play

**Preconditions:** The player has logged in

**Trigger:** After submitting login credentials, the player is redirected to the main game page

**Scenario:**
1. On the main game page screen, the system displays a list of games available for the player to choose from
2. When requested by the player, the system displays a brief description for each game selection on the game library page interface 
3. Player selects desired game from the list 
4. Player is redirected to the game play screen

**Post conditions:** The player has selected a game to play and has now been redirected to the screen where they will play the chosen game

**Exceptions:**
1. In the case of a server issue, the system will display an error message to the user to try again later
2. If there are no opponents available to match, the system will display this message to the user and request that they wait for a match

**Priority:** High. Selecting games is necessary in order to be able to play games using the system.

**When available:** Second or third iteration

**Frequency of use:** Frequent. Players will need to select a game each time they wish to start playing a game or enter a new match

**Channel to actor:** Game page screen

**Secondary actors:** N/A

**Channel to secondary actors:** N/A

**Open issues:**
1. How will information be displayed for each game? Will the player have the option to choose to view instructions on how to play if needed?


**Use case:** Allow user to view their game history

**Iteration:** 1

**Primary Actor:** Player

**Goal in context:** By viewing their profile, the player can access their past game history (wins, losses, playing statistics) and rankings

**Preconditions:** The user has logged into their account on the system

**Trigger:** The user selects the “profile” button on the home game page

**Scenario:**
1. After player selects the profile button on the home screen, the system displays a list of past games played by the logged in player and any associated statistics 
2. Player selects specific games played to view more specific details of a given match, such as ranking, which is displayed by the system

**Post conditions:** The player has viewed their game history

**Exceptions:**
1. If the user has not played any games, the system will display a message to the user redirecting them back to the home game page to select a game

**Priority:** Medium priority. While users may be curious about their game history/status, it is not essential in order for the gameplay features of the system to run

**When available:** Second or third iteration.

**Frequency of use:** Somewhat frequent. Some players may occasionally wish to view their game history out of curiosity, or to improve their gameplay skills.

**Channel to actor:** Via the touch screen interface (game selection homepage)

**Secondary actors:** N/A

**Channel to secondary actors:** N/A

**Open issues:**
1. Other player’s have access to view some of their opponent’s game history (i.e. ranks, current status, recent matches). Should there be an option presented to users allowing them to make this information private?

**Use case:** Allow players to access instructions before game has started

**Iteration:** 1

**Primary Actor:** Player

**Goal in context:**The player can be well informed before starting the game.

**Preconditions:**The player has chosen a game.

**Trigger:** The player presses the information button on the multiplayer pop up window.

**Scenario:**
1. The player selects a game (connect 4, checkers, tic tac toe) on game page
2. The player is taken to a pop up window where they can choose a match or create their own.
3. If the player already knows the rules, the player chooses a preferred match.
4. If the player has never played the game before or needs a refresher, the player will press the information button (i on the left corner) .
5. New pop up will show the instructions of selected game

**Post conditions:** The player is informed of all the rules. The player can now start the game.

**Exceptions:**
    1.

**Priority:**  Medium. Not all players will need to check the game description as some may have memorized the instructions

**When available:** 2nd or 3rd project iteration.

**Frequency of use:** Occasional. If it is a new player or a player needs a refresher on game rules.

**Channel to actor:**In the multiplayer join match window, under the information icon “i".

**Secondary actors:** N/A

**Channel to secondary actors:** N/A

**Open issues:**  Should the instructions be shown every time you first log into the game or just make it optional from the start ?

**Use case:** Allow players to access instructions after game has started.

**Iteration:** 1

**Primary Actor:** Player

**Goal in context:**The player can go back to the instructions after starting the match.

**Preconditions:**The player has started a match.

**Trigger:** The player presses the information button on the active game page.

**Scenario:**
1. The player selects a game (connect 4, checkers, tic tac toe) on the game page.
2. The player is taken to a pop up window where they can choose a match or create their own.
3. The player starts the game and forgets the game instructions.
4. The player presses the information button on the left corner.
5. New pop up window will have selected game instructions.


**Post conditions:** The player has selected a match. The player starts playing and forgets a step.

**Exceptions:**
    1.

**Priority:** Medium. Not all players will need to check the game description as some may have memorized the instructions.

**When available:** 2nd or 3rd project iteration.

**Frequency of use:** Occasional. If it is a new player or a player needs a refresher on game rules.

**Channel to actor:** In the active game window, under the information icon “i” on the left corner.

**Secondary actors:** N/A

**Channel to secondary actors:** N/A

**Open issues:** N/A

## Game Logic Team

**Use case:** Allow Player To Voluntarily Quit The Game.

**Iteration:** 1

**Primary Actor:** Player

**Goal in context:** The player will be able to voluntarily leave/quit their current game session. This will result in a loss for the player that left, and a win for the other player.

**Preconditions:** The player has joined a match, and the game is ongoing.

**Trigger:** The player wishes to leave the ongoing game.

**Scenario:**
1. The player begins playing any of the available games (Connect 4, Tic Tac Toe, Checkers).
2. The game begins and the player wishes to leave the ongoing game.
3. The player is warned, before leaving, that he will be given a loss for leaving the match.
4. The player makes the choice to leave the game.
5. The game is ended and the leaving player is given a loss, while the other player is awarded a win.

**Post conditions:** The player has voluntarily left the game, and the game is ended.

**Exceptions:** N/A

**Priority:** Medium-High Priority. Is an almost essential feature for many players to have available to them. Although it is not essential for gameplay functionality, it is a good feature to provide to our players to ensure they do not feel "trapped" in a game.

**When available:** Second or third iteration.

**Frequency of use:** Somewhat frequent. Some players may wish to leave as a form of conceding to their opponent, others may be under a time crunch, and many other reasons.

**Channel to actor:** Via the main menu interface.

**Secondary actors:** N/A

**Channel to secondary actors:** N/A

**Open issues:**
1. If a player consistently leaves ongoing games, should they receive incrementing forms of punishment? (lower matchmaking priority, temporary matchmaking bans, timeouts, other?)

**Use case:** Update Current Game State

**Iteration:** 1

**Primary Actor:** Game Logic software

**Goal in context:** To update the current state of the game being played to the player in real-time. The game logic should be able to know when a player is ending/beginning their turn. It should also know when a game is over and update the game state to be completed.

**Preconditions:** The player has joined a match, and the game state has initiated.

**Trigger:** Whenever a game is started and a player is designated to make their turn, or whenever the game has concluded.

**Scenario:**
1. A player begins a match from one of the 3 available games, Connect 4, Tic Tac Toe, Checkers.
2. The game state is initiated and a player must take their turn.
3. The game state is updated to ensure the correct player is able to make their turn as designated.
4. The designated player makes their turn.
5. If the game ends due to a win condition being met, go to 7.
6. If the game is still ongoing and it is now the next player's turn, go back to 3.
7. Update the game state to ensure that the game is concluded and no other players can take turns. A winner and loser are assigned and the match concludes.

**Post conditions:** The game state is updated and the game is either initiated, awaiting a player's turn, or completed.

**Exceptions:** N/A

**Priority:** High priority.

**When available:** 2nd or 3rd iteration.

**Frequency of use:** Always. Game state is being constantly updated through gameplay.

**Channel to actor:** Game Logic Java Classes.

**Secondary actors:** N/A

**Channel to secondary actors:** N/A

**Open issues:**
1. If there are network errors or disconnects, how will we handle delayed game status updates? Must prepare for this case to ensure that the game state is appropriately updated when possible for both players.


**Use case:** Provide user with tutorials and/or hints

**Iteration:** 1

**Primary Actor:** Player

**Goal in context:** The player will be able to ask for a tutorial or hint at the beginning or end.

**Preconditions:** The player has started one of 3 games.

**Trigger:** The player clicks on the button for tutorial or hint on the screen.

**Scenario:**
1. The player selects the tutorial or hint button from the game interface.
2. If tutorial is selected, the player is walked through the first few steps of a game and its rules.
3. If a hint is selected, the player will receive a game-appropriate hint that does nudges them in the right direction in relation to the game.
4. The player should be allowed to then close the tutorial/hint and continue playing.

**Post conditions:** The player received a tutorial/hint and continues with the game.

**Exceptions:** 
1. If the player selects a hint but they are in a stalemate, hint may not be provided.

**Priority:** Medium priority. It's not extremely necessary as these games are quite common and popular, you can find the rules anywhere, but some users may not know it well enough and would need a tutorial or hints or both.

**When available:** Between 2nd and 3rd iterations

**Frequency of use:** Somewhat frequent. As mentioned above, some people may need to use it and some don't.

**Channel to actor:** The game's interface.

**Secondary actors:** N/A

**Channel to secondary actors:** N/A

**Open issues:**
1. How would the hints be generated?
2. Should there be a limited number of hints to prevent users from exploiting it?



## Networking Team

**Use case:** Player Joins a Multiplayer Game (client-server Model)

**Iteration:** 1

**Primary Actor:** Player (client)

**Goal in context:** The player selects a game and sends a request to the server to join a game session.

**Preconditions:** 

1. The player is logged into their account.
2. The client is successfully connected to the server.
3. The player is on the game selection screen.
4. The game server is online and responsive.

**Trigger:** The player selects a game and requests to join a session.

**Scenario:**
1. The client and host player's select from list of board games to play.
2. The host player creates a game server/session.
3. The client player sends the host player a request to join the game server.
4. The server receives the request, processes it and sends session details back to the client player.
5. The client receives confirmation and the initial game state loads.

**Post conditions:** The player is successfully connected to a game session through a client-server socket connnection.

**Exceptions:** 

1. The server is unreachable, preventing the player from joining.
2. The client loses connection before the game session is initialized.
3. The game session has reached the player limit.
4. The request is incorrect, or corrupted during connection phase.
5. A timeout threshold is surpassed, where either the server or client do not receive a response within a predefined time.

**Priority:** High priority. Establishing a reliable multiplayer connection is crucial for gameplay.

**When available:** 2nd or 3rd iteration.

**Frequency of use:** Frequent. Everytime player's would like to play against other human opponents.

**Channel to actor:** 

1. GUI interaction.
2. Socket connection for communication with the server.

**Secondary actors:** 

1. Game Server which handles session management and player matchmaking.

**Channel to secondary actors:** 

1. TCP socket communication between client and server for session handling.

**Open issues:**

1. What kind of authentication mechanism should be used for verifying player join requests?

**Use case:** Multiplayer Game State Update (client-server Model) 
 
**Iteration:** 1 
 
**Primary Actor:** Player (client) 
 
**Goal in context:** The player makes an in-game action that is sent to the server. The server then updates the game state and sends the new state to all connected players. 
 
**Preconditions:**  
 
1. The player is logged into their account. 
2. The player is connected to the game. 
3. The client is connected to the server. 
4. The game server is online and responsive. 
 
**Trigger:** The player takes an action in the game 
 
**Scenario:** 
1. The player takes an action in the client. 
2. The client sends the action to the server. 
3. The server game session manager updates the game state. 
4. The server game state synchronizer sends the new game state to all connected clients 
5. The client receives the new game state and replaces the old game state with it. 
 
**Post conditions:** All players receive the updated game state 

**Exceptions:**  
 
1. The server is unreachable, so the action is not sent to the server. 
2. The client loses connection before the game state is synchronized. 
3. A move is invalid or corrupted during the game state update. 
4. A timeout threshold is surpassed, where either the server or client do not receive a response within a predefined time. 
 
**Priority:** High priority. Being able to make moves that all players can see is crucial for multiplayer gameplay
 
**When available:** 2nd or 3rd iteration. 
 
**Frequency of use:** Very Frequent. Every time any player takes an action in a multiplayer game. 
 
**Channel to actor:**  
 
1. GUI interaction. 
2. Socket connection for communication with the server. 
 
**Secondary actors:**  
 
1. Game Server which handles session management and player matchmaking.
2. Other player clients 
 
**Channel to secondary actors:**  
 
1. TCP socket communication between client and server for session handling. 
 
**Open issues:** 

1. How to handle simultaneous moves from different players.
2. How to make sure the game state is updated at the same time for all players 

**Use case:** Server Account Creation (client-server Model)

**Iteration:** 1

**Primary Actor:** Player (client)

**Goal in context:** The player creates a new account by entering their details on the registration screen, their credentials are then stored on the server.

**Preconditions:**

1. The client can be connected to the server.
2. The player is on the registration screen.
3. The game server is online and responsive.

**Trigger:** The player selects the option to register a new account.

**Scenario:**
1. The player enters the required registration details.
2. The client sends the registration information and request to the server.
3. The server checks the validity of the submitted information.
4. The server stores the player's registration info in its database.
5. The server sends confirmation back to the client and connects to the player's account.

**Post conditions:** The player's account is successfully created, and is connected to the server


**Exceptions:**

1. The server is unreachable, so the registration info was not sent to the server.
2. The database is unreachable and cannot store the data.
3. The player's registration information is invalid

**Priority:** High priority. Having an account is necessary to connect to the multiplayer aspects of the game.

**When available:** 2nd or 3rd iteration.

**Frequency of use:** Low. Will be used when a player first begins using the multiplayer aspects of a game

**Channel to actor:**

1. GUI interaction.
2. Socket connection for communication with the server.

**Secondary actors:**

1. Game Server
2. Game Database

**Channel to secondary actors:**

1. TCP socket communication between client and server for session handling.

**Open issues:**

1. How to handle registration information if the server database is full
**Use case:** Player Disconnection & Reconnection

**Iteration:** 1

**Primary Actor:** Player

**Goal in context:** Ensure that if player disconnects from an ongoing game session, their game state is retained, allowing
them to reconnect and resume the match if they return within a certain timeframe.

**Preconditions:**

1. The player is currently connected to an active multiplayer session.
2. The server is online and managing the session.
3. The game session is still ongoing when the disconnection occurs.

**Trigger:** The player's connection is cut due to network issues, system crashes, or manual disconnection.

**Scenario:**

1. The player is currently within a multiplayer game session.
2. The player disconnects unexpectedly due to internet issues, game/system crashes, or manual disconnection.
3. The server detects the player's disconnection and marks the player a disconnected (temporarily).
4. The server retains the player's game state (moves, scores, turn state) for a predetermined amount of time.
5. If the player reconnects:
   - The client sends a reconnection request to the server.
   - The server verifies the player's identity and checks if their previous session is still available,
   - The server restores the player's last game state and re-establishes their connection to the session.

**Post conditions:** If the player successfully reconnects, they resume the game from the last known state.

**Exceptions:**

1. The player does not reconnect within the allocated time, and the game session is forced to continue.
2. If the server goes down before the player reconnects, their game state may be lost.
3. The player reconnects but fails identity verification.
4. If the player repeatedly disconnects and reconnects, the server may impose a limit.

**Priority:** High. Disconnection and reconnection handling is essential for preventing abuse of online system, and ensuring smooth
multiplayer experience.

**When available:** 2nd or 3rd iteration.

**Frequency of use:** Variable. Some player's may rarely disconnect, while others may have unstable connections, or abuse the system.

**Channel to actor:**

1. Player interacts via the game client.
2. Reconnection occurs via the server-client communication.

**Secondary actors:** N/A

**Channel to secondary actors:** N/A

**Open issues:**

1. How long should the server retain the player's game state before removing them from the session?
2. How should the system handle repeated connections and disconnections from players?
3. What security measures are in place to prevent exploitation of disconnect/reconnect logic?
4. How long should the reconnect threshold times be, and should it depend on game type?

**Use case:** Chat Message Handling (client-server model)

**Iteration:** 1

**Primary Actor:** Player (client)

**Goal in context:** The player creates a new account by entering their details on the registration screen, their credentials are then stored on the server.

The player sends a chat message using the chat box. The message is routed through the server and then sent to all other players.

**Preconditions:**

1. The player is logged into their account.
2. The player is connected to the game.
3. The client is connected to the server.
4. The game server is online.
5. The chat box is active

**Trigger:** The player types and sends a chat message

**Scenario:**
1. The player enters the required registration details.
2. The client sends the message to the game server.
3. The server checks the appropriateness of the message.
4. The server sends the chat message to the other connected players.
5. The client receives the message and updated the chat box.

**Post conditions:** All players receive the new chat message

**Exceptions:**

1. The server is unreachable, so the message was not sent to the server.
2. A timeout threshold is surpassed, where either the server or client do not receive a response within a predefined time.
3. The player's message is inappropriate

**Priority:** Low priority. Messaging is secondary to the games core functions.

**When available:** 2nd or 3rd iteration.

**Frequency of use:** Medium. Will be used every time a wants to send a message to other players.

**Channel to actor:**

1. GUI interaction.
2. Socket connection for communication with the server.

**Secondary actors:**

1. Game Server
2. Other player clients

**Channel to secondary actors:**

1. TCP socket communication between client and server for session handling.

**Open issues:**

1. How to handle message moderation
2. How to handle simultaneous messages from different players
3. How to handle very large chat messages
4. How to make sure the chat box is updated for all players 