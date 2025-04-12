# Retro Arcade

Retro Arcade is an online multiplayer board game platform built for real-time competitive fun and seamless matchmaking. Inspired by the vision of creating a "Steam for board games," it supports multiple users, dynamic game sessions, and a responsive JavaFX interface. Whether you're playing with friends or climbing the leaderboard, Retro Arcade delivers the experience.

---

## Table of Contents

- [About](#about)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [License](#license)

---

## About

Retro Arcade was developed in response to a bold vision: to create a platform where players can discover and play multiplayer board games in real-time, track their game stats, rise through leaderboards, and build a vibrant community.

The current version has seperate server and client files and has networking capabilities

---

## Features

- Multiplayer board game support
- Matchmaking engine for real-time game joining
- Real time chat within game play
- JavaFX-based GUI for smooth user interaction
- Multithreaded server for concurrent gameplay
- Supports multiple simultaneous client connections
- Extensible architecture for  features like leaderboards and player profiles
- Functionality for adding and playing with friends

---

## Tech Stack

- **Language:** Java
- **GUI:** JavaFX
- **Networking:** java.net (sockets)
- **Concurrency:** Java Threads
- **Database:** PostgreSQL
- **Testing:** JUnit 5

---

## Getting Started

### Starting the Server

1. Clone the repository and switch to the `Server` branch.
2. Set up roots (see below)
3. Navigate to the `Launcher` package.
4. Run the server launcher class to start the server instance.

#### Location: Branch: "Server", src/server_main/java/launcher/ServerLauncher.java

### Set up roots:
Sources root: src/server_main/java \
Test sources root: tests 



### Launching the Client

1. Set up roots (see below)
2. Switch to the `p3_GUI` branch or open the client-side code.
3. Navigate to the `GUI_client` package.
4. Run the main application class to launch the game client.

#### Location: Branch: "p3_GUI", src/client_main/java/GUI_client/MainApplication.java

### Set up roots:
Sources root: src/client_main/java \
Resources root: src/client_main/assets \
Test sources root: tests 



---

## Usage

Once both client and server are running:

- Players can connect to the server from the GUI.
- Available games will appear in the game lobby.
- Players can join matches, interact through the GUI, and play selected board games in real-time.

---

## License

This project is for educational purposes and is not yet licensed for commercial use.
