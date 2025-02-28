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
