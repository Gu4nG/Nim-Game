
/*
COMP90041 Programming and Software Development
Project A
Nimsys game
Student Name: Qianyu Guo
Student Number:921808
28 March 2018
*/
import java.util.Scanner;

/*
 *class Nimplayer record the name of 2 players and get the number of stones removed by players.
 */
public class NimPlayer {
	// record players' name
	String playerName;

	public NimPlayer(Scanner scanner) {
		this.playerName = scanner.next();
	}

	// Get the number of stones removed
	public int removeStone(Scanner scanner) {
		System.out.println(this.playerName + "'s turn - remove how many?");
		int stoneNumber = scanner.nextInt();
		// If the input stoneNumber is less then 1, then return 1 to ensure player
		// remove at least one stone.
		if (stoneNumber > 1) {
			return stoneNumber;
		} else {
			return 1;
		}
	}
}
