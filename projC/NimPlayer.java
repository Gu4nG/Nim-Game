
/*
COMP90041 Programming and Software Development
Project C: Nimsys Game
Student Name: Qianyu Guo
Student Number:921808
25 May 2018
*/

import java.io.*;
import java.util.Scanner;

abstract class NimPlayer implements Serializable,Testable{

	private String userName;
	private String familyName;
	private String givenName;



	//if type=0,the player is human,if type=1,the player is AI
	private int type=0;
	public int winTimes = 0;
	public int playTimes = 0;
	public int rank = 0;
	public float ranking = 0;
	public int index = 0;
	//isFirst=1,player1,=2 player2
	public int isFirst=0;
	public NimPlayer(){

	}

	public NimPlayer(String userName, String familyName, 
						String givenName,int type) {
		setType(type);
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
			userName + "," + givenName + "," +
			familyName + "," + playTimes
			 +" games," + winTimes + " wins");
	}

	//show rank of players
	public void rankings() {
		String ranking = String.valueOf(rank) + "%";
		System.out.printf(
			"%-5s| %02d games | %s %s\n",
			 ranking, playTimes, givenName, familyName);
	}

	// Remove stones during the game
	public int removeStone(Scanner scanner) {
		int stoneNumber = scanner.nextInt();
		scanner.nextLine();
		return stoneNumber;
	}
	public int removeStone(int initialNumber,int upperBound){
		int stoneNumber=0;
		return stoneNumber;
	}
	public String advancedMove(boolean[] available, Scanner scanner){
		
		return scanner.nextLine();
	}
	public String advancedMove(boolean[] available, String lastMove){
		return lastMove;
	}
}
