
/*
COMP90041 Programming and Software Development
Project B: Nimsys Game
Student Name: Qianyu Guo
Student Number:921808
29 April 2018
*/

import java.util.Arrays;
import java.util.Scanner;
import java.util.Comparator;

public class Nimsys {
	int a = 100;
	private NimPlayer[] players = new NimPlayer[a];
	public boolean isInNimsys = true;
	public int pos = 0;
	public NimPlayer playerUpdate;
	Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Welcome to Nim");
		Nimsys newNim = new Nimsys();
		newNim.command();
	}
	
	//different commands correspond to different methods 
	public void command() {
		while (isInNimsys) {
			System.out.print("\n$");
			String[] userInput = scanner.nextLine().split(" ");
			switch (userInput[0]) {
			case "addplayer":
				String[] addPlayers = userInput[1].split(",");
				addPlayer(addPlayers[0], addPlayers[1], addPlayers[2]);
				break;
			case "removeplayer":
				if (userInput.length == 1) {
					removeAllPlayers();
				} else {
					removePlayer(userInput[1]);
				}
				break;
			case "editplayer":
				String[] editPlayers = userInput[1].split(",");
				editPlayer(editPlayers[0], editPlayers[1], editPlayers[2]);
				break;
			case "resetstats":
				if (userInput.length == 1) {
					resetAllPlayers();
				} else {
					resetPlayer(userInput[1]);
				}
				break;
			case "displayplayer":
				if (userInput.length == 1) {
					displayAllPlayers();
				} else {
					displayPlayer(userInput[1]);
				}
				break;
			case "rankings":
				if (userInput.length == 1) {
					rankByDesc();
				} else {
					if (userInput[1].equals("desc")) {
						rankByDesc();
					}
					if (userInput[1].equals("asc")) {
						rankByAsc();
					}
				}
				break;
			case "startgame":
				String[] startGame = userInput[1].split(",");
				startGame(Integer.valueOf(startGame[0]),
						Integer.valueOf(startGame[1]), 
						startGame[2], startGame[3]);
				break;
			case "exit":
				isInNimsys = false;
				break;
			default:
				break;
			}
		}
		System.out.println();
		System.exit(0);
	}

	// find certain player in the array according to username
	public int findPlayer(String userName, int mode) {
		for (int i = 0; i < players.length; i++) {
			if (players[i] != null) {
				if (players[i].getUserName().equals(userName)) {
					return i;
				}
			}
		}
		if (mode == 0) {
		}
		if (mode == 1) {
			System.out.println("The player does not exist.");
		}
		return -1;
	}

	// add player
	public void addPlayer(String userName, String familyName, String givenName) {
		if (findPlayer(userName, 0) >= 0) {
			System.out.println("The player already exists.");
		} else {
			NimPlayer playerToAdd = new NimPlayer(userName, familyName, givenName);
			playerToAdd.index = pos;
			players[pos] = playerToAdd;
			if (pos < players.length)
				pos++;
			else
				return;
		}
	}

	// remove all the players
	public void removeAllPlayers() {
		System.out.println("Are you sure you want to remove all players? (y/n)");
		if (scanner.next().equals("y")) {
			for (int i = 0; i < players.length; i++) {
				players[i] = null;
			}
		}
		pos = 0;
		scanner.nextLine();
	}

	// remove certain player
	public void removePlayer(String userName) {
		int index = findPlayer(userName, 1);
		if (index >= 0) {
			players[index] = null;
			while (players[index + 1] != null) {
				players[index] = players[index + 1];
				players[index].index--;
				index++;
			}
			players[index] = null;
			pos--;
		}
	}

	// edit player's name
	public void editPlayer(String userName, String newFamilyName, String newGivenName) {
		int index = findPlayer(userName, 1);
		if (index >= 0) {
			players[index].setFamilyName(newFamilyName);
			players[index].setGivenName(newGivenName);
		}
	}

	// reset all the players
	public void resetAllPlayers() {
		System.out.println("Are you sure you want to reset all player statistics? (y/n)");
		if (scanner.next().equals("y")) {
			for (int i = 0; i < players.length; i++) {
				if (players[i] != null) {
					players[i].reset();
				}
			}
		}
		scanner.nextLine();
	}

	// reset certain player
	public void resetPlayer(String userName) {
		int index = findPlayer(userName, 1);
		if (index >= 0) {
			players[index].reset();
		}
	}

	// display info of all the players
	public void displayAllPlayers() {
		NimPlayer[] displayArray = moveToNewArray(players);
		Arrays.sort(displayArray, comparatorUserName);
		for (int i = 0; i < displayArray.length; i++) {
			if (displayArray[i] != null) {
				displayArray[i].display();
			}
		}
	}

	// display info of certain player
	public void displayPlayer(String userName) {
		int index = findPlayer(userName, 1);
		if (index >= 0) {
			players[index].display();
		}

	}

	// rank players by descend order
	public void rankByDesc() {
		NimPlayer[] rankingArray = moveToNewArray(players);
		Arrays.sort(rankingArray, comparatorDesc);
		if (rankingArray.length > 10) {
			for (int i = 0; i < 10; i++) {
				rankingArray[i].rankings();
			}
		} else {
			for (int i = 0; i < rankingArray.length; i++) {
				rankingArray[i].rankings();
			}
		}
	}

	// rank players by ascend order
	public void rankByAsc() {
		NimPlayer[] rankingArray = moveToNewArray(players);
		Arrays.sort(rankingArray, comparatorAsc);
		if (rankingArray.length > 10) {
			for (int i = 0; i < 10; i++) {
				rankingArray[i].rankings();
			}
		} else {
			for (int i = 0; i < rankingArray.length; i++) {
				rankingArray[i].rankings();
			}
		}
	}

	// sort rank by descend order
	Comparator<NimPlayer> comparatorDesc = new Comparator<NimPlayer>() {
		@Override
		public int compare(NimPlayer o1, NimPlayer o2) {
			float res = o2.ranking - o1.ranking;
			int resLast = 0;
			if (res == 0) {
				resLast = o1.getUserName().compareTo(o2.getUserName());
			}
			if (res < 0)
				resLast = -1;
			if (res > 0)
				resLast = 1;
			return resLast;
		}
	};
	
	// sort rank by ascend order
	Comparator<NimPlayer> comparatorAsc = new Comparator<NimPlayer>() {
		@Override
		public int compare(NimPlayer o1, NimPlayer o2) {
			float res = o1.rank - o2.rank;
			int resLast = 0;
			if (res == 0) {
				res = o1.getUserName().compareTo(o2.getUserName());
			}
			if (res < 0)
				resLast = -1;
			if (res > 0)
				resLast = 1;
			return resLast;
		}
	};
	
	// sort username by Alphabetical order
	Comparator<NimPlayer> comparatorUserName = new Comparator<NimPlayer>() {
		@Override
		public int compare(NimPlayer o1, NimPlayer o2) {
			return o1.getUserName().compareTo(o2.getUserName());
		}
	};

	// move sorting result to a new array
	public NimPlayer[] moveToNewArray(NimPlayer[] oldArray) {
		NimPlayer[] newArray = new NimPlayer[pos];
		for (int j = 0; j < newArray.length; j++) {
			newArray[j] = oldArray[j];
		}
		return newArray;
	}

	// check if two players are in the array,
	// and record play times,win times and ranking
	public void startGame(int initialStones, int upperBound, String userName1, String userName2) {
		int index1 = findPlayer(userName1, 0);
		int index2 = findPlayer(userName2, 0);
		if (index1 >= 0 && index2 >= 0) {
			players[index1].playTimes++;
			players[index2].playTimes++;
			NimGame newVersus = new NimGame(initialStones, upperBound, players[index1], players[index2], scanner);
			NimPlayer winner = newVersus.start();
			players[winner.index].winTimes++;
			players[index1].ranking = (float) players[index1].winTimes / (float) players[index1].playTimes;
			players[index2].ranking = (float) players[index2].winTimes / (float) players[index2].playTimes;
			players[index1].rank =  Math.round(100 *players[index1].ranking);
			players[index2].rank =  Math.round(100 *players[index2].ranking);
		} else {
			System.out.println("One of the players does not exist.");
		}
	}

}