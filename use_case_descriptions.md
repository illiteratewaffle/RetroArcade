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
1. The unregistered player clicks the Create a New Account Button.
2. The unregistered player is taken to a new Account Creation Screen.

**Post conditions:** The new Account Creation Screen prompts the unregistered player to fill in text boxes to input an E-mail Address,
a username, and a password. There is a button at the end to confirm and complete registration.

**Exceptions:**
1. A Profile is already currently logged in, so Create an Account is unavailable.
2. Program malfunctions and does not properly load the new Account Creation Screen.
3. The Create a New Account Button is unresponsive when selected.
4. The Create a New Account Button does not appear.

**Priority:** High. The creation of Profiles is required to properly track players' game history, leaderboard rankings, 
player stats and win/loss ratios for appropriate match making. Profiles also allow players to find their friends' profiles
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

**Trigger:** The unregistered player is prompted to enter an e-mail address that is not associated with any current account.

**Scenario:**
1. Click on text box marked e-mail.
2. Unregistered player types in e-mail address.
3. Press Enter.
4. Once submitted, verified to be a unique e-mail address on the server and added to profile.

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


**Use case:** Verify E-mail Address

**Iteration:** 1

**Primary Actor:** Profile Server

**Goal in context:** Check if entered e-mail already is not associated with an already existing profile.

**Preconditions:** An e-mail has been submitted from either Create an Account page Add E-mail Address or from Update E-mail Address.

**Trigger:** E-mail Address is submitted to server.

**Scenario:**
1. Profile Server checks HashSet profileEmailSet if it contains the requested e-mail. 
2. Returns true if the HashSet does not contain the e-mail address, indicating a valid e-mail address allowing Player to proceed.
3. Returns false if the HashSet already contains the e-mail address, indicating that e-mail is not valid due to the e-mail
address being already associated with another profile.

**Post conditions:** Returns back if the E-mail address is valid or invalid.

**Exceptions:**
1. An Empty string is submitted by the player.

**Priority:** High priority, A profile needs to have a unique identifier through the e-mail address, so an e-mail cannot
be used by multiple profiles.

**When available:** When Adding to a new Profile or Updating an E-mail Address of an existing Profile.

**Frequency of use:** Not very frequently. At least once for every created profile, with un frequent additional calls for e-mail address
updates.

**Channel to actor:** profileEmailSet HashSet

**Secondary actors:** N/A

**Channel to secondary actors:** N/A

**Open issues:** N/A


**Use case:** Create Username

**Iteration:** 1

**Primary Actor:** New (Unregistered) Player

**Goal in context:** Create a username associated with the players account.

**Preconditions:** The program is on the Account Registration page, and ready to add a username.

**Trigger:** The player is prompted to enter a username that is not associated with any current account.

**Scenario:**
1. Click on the text box marked username.
2. Player types in the username.
3. Press Enter.
4. Once submitted, verified to be a unique username in the system and then added to player profile.

**Post conditions:** Profile has a unique username attached to it .

**Exceptions:**
1. Username is already associated with another profile.
2. Invalid username.

**Priority:** High Priority. The username is the main reference to the players account and is specific to each individual account. 

**When available:** Available on the Register Account Information page.

**Frequency of use:** Once for every profile that is created on the platform.

**Channel to actor:** Keyboard and mouse

**Secondary actors:** Profile server

**Channel to secondary actors:** Entered username is searched on the Profile server and stored if valid. An error is thrown 
if the username is associated with an already existing profile.

**Open issues:** Any length/character restrictions for a username (number of characters, no spaces, etc.)? Prevention of 
offensive usernames?


**Use case:** Create Password

**Iteration:** 1

**Primary Actor:** New (Unregistered) Player

**Goal in context:** Create a password associated with the players account.

**Preconditions:** The program is on the Account Registration page, and ready to add a password.

**Trigger:** The player is prompted to create a password for the account.

**Scenario:**
1. Click on the text box marked password.
2. Player types in the password.
3. Press Enter.
4. Once submitted, the password is added to the player profile

**Post conditions:** Profile has a password attached to it. 

**Exceptions:**
1. Invalid password.

