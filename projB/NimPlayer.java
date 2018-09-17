
/*
COMP90041 Programming and Software Development
Project B: Nimsys Game
Student Name: Qianyu Guo
Student Number:921808
29 April 2018
*/

import java.util.Scanner;

public class NimPlayer {

	private String userName;
	private String familyName;
	private String givenName;
	public int winTimes = 0;
	public int playTimes = 0;
	public int rank = 0;
	public float ranking = 0;
	public int index = 0;
	

	public NimPlayer(String userName, String familyName, String givenName) {
		setUserName(userName);
		setFamilyName(familyName);
		setGivenName(givenName);
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	

	//reset the game
	public void reset() {
		winTimes = 0;
		playTimes = 0;
		rank = 0;
	}
	
	//display info about players
	public void display() {
		System.out.println(
				userName + "," + givenName + "," + familyName + "," + playTimes + " games," + winTimes + " wins");
	}

	//show rank of players
	public void rankings() {
		String ranking = String.valueOf(rank) + "%";
		System.out.printf("%-5s| %02d games | %s %s\n", ranking, playTimes, givenName, familyName);
	}

	// Remove stones during the game
	public int removeStone(Scanner scanner) {
		int stoneNumber = scanner.nextInt();
		scanner.nextLine();
		return stoneNumber;
	}
}
