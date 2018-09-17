/*
COMP90041 Programming and Software Development
Project C: Nimsys Game
Student Name: Qianyu Guo
Student Number:921808
25 May 2018
*/

import java.util.Arrays;
import java.util.Scanner;

public class NimGame implements AdvancedNimGame {
    private NimPlayer player1;
    private NimPlayer player2;
    private NimPlayer currentPlayer;
    private NimPlayer winner;
    private int initialNumber;
    private int upperBound;
    private boolean[] available;
    private Scanner scanner;
    private String lastMove = null;


    public NimGame(int initialNumber, int upperBound, 
                    NimPlayer player1, NimPlayer player2, 
                    Scanner scanner) {
        setInitialNumber(initialNumber);
        setUpperBound(upperBound);
        this.player1 = player1;
        player1.isFirst=1;
        this.player2 = player2;
        player2.isFirst=2;
        this.scanner = scanner;
        this.available = new boolean[initialNumber];
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
        System.out.println("Player 1: " + player1.getGivenName() 
                            + " " + player1.getFamilyName());
        System.out.println("Player 2: " + player2.getGivenName() 
                            + " " + player2.getFamilyName());
        while (initialNumber > 0) {
            turn(player1);
            turn(player2);
        }
        gameOver();
        return winner;
    }

    // Turns between two players

    private void turn(NimPlayer player) {
        boolean removeStoneOutsideRange = true;
        int stoneToMove = 0;
        int upper = Math.min(upperBound, initialNumber);
        if (initialNumber > 0) {
            currentPlayer = player;
            while (removeStoneOutsideRange) {
                displayStones();
                System.out.println(currentPlayer.getGivenName()
                        + "'s turn - remove how many?");
                if (currentPlayer.getType() == 0) {
                    try {
                        stoneToMove = currentPlayer.removeStone(scanner);
                        //upper = Math.min(upperBound, initialNumber);
                        if (stoneToMove < 1 || stoneToMove > upper)
                            throw new Exception();
                        initialNumber = initialNumber - stoneToMove;
                        removeStoneOutsideRange = false;
                    } catch (Exception e) {
                        System.out.println(
                            "Invalid move.You must remove between 1 and "
                            + upper + " stones.");
                    }
                } else if (currentPlayer.getType() == 1) {
                    stoneToMove = currentPlayer.removeStone(initialNumber, upper);
                    initialNumber = initialNumber - stoneToMove;
                    removeStoneOutsideRange = false;
                }
            }
            //When no stones left, then the method judgeWinner() returns the winner
            if (initialNumber <= 0) {
                winner = judgeWinner();
            }
        }
    }

    // If current player is player1,then player2 wins, and vice versa.
    private NimPlayer judgeWinner() {
        if (currentPlayer == player1)
            return player2;
        else
            return player1;
    }

    // Display the number of stones in "*"
    private void displayStones() {
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
        System.out.println(winner.getGivenName() + " " +
                             winner.getFamilyName() + " wins!");
    }

    public NimPlayer advancedStart() {
        for (int i = 0; i < available.length; i++) {
            available[i] = true;
        }
        System.out.println("\nInitial stone count: " + initialNumber);
        System.out.print("Stones display:");
        advancedDisplayStones(available);
        System.out.println("Player 1: " + player1.getGivenName() 
                                + " " + player1.getFamilyName());
        System.out.println("Player 2: " + player2.getGivenName() 
                                + " " + player2.getFamilyName());
        while (initialNumber > 0) {
            advancedTurn(player1);
            advancedTurn(player2);
        }
        gameOver();
        return winner;
    }

    public void advancedTurn(NimPlayer player) {
        //if lastMove=null,means AI first,else AI second
        int[] stoneToMove = new int[2];
        boolean removeStoneOutsideRange = true;
        if (initialNumber > 0) {
            currentPlayer = player;
            while (removeStoneOutsideRange) {
                System.out.print("\n" + initialNumber + " stones left:");
                advancedDisplayStones(available);
                System.out.println(
                    currentPlayer.getGivenName()
                    + "'s turn - which to remove?");
                if (currentPlayer.getType() == 0) {
                    try {
                        lastMove = currentPlayer.advancedMove(available, scanner);
                        String[] userInput = lastMove.split(" ");
                        stoneToMove[0] = Integer.valueOf(userInput[0]);
                        stoneToMove[1] = Integer.valueOf(userInput[1]);
                        available = advancedRemoveStones(available, stoneToMove);
                        initialNumber = initialNumber - stoneToMove[1];
                        removeStoneOutsideRange = false;
                    } catch (Exception e) {
                        System.out.println("Invalid move.");
                    }
                } else if (currentPlayer.getType() == 1) {
                    lastMove=currentPlayer.advancedMove(available,lastMove);
                    String[] AIInput = lastMove.split(" ");
                    stoneToMove[0] = Integer.valueOf(AIInput[0]);
                    stoneToMove[1] = Integer.valueOf(AIInput[1]);
                    try {
                        available = advancedRemoveStones(available, stoneToMove);
                        initialNumber=initialNumber-stoneToMove[1];
                        removeStoneOutsideRange = false;
                    }catch (Exception e){
                        System.out.println("AISB");
                    }
                }
            }
            //When no stones left, then the method judgeWinner() returns the winner
            if (initialNumber <= 0) {
                winner = advancedJudgeWinner();
                lastMove=null;
            }
        }
    }
     //advanced method to remove stones                                                                                                                                                                                                                     
    private boolean[] advancedRemoveStones(boolean[] available,int[] stoneToMove) 
        throws Exception {
        if (! (stoneToMove[0] >= 1 && stoneToMove[0] <= available.length))
            throw new Exception();
        if (stoneToMove[1] != 1 && stoneToMove[1] != 2)
            throw new Exception();
        int firstPosition = stoneToMove[0] - 1;
        int secondPosition = firstPosition + stoneToMove[1] - 1;
        if (available[firstPosition] == false || available[secondPosition] == false)
            throw new Exception();
        if (secondPosition > available.length)
            throw new Exception();
        available[firstPosition] = false;
        available[secondPosition] = false;
        return available;
    }

    //advanced method to display stones
    public void advancedDisplayStones(boolean[] available) {
        if (initialNumber > 0) {
            for (int i = 0; i < available.length; i++) {
                if (available[i] == true)
                    System.out.print(" <" + (i + 1) + ",*>");
                else System.out.print(" <" + (i + 1) + ",x>");
            }
            System.out.println();
        }
    }


    public NimPlayer advancedJudgeWinner() {
        return currentPlayer;
    }


}
