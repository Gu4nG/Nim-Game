
/*
COMP90041 Programming and Software Development
Project A: Nimsys Game
Student Name: Qianyu Guo
Student Number:921808
28 March 2018
*/
import java.util.Scanner;

/*
 * class Nimsys plays Nimsys game of two players from NimPlayer class.
 */
public class Nimsys {
	int upperBound = 0;
	int initialNumber = 0;

	String winnerName;
	boolean playAgain = true;
	Scanner scanner = new Scanner(System.in);

	NimPlayer player1;
	NimPlayer player2;
	NimPlayer currentPlayer;

	public static void main(String[] args) {
		Nimsys newGame = new Nimsys();
		newGame.getname();
		newGame.playGame();
	}

	// Display a welcome message and get players' name
	public void getname() {
		System.out.println("Welcome to Nim");
		System.out.println("\nPlease enter Player 1's name:");
		player1 = new NimPlayer(scanner);
		System.out.println("\nPlease enter Player 2's name:");
		player2 = new NimPlayer(scanner);
	}

	// The process of the game
	public void playGame() {
		// while playAgain equals true, the game will start again and again.
		while (playAgain) {
			System.out.println("\nPlease enter upper bound of stone removal:");
			upperBound = scanner.nextInt();
			System.out.println("\nPlease enter initial number of stones:");
			initialNumber = scanner.nextInt();
			displayStones();
			while (initialNumber > 0) {
				turn(player1);
				turn(player2);
			}
			// playAgain equals the returned value of gameOver().
			playAgain = gameOver();
		}
	}

	// Turns between two players
	// Input a instance of NimPlayer class
	public void turn(NimPlayer player) {
		if (initialNumber > 0) {
			// two players take turns to play.
			currentPlayer = player;
			initialNumber = initialNumber - currentPlayer.removeStone(scanner);
			displayStones();
		}
		// no stones left,judge winner
		if (initialNumber <= 0) {
			winnerName = judgeWinner();
		}
	}

	// If current player is player1,then player2 wins, and vice versa.
	public String judgeWinner() {
		if (currentPlayer == player1)
			return player2.playerName;
		else
			return player1.playerName;
	}

	// Display the number of stones in "*"
	public void displayStones() {
		System.out.println();
		if (initialNumber > 0) {

			System.out.print(initialNumber + " stones left:");
			for (int i = 0; i < initialNumber; i++) {
				System.out.print(" *");
			}
			System.out.println();
		}
	}

	// Judge if the game is over
	public boolean gameOver() {
		System.out.println("Game Over");
		System.out.println(winnerName + " wins!\n");
		System.out.print("Do you want to play again (Y/N):");
		// If input is "Y", return is true, the game will start again,
		// otherwise, the game ends.
		if (scanner.next().equals("Y"))
			return true;
		else
			return false;
	}
}