**Priority:** High Priority. The password is needed for account security, along with signing in to a players account.

**When available:** Available on the Register Account Information page.

**Frequency of use:** Once for every profile that is created on the platform.

**Channel to actor:** Keyboard and mouse

**Secondary actors:** Profile server

**Channel to secondary actors:** Entered password is stored on the Profile server.

**Open issues:** Specific requirements for a password to be valid (at least 8 characters, a number, etc.)? Password confirmation step 
to ensure it is entered correctly?


**Use case:** Add New Profile to Profile Database 

**Iteration:** 1

**Primary Actor:** New (Unregistered) Player

**Goal in context:** Create new player account and add it to the Profile Database.

**Preconditions:** The program is on the Account Registration Page with all information entered, and ready to finalize profile.

**Trigger:** The player initiates finalized registration of the account.

**Scenario:**
1. The player clicks complete to finalize account registration.
2. The system validates the entered information.
3. If valid, the system creates a new profile.
4. The profile is stored in the Profile Database.
5. The system confirms successful completion of profile.

**Post conditions:** Profile is created and added to the Profile Database

**Exceptions:**
1. Any invalid fields.
2. Server issues.

**Priority:** High Priority. Essential to creating a new profile allowing players to join the server.

**When available:** Available on the Account Registration page.

**Frequency of use:** Once for every profile created on the server.

**Channel to actor:** Keyboard and mouse

**Secondary actors:** Profile server

**Channel to secondary actors:** Finalized account is stored on the Profile server.

**Open issues:** N/A


**Use case:** Change E-mail Address

**Iteration:** 1

**Primary Actor:** Existing Player

**Goal in context:** Change the e-mail address associated with the players current account. 

**Preconditions:** The program is on the Edit Information page, and ready to change the e-mail.

**Trigger:** The player initiates changing the e-mail associated with the account.

**Scenario:**
1. Player selects the option to change the e-mail.
2. Click on the text box marked e-mail.
3. Player types in the new e-mail address.
4. Press Enter. 
5. Once submitted, verified to be a unique e-mail address on the server and added to player profile.

**Post conditions:** Profile is updated to have the new e-mail address associated with the account.

**Exceptions:** 
1. Invalid e-mail address.
2. E-mail address is already associated with another profile.

**Priority:** Medium Priority. Not essential for profile function but may add to player experience.

**When available:** Available on the Edit Information page.

**Frequency of use:** Low, only used when player wants to change their username.

**Channel to actor:** Keyboard and mouse

**Secondary actors:** Profile server

**Channel to secondary actors:** New e-mail entered is searched up on the Profile server. An error is thrown if the e-mail address
   is associated with an already created profile.

**Open issues:** Limits to number of e-mail changes?


**Use case:** Change Username

**Iteration:** 1

**Primary Actor:** Existing Player

**Goal in context:** Change a username associated with the players current account.

**Preconditions:** The program is on the Edit Information page, and ready to change the username.

**Trigger:** The player initiates changing the username for the account.

**Scenario:**
1. Player selects the option to change the username.
2. Click on the text box marked username.
3. Player types in the new username.
4. Press Enter.
5. Once submitted, verified to be a unique username in the system and then added to player profile to replace old username.

**Post conditions:** Profile is updated to have the new unique username attached to it.

**Exceptions:**
1. Username is already associated with another profile.
2. Invalid username.

**Priority:** Medium Priority. Not essential for profile function but may add to player experience. 

**When available:** Available on the Edit Information page.

**Frequency of use:** Low, only used when player wants to change their username. 

**Channel to actor:** Keyboard and mouse

**Secondary actors:** Profile server

**Channel to secondary actors:** New username is searched on the Profile server and stored if valid. An error is thrown
   if the username is associated with an already existing profile.

**Open issues:** Limits to amount of username changes? Any restrictions to the username itself?


**Use case:** Change Password

**Iteration:** 1

**Primary Actor:** Existing Player

**Goal in context:** Change the password associated with the players current account.

**Preconditions:** The program is on the Edit Information page, and ready to change the password.

**Trigger:** The player initiates changing the password for the account.

