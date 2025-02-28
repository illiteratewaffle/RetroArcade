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
A login screen is displayed to player prompting them to enter their username and password
The player enters their username and password and hits enter
The system verifies the player’s credentials using the authentication database
The system verifies the player’s credentials and redirects them to the page displaying their profile
If the username and/or password entered by the user are invalid, the screen displays an error message and the user is prompted to re-enter their credentials, or to create an account if they do not have an existing one

**Post conditions:** The player has successfully logged in and now has access to their profile, leaderboard, and the systems various game features

**Exceptions:**
Username and/or password not correct. System displays error message and prompts user to try again
Player has forgotten password. Screen displays “forgot password” option, enabling player to reset their password through email


**Priority:** High. Essential in order for players to access the gaming platform and its various features, some of which include playing games, checking leaderboards, and viewing their profile

**When available:** Second or third project iteration.

**Frequency of use:** Most, if not every use. Players will need to log in every time they attempt to access the platform after closing it.

**Channel to actor:** Via the touch screen interface (login screen on the homepage)

**Secondary actors:** Authentication system/database

**Channel to secondary actors:** User database

**Open issues:**
If the login page provides the option to access the system through third-party logins, how will this be presented on the screen?
How will error messages be displayed on the screen?
