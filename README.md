# Recycling Game ♻️

An educational desktop game developed in Java using Swing to promote environmental awareness through interactive waste sorting.

## About the Project

Recycling Game challenges players to sort different types of waste into their corresponding recycling bins before the countdown reaches zero.

The project was developed as a Java learning and portfolio project, focusing on graphical user interfaces, event handling, timers, audio playback, and persistent data storage.

## Screenshots

### Main Menu
![Main Menu](screenshots/main-menu.png)

### Gameplay
![Gameplay](screenshots/gameplay.png)

### Final Score
![Final Score](screenshots/final-score.png)

## Features

- Interactive drag-and-drop gameplay
- Five types of waste and corresponding recycling bins
- Countdown timer
- Score tracking
- Persistent high score between sessions
- Background music and sound effects
- Final score screen
- Manually ending a game

## Technologies Used

- **Java 24**
- **Java Swing** — graphical user interface
- **Java AWT** — graphics and event handling
- **Java Sound API** — music and sound effects
- **Git and GitHub** — version control and project hosting

## Download

Download the executable JAR file from the latest release:

**[Download Recycling Game v1.0.0](https://github.com/rodprogramer/recycling-game/releases/tag/v1.0.0)**

### Requirements

- A compatible Java Runtime Environment (JRE) or Java Development Kit (JDK).
- Java must be available from the command line.

### How to Run

1. Download `recycling-game.jar` from the release page.
2. Open a terminal in the folder containing the downloaded file.
3. Run the following command:

   ```bash
   java -jar recycling-game.jar
   ```

Alternatively, if Java is configured to open JAR files, you can try double-clicking the file.

## Project Structure

```text
recycling-game/
├── src/
│   └── jogo/reciclagem/
│       ├── JogoReciclagemGUI.java
│       ├── TelaJogo.java
│       ├── TelaFinal.java
│       └── AudioPlayer.java
├── resources/
│   ├── Images and audio files
├── screenshots/
│   ├── main-menu.png
│   ├── gameplay.png
│   └── final-score.png
├── .gitignore
└── README.md
```

## Author

Developed as a Java learning and portfolio project.