**Scenario:**
1. Player selects the option the change the password.
2. Click on the text box marked password.
3. Player types in the new password.
4. Press Enter.
5. Once submitted, verified to be a unique password in the system and then added to player profile to replace the old password.

**Post conditions:** Profile is updated to have the new password attached to it.

**Exceptions:**
1. Invalid password.

**Priority:** Medium Priority. Not essential for profile function but may add to player experience. 

**When available:** Available on the Edit Information page. 

**Frequency of use:** Low, only used when player wants to change their password.

**Channel to actor:** Keyboard and mouse

**Secondary actors:** Profile server

**Channel to secondary actors:** New entered password is stored on the Profile server.

**Open issues:** Password confirmation step to ensure correct entry? Restrictions to the password itself?

**Use case:** Forgot Username

**Iteration:** 1

**Primary Actor:** Existing Player

**Goal in context:** To recover the username associated with a players account.

**Preconditions:** The player has an account with an email attached to verify account owner.

**Trigger:** The player selects the forgot username option on the login screen.

**Scenario:**
1. Player clicks the Forgot Username button on the login page.
2. Clicks on the text box marked e-mail.
2. The player enters the e-mail associated with the account.
3. Verifies email associated with the account.
4. If valid, displays the username associated with the e-mail.

**Post conditions:** Player recovers the username associated with the account and is able to complete login.

**Exceptions:**
1. Invalid e-mail.
2. No account matching the e-mail.

**Priority:** High Priority. Essential for account recovery.

**When available:** Available on the login page.

**Frequency of use:** Low, only used when player forgets their username. 

**Channel to actor:** Keyboard and mouse

**Secondary actors:** Profile server

**Channel to secondary actors:** Username is found on the profile server based on the accounts e-mail address.

**Open issues:** Lock out after too many failed entries? Security issues?


**Use case:** Forgot Password

**Iteration:** 1

**Primary Actor:** Existing Player

**Goal in context:** To reset the password associated with a players account when attempting to log in.

**Preconditions:** The player has an existing account with an email attached for verification.

**Trigger:** The player selects the forgot password option on the login screen.

**Scenario:**
1. Player clicks the Forgot Password button on the login page.
2. Clicks on the text box marked e-mail.
3. Player enters the e-mail associated with the account.
4. Clicks on the text box marked username.
5. Player enters the username associated with the account.
6. Verifies the e-mail and username entered.
7. If valid, the system prompts the player to set a new password.
8. Click on the text box marked password.
9. Player types in the new password.
10. Press Enter.
11. Once submitted, verified to be a unique password in the system and then added to player profile to replace the old password.

**Post conditions:** Players password associated with the account is changed, and player is able to log in.

**Exceptions:**
1. Invalid e-mail or username.
2. No account matching the entries is found.
3. Invalid new password.

**Priority:** High Priority. Essential for account recovery.

**When available:** Available on the login page.

**Frequency of use:** Low, only used when player forgets their password.

**Channel to actor:** Keyboard and mouse

**Secondary actors:** Profile server

**Channel to secondary actors:** E-mail and username are stored on the Profile server and are used for account verification.

**Open issues:** Security issues?


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


**Use case:** View Current Status

**Iteration:** 1

**Primary Actor:** Player

**Goal in context:** View the current status of a searched player.

**Preconditions:** The player has searched for and selected a Profile.

**Trigger:** The player wants to see if the player is online and available to play.

**Scenario:**
1. Player selects view status.
2. Server checks current profile isOnline variable status
3. If false and therefore not logged in, profile is displayed as offline.
4. If true and therefore logged in, profile is displayed as online.
5. If logged in, Server checks current profile currentGame variable status.
6. If null, player is not currently in a game, profile is displayed as available.
7. If variable contains a string of a game name, then profile displays the current game name.

**Post conditions:** Current status is displayed on Profile, showing if online, offline, or what game the profile is currently playing.

**Exceptions:**
1. isOnline and currentGame variables display not up-to-date information

**Priority:** High priority, because players need to know if a player is currently available to be able to invite them to 
play a game together. 

**When available:** Viewable on a Profile page or on the Friends List.

