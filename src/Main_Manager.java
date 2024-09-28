//	Ron Scott - August 15 2023 - Case Study – Mario Match Game
//
//	This is a small game that attempts to use all of the programming concepts we've worked on over the semester.
//	This is the main class. It calls on other classes to run the game.
//
//	LayoutManager handles all of the GUI
//	GameLogicManager handles all of the game logic
//	FileManager handles the IO for storing High Scores
//	It's not called here, but ButtonClickListener handles button click events
//
//	This is a matching game. You have 32 tiles on the screen that you need to click on.
//	When you click on a tile, it'll turn face up. Now you have to find the matching tile.
//
//	If you find the matching tile, then that pair will be removed from the game table.
//	If the tiles do not match, they will both be flipped back over.
//
//	When there are no more tiles remaining, you win!
//	
//	The game will display an previous high scores you may have set.
//	This is read/written to file as the game starts and quits.
//  
//	Best of Luck! Hope you have fun. I had fun designing it :)
//
public class Main_Manager {

	public static void main(String[] args) {

		GameLogicManager gameLogicManager = new GameLogicManager();
		FileManager fileManager = new FileManager();

		LayoutManager layoutManager = new LayoutManager(gameLogicManager);	

		gameLogicManager.setLayoutManager(layoutManager);
		gameLogicManager.setFileManager(fileManager);

		fileManager.setLayoutManager(layoutManager);
		fileManager.setGameLogicManager(gameLogicManager);

		gameLogicManager.gameInit();
	}
}
