
/*
COMP90041 Programming and Software Development
Project C: Nimsys Game
Student Name: Qianyu Guo
Student Number:921808
25 May 2018
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Comparator;
import java.io.*;

public class Nimsys {
    public Nimsys() {
        if (checkFileExist()) this.setPlayers(readPlayer());
    }

    final int a = 100;
    public boolean isInNimsys = true;
    public int pos = 0;
    public NimPlayer playerUpdate;
    private String fileName = "players.dat";
    private NimPlayer[] players = new NimPlayer[a];
    private ArrayList<NimPlayer> playersAL;
    private String[] commands = {"addplayer", "editplayer",
                             "removeplayer", "displayplayer", 
                            "resetstats", "rankings", 
                            "addaiplayer", "startgame",
                             "startadvancedgame", "exit"};
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
            if (! userInput[0].isEmpty()) {
                switch (userInput[0]) {
                    case "addplayer":
                        String[] addPlayer = null;
                        try {
                            try {
                                addPlayer = userInput[1].split(",");
                            } catch (Exception e) {
                                System.out.println(
                                    "Incorrect number of arguments supplied to command.");
                                break;
                            }
                            checkArguments(3, addPlayer.length);
                        } catch (Exception e) {
                            System.out.println(
                                "Incorrect number of arguments supplied to command.");
                            break;
                        }
                        addPlayer(addPlayer[0], addPlayer[1], addPlayer[2], 0);
                        break;
                    case "addaiplayer":
                        String[] addAIPlayer = null;
                        try {
                            try {
                                addAIPlayer = userInput[1].split(",");
                            } catch (Exception e) {
                                System.out.println(
                                    "Incorrect number of arguments supplied to command.");
                                break;
                            }
                            checkArguments(3, addAIPlayer.length);
                        } catch (Exception e) {
                            System.out.println(
                                "Incorrect number of arguments supplied to command.");
                            break;
                        }
                        addPlayer(addAIPlayer[0], addAIPlayer[1],
                                 addAIPlayer[2], 1);
                        break;
                    case "removeplayer":
                        String[] removePlayer = null;
                        if (userInput.length == 1) {
                            removeAllPlayers();
                        } else {
                            try {
                                removePlayer = userInput[1].split(",");
                            } catch (Exception e) {
                                removePlayer(userInput[1]);
                            }
                            removePlayer(removePlayer[0]);
                        }
                        break;
                    case "editplayer":
                        String[] editPlayers = null;
                        try {
                            try {
                                editPlayers = userInput[1].split(",");
                            } catch (Exception e) {
                                System.out.println(
                                    "Incorrect number of arguments supplied to command.");
                                break;
                            }
                            checkArguments(3, editPlayers.length);
                        } catch (Exception e) {
                            System.out.println(
                                "Incorrect number of arguments supplied to command.");
                            break;
                        }
                        editPlayer(editPlayers[0], editPlayers[1],
                                     editPlayers[2]);
                        break;
                    case "resetstats":
                        String[] resetPlayer = null;
                        if (userInput.length == 1) {
                            resetAllPlayers();
                        } else {
                            try {
                                resetPlayer = userInput[1].split(",");
                            } catch (Exception e) {
                                resetPlayer(userInput[1]);
                            }
                            resetPlayer(resetPlayer[0]);
                        }
                        break;
                    case "displayplayer":
                        String[] displayPlayer = null;
                        if (userInput.length == 1) {
                            displayAllPlayers();
                        } else {
                            try {
                                displayPlayer = userInput[1].split(",");
                            } catch (Exception e) {
                                displayPlayer(userInput[1]);
                            }
                            displayPlayer(displayPlayer[0]);
                        }
                        break;
                    case "rankings":
                        String[] rankings = null;
                        if (userInput.length == 1) {
                            rankByDesc();
                        } else {
                            try {
                                rankings = userInput[1].split(",");
                            } catch (Exception e) {
                                if (userInput[1].equals("desc")) {
                                    rankByDesc();
                                }
                                if (userInput[1].equals("asc")) {
                                    rankByAsc();
                                }
                            }
                            if (rankings[0].equals("desc")) {
                                rankByDesc();
                            }
                            if (rankings[0].equals("asc")) {
                                rankByAsc();
                            }
                        }
                        break;
                    case "startgame":
                        String[] startGame = null;
                        try {
                            try {
                                startGame = userInput[1].split(",");
                            } catch (Exception e) {
                                System.out.println(
                                    "Incorrect number of arguments supplied to command.");
                                break;
                            }
                            checkArguments(4, startGame.length);
                        } catch (Exception e) {
                            System.out.println(
                                "Incorrect number of arguments supplied to command.");
                            break;
                        }
                        startGame(Integer.valueOf(startGame[0]),
                                Integer.valueOf(startGame[1]),
                                startGame[2], startGame[3], 0);
                        break;
                    case "startadvancedgame":
                        String[] startAdvancedGame = null;
                        try {
                            try {
                                startAdvancedGame = userInput[1].split(",");
                            } catch (Exception e) {
                                System.out.println(
                                    "Incorrect number of arguments supplied to command.");
                                break;
                            }
                            checkArguments(3, startAdvancedGame.length);
                        } catch (Exception e) {
                            System.out.println(
                                "Incorrect  number of arguments supplied to command.");
                            break;
                        }
                        startGame(Integer.valueOf(startAdvancedGame[0]),2, 
                                                    startAdvancedGame[1], 
                                                startAdvancedGame[2], 1);
                        break;
                    case "exit":
                        try {
                            savePlayer();
                        } catch (Exception e) {

                        }
                        isInNimsys = false;
                        break;
                    case "test":
                        test();
                        break;
                    default:
                        try {
                            checkCommand(userInput[0]);
                        } catch (Exception e) {
                            System.out.println(
                                "'" + userInput[0] + "'" + " is not a valid command.");
                        }
                        break;
                }
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
        return - 1;
    }

    // add player
    public void addPlayer(String userName, String familyName, 
                        String givenName, int humanOrAI) {
        NimPlayer playerToAdd = null;
        if (findPlayer(userName, 0) >= 0) {
            System.out.println("The player already exists.");
        } else {
            if (humanOrAI == 0) playerToAdd = new NimHumanPlayer(userName, 
                                                    familyName, givenName);
            if (humanOrAI == 1) playerToAdd = new NimAIPlayer(userName,
                                                     familyName, givenName);
            playerToAdd.index = pos;
            players[pos] = playerToAdd;
            if (pos < players.length) {
                pos++;
            } else
                return;
        }
    }

    // remove all the players
    public void removeAllPlayers() {
        System.out.println(
            "Are you sure you want to remove all players? (y/n)");
        if (scanner.next().equals("y")) {
            for (int i = 0; i < players.length; i++) {
                players[i] = null;
            }
            pos = 0;
        }
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
    public void editPlayer(String userName,
                             String newFamilyName, String newGivenName) {
        int index = findPlayer(userName, 1);
        if (index >= 0) {
            players[index].setFamilyName(newFamilyName);
            players[index].setGivenName(newGivenName);
        }
    }

    // reset all the players
    public void resetAllPlayers() {
        System.out.println(
            "Are you sure you want to reset all player statistics? (y/n)");
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
        //NimPlayer[] displayArray = players;
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
    //if mode=0,old NimGame,if mode=1,advanced NimGame
    public void startGame(int initialStones, int upperBound, String userName1, 
                            String userName2, int mode) {
        NimPlayer winner = null;
        int index1 = findPlayer(userName1, 0);
        int index2 = findPlayer(userName2, 0);
        if (index1 >= 0 && index2 >= 0) {
            players[index1].playTimes++;
            players[index2].playTimes++;
            NimGame newVersus = new NimGame(initialStones, 
                                          upperBound, players[index1],
                                             players[index2], scanner);
            switch (mode) {
                case 0:
                    winner = newVersus.start();
                    break;
                case 1:
                    winner = newVersus.advancedStart();
                    break;
            }
            players[winner.index].winTimes++;
            players[index1].ranking =
                (float) players[index1].winTimes / (float) players[index1].playTimes;
            players[index2].ranking = 
                (float) players[index2].winTimes / (float) players[index2].playTimes;
            players[index1].rank = Math.round(100 * players[index1].ranking);
            players[index2].rank = Math.round(100 * players[index2].ranking);
        } else {
            System.out.println("One of the players does not exist.");
        }
    }

    //check if file exists
    public boolean checkFileExist() {
        File file = new File(fileName);
        if (file.exists()) return true;
        else return false;
    }

    //read game doc
    public NimPlayer[] readPlayer() {
        NimPlayer[] playersToReturn = new NimPlayer[a];
        try {
            ObjectInputStream ois = new ObjectInputStream(
                                        new FileInputStream(fileName));
            playersToReturn = (NimPlayer[]) ois.readObject();
            pos = (int) ois.readInt();
            ois.close();
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
        return playersToReturn;
    }

     //save game doc
    public void savePlayer() {
        File file = new File(fileName);
        if (! file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                                        new FileOutputStream(fileName));
            oos.writeObject(players);
            oos.writeInt(pos);
            oos.close();
        } catch (IOException e) {
        }   
    }

    //check if input command in the commands list 
    public boolean checkCommand(String command) throws Exception {
        if (Arrays.binarySearch(commands, command) < 0)
            throw new Exception();
        else return true;
    }

    //check if arguement is sufficient
    public boolean checkArguments(int rightNumber, int inputNumber)
        throws Exception {
            if (inputNumber < rightNumber)
                throw new Exception();
          else return true;
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
                resLast = - 1;
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
                resLast = - 1;
            if (res > 0)
                resLast = 1;
            return resLast;
        }
    };
    public void test() {
        System.out.println(pos);
    }

    // sort username by Alphabetical order
    Comparator<NimPlayer> comparatorUserName = new Comparator<NimPlayer>() {
        @Override
        public int compare(NimPlayer o1, NimPlayer o2) {
            return o1.getUserName().compareTo(o2.getUserName());
        }
    };

    public void setPlayers(NimPlayer[] players) {
        this.players = players;
    }

}