**Frequency of use:** Constantly updated when on the Friends List page or on a Profile Page. 

**Channel to actor:** Displayed on a Profile Page Screen, or on the Friends List Page.

**Secondary actors:** Server

**Channel to secondary actors:** Obtains profile of selected profile to obtain variable contents.

**Open issues:** N/A


**Use case:** View Bio

**Iteration:** 1

**Primary Actor:** Existing Player

**Goal in context:** View the bio of another player.

**Preconditions:** The player has searched for and selected another player's profile. 

**Trigger:** The player wants to view the bio of another player

**Scenario:**
1. Player clicks into another player's profile.
2. The other player's profile loads up and their name and part of their bio is immediately visible.
3. If there is more to a player's bio than can fit in the immediately presented section of the profile, the user can click "show more" to see the other user's full bio.

**Post conditions:** The player who wanted to see the bio of the other player is able to see the other player's bio and has extended it if they wanted to. 

**Exceptions:**
1. The other player has no bio set.

**Priority:** High, because the bio is a fundamental part of player profiles. It allows users to express themselves and makes the site more social. 

**When available:** When an existing player is on another existing player's profile page. 

**Frequency of use:** Medium, players won't always be searching for other players but will occasionally want to look up other players to see how they're performing or to add them as friends.

**Channel to actor:** Displayed on another player's profile page, 

**Secondary actors:** Profile database server

**Channel to secondary actors:** Stored bio information for each user server-side

**Open issues:** N/A

**Use case:** Change Bio

**Iteration:** 1

**Primary Actor:** Existing Player

**Goal in context:** Change your own profile's bio.

**Preconditions:** The player has clicked into and is on their own profile page.

**Trigger:** The player wants to change their own bio.

**Scenario:**
1. Player clicks into their own profile.
2. Their profile loads up and their name and part of their bio is immediately visible.
3. There is a button that says "Edit profile" somewhere on their profile page.
4. They click that button and are brought to an edit profile page where they can edit their bio's information.
5. The user clicks a "save" button to save their changes.
6. The user is kicked back to their profile page where the changes have been applied.

**Post conditions:** The player has edited their bio and saved the changes. The changes are reflected on their profile.

**Exceptions:**
1. The player has no bio set. 

**Priority:** High, because the bio is a fundamental part of player profiles. It allows users to express themselves and makes the site more social.

**When available:** When a player is on their own profile page.

**Frequency of use:** Low, players will not often want to change their bio after they've set it for the first time, but when things change in their life or their interests shift, they might want to reflect that on their profile page.

**Channel to actor:** Displayed on a player's own profile page.

**Secondary actors:** Profile database server

**Channel to secondary actors:** Stored bio information for each user server-side

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

**Goal in context:** The player can be well informed before starting the game.

**Preconditions:** The player has chosen a game.

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

**Channel to actor:** In the multiplayer join match window, under the information icon “i".

**Secondary actors:** N/A

**Channel to secondary actors:** N/A

**Open issues:**  Should the instructions be shown every time you first log into the game or just make it optional from the start ?



**Use case:** Allow players to access instructions after game has started.

**Iteration:** 1

**Primary Actor:** Player

**Goal in context:** The player can go back to the instructions after starting the match.

**Preconditions:** The player has started a match.

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



**Use case:** Allow player to modify sound

**Iteration:** 1

**Primary Actor:** Player

**Goal in context:** Change the volume of background music 

**Preconditions:** The player has started a match.

**Trigger:** Pressing the settings button 
**Scenario:**
    1. The player selects a game (connect 4, checkers, tic tac toe) on the game page.
    2. The players volume is either to high or too low
    3. The player presses the settings button.
    4. The player adjust the volume to their desired intensity
    5. The player goes back to match

**Post conditions:** N/A

**Exceptions:**
    1. The volume is lowered
    2. The volume is intensified

**Priority:** Low. Not all players will need to change the background music, it will only be for those that want it to be louder or lower.

**When available:** 2nd or 3rd project iteration.

**Frequency of use:** Occasional. If the player finds the volume too loud or too quiet they can change the volume to their need.

