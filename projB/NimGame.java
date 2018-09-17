
/*
COMP90041 Programming and Software Development
Project B: Nimsys Game
Student Name: Qianyu Guo
Student Number:921808
29 April 2018
*/
import java.util.Scanner;

public class NimGame {

	private int initialNumber;
	private int upperBound;

	private Scanner scanner;
	public NimPlayer player1;
	public NimPlayer player2;
	public NimPlayer currentPlayer;
	public NimPlayer winner;

	public NimGame(int initialNumber, int upperBound, NimPlayer player1, NimPlayer player2, Scanner scanner) {
		setInitialNumber(initialNumber);
		setUpperBound(upperBound);
		this.player1 = player1;
		this.player2 = player2;
		this.scanner = scanner;
	}

	public int getInitialNumber() {
		return initialNumber;
	}

	public void setInitialNumber(int initialNumber) {
		this.initialNumber = initialNumber;
	}

	public int getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(int upperBound) {
		this.upperBound = upperBound;
	}

	//Show game info,and start game
	public NimPlayer start() {
		System.out.println("\nInitial stone count: " + initialNumber);
		System.out.println("Maximum stone removal: " + upperBound);
		System.out.println("Player 1: " + player1.getGivenName() + " " + player1.getFamilyName());
		System.out.println("Player 2: " + player2.getGivenName() + " " + player2.getFamilyName());
		while (initialNumber > 0) {
			turn(player1);
			turn(player2);
		}
		gameOver();
		return winner;
	}

	// Turns between two players
	
	public void turn(NimPlayer player) {
		boolean removeStoneOutsideRange = true;
		int stoneToMove = 0;
		if (initialNumber > 0) {
			currentPlayer = player;
			while (removeStoneOutsideRange) {
				displayStones();
				System.out.println(currentPlayer.getGivenName() 
						+ "'s turn - remove how many?");
				stoneToMove = currentPlayer.removeStone(scanner);
				if (stoneToMove >= 1 && stoneToMove <= upperBound)
					removeStoneOutsideRange = false;
				else {
					int upper;
					if(upperBound<initialNumber) upper=upperBound;
					else upper=initialNumber;
					System.out.println("Invalid move. You must remove between 1 and " +
				upper+ " stones");
			}
			initialNumber = initialNumber - stoneToMove;
		}
		//When no stones left, then the method judgeWinner() returns the winner
		if (initialNumber <= 0) {
			winner = judgeWinner();
		}
	}
	// If current player is player1,then player2 wins, and vice versa.
	public NimPlayer judgeWinner() {
		if (currentPlayer == player1)
			return player2;
		else
			return player1;
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

	//Show game is over and who won the game.
	public void gameOver() {
		System.out.println("\nGame Over");
		System.out.println(winner.getGivenName() + " " + winner.getFamilyName() + " wins!");
	}
}
