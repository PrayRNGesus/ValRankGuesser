# ValRankGuesser

ValRankGuesser is a **Discord bot** designed to engage users with a fun challenge: guess the rank of players in Valorant based on a clip of them. This project was developed three years ago, and was my first attempt at making a discord bot. Since development of this plugin, multiple websites have popped up with similar concepts. Some clips

## Features
- **Rank Guessing Game**: Users can guess the rank of a given Valorant player based on a video provided to the user.
- **Discord Integration**: The bot is fully integrated into Discord, making it easy for users to interact through commands.

## Usage
- Add clips in the clips/RegisterClips.java file.
- Change the command prefix with `prefix + prefix [new prefix]` (e.x: v!prefix .)
- Start playing by typing `prefix + start [easy/hard]` (e.x: v!start easy)
- View highscore with `prefix + highscore`, `prefix + score`, or `prefix + points` (e.x: v!highscore)
- Quit/Stop playing with `prefix + quit` (e.x: v!quit)
- View Highscore Leaderboards by everyone in your database (if only in one server, highscore of players in your server) with `prefix + leaderboard` (e.x: v!leaderboard)
- For further with commands, type `prefix + help` (e.x: v!help)
- Review TOS with `prefix + tos` (e.x: v!tos)

## Setup
1. Clone this repository:
   ```bash
   git clone https://github.com/nathanrenner7/ValRankGuesser
   ```

2. Install Maven if you haven't already.

3. Create a file called `token.txt` in the root of your project, and paste in your discord bot token.

4. Build the project using Maven:
	```bash
	mvn clean install
	```
	
5. Run the bot:
	```bash
	java -jar target/valrankguesser-1.0-SNAPSHOT.jar
	```
	
# Requirements
- Java 10 or higher.

# Contributing
Feel free to fork the repository to modify. Contributions are welcome to improve the plugin further!