**Channel to actor:** In the active game Window, under settings icon. 

**Secondary actors:** N/A

**Channel to secondary actors:** N/A

**Open issues:** Am not sure if we will have the music throughout the whole game. Would the music also be playing while game is selected? If so there will be more than one case description for this.



**Use case:** Allow player to modify brightness

**Iteration:** 1

**Primary Actor:** Player

**Goal in context:** Adjust the brightness of game. 

**Preconditions:** The player has started a match. 

**Trigger:** Pressing the settings button 
**Scenario:**
    1. The player selects a game (connect 4, checkers, tic tac toe) on the game page.
    2. The players brightness is either to high or too low
    3. The player presses the settings button.
    4. The player adjust the brightness to their desired intensity
    5. The player goes back to match

**Post conditions:** N/A

**Exceptions:**
    1. The brightness is intensified
    2. The brightness is lowered

**Priority:** Low. Not all players will need to change the brightness, it will only be for those that are uncomfortable with the brightness.

**When available:** 2nd or 3rd project iteration.

**Frequency of use:** Occasional. If the player finds the brightness is too bright or too low they can change the brightness to their need.

**Channel to actor:** In the active game Window, under settings icon. 

**Secondary actors:** N/A

**Channel to secondary actors:** N/A

**Open issues:** The brightness can be changed at any point. Therefore should we have a use case for each channel?


**Use case:** Home button: Allows people to quickly return to the home page from any other page.

**Iteration:** 1

**Primary Actor:** Player

**Goal in context:** The player can return to the "home page" (game menu) at any point in time, to allow
for easy backtracking when nested inside menus or other user profiles.

**Preconditions:** The player has logged in, and is currently on any page other than the home page.

**Trigger:** The player presses the home button available on any page other than the home page.

**Scenario:**
1. Player logs into their account.
2. Player selects the leader board and looks at the other players stats.
3. Player clicks on one of the other players name to open their profile.
4. Player decides they want to start a game of checkers, so the press the home button to return to the home page.


**Post conditions:** The player has returned to the home menu from the page they were previously on, and is now free to
navigate from there.

**Exceptions:**
1. If the player is already on the home page this option will not be made available.
2. If the player is in an active game, the player will still be able to press the home button, but a pop-up will show
that prompts them to consider if they actually wish to leave the game.

**Priority:** Medium, This is a convenient button to have if a player is embedded in multiple clicks of user profiles,
and reduces the inconvenience of having to click the back button multiple times.

**When available:** Any page after the login and main page. 

**Frequency of use:** Often. It will often be the case that a player may want to return to the main page to quickly
access the game menu. 

**Channel to actor:** In any page that is not the login page or main page. This will be a button in the top right corner
of the page.

**Secondary actors:** N/A

**Channel to secondary actors:** N/A

**Open issues:** N/A


**Use case:** Back button: Allows players to go back to the previous page visited.

**Iteration:** 1

**Primary Actor:** Player

**Goal in context:** The player can return to the previous page visited by clicking the back button,
this will allow players to go back one page without having to return to the home page.

**Preconditions:** The player has logged in, and is currently on any page other than the home page.

**Trigger:** The player presses the back button available on any page other than the home page.

**Scenario:**
1. Player logs into their account.
2. Player selects the leader board and looks at the other players stats.
3. Player clicks on one of the other players name to open their profile.
4. Player decides they want to go back to the leaderboard, so the press the back button to return to leaderboard page.


**Post conditions:** The player has returned to the previous page visited, and is now free to navigate from there.
navigate from there.

**Exceptions:**
1. If the player is on the home page this option will not be made available, instead a logout button will be available.
2. If the player is in an active game, the player will not be able to press the back button but will instead have the 
option to press the home button. 

**Priority:** Medium, This is a convenient button to have if the player wishes to visit a previous page that they were 
on instead of having to press the home button.

**When available:** Any page after the login and main page.

**Frequency of use:** Often. It will often be the case that a player may want to return to a previous page to quickly
access the content they had on the last page. 

**Channel to actor:** In any page that is not the login page or main page. This will be a button in the top left corner
of the page.

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
