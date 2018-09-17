
/*
COMP90041 Programming and Software Development
Project C: Nimsys Game
Student Name: Qianyu Guo
Student Number:921808
25 May 2018
*/
public interface AdvancedNimGame {
    public NimPlayer advancedStart();
    public void advancedTurn(NimPlayer player);
    public void advancedDisplayStones(boolean[] available);
    public NimPlayer advancedJudgeWinner();
}
