

/**
 * Created by Administrator on 2018/5/22.
 */
public class NimAIPlayer extends NimPlayer implements Testable {
    public NimAIPlayer() {
        super();
    }

    public NimAIPlayer(String userName, String familyName, String givenName) {
        super(userName, familyName, givenName, 1);
    }

    public int removeStone(int initialNumber, int upperBound) {
        int stoneNumber = 1;
        int k = 0;
        for (stoneNumber = 1; stoneNumber <= upperBound; stoneNumber++) {
            if (((initialNumber - stoneNumber) - 1) % (upperBound + 1) == 0) return stoneNumber;
        }
        return stoneNumber;
    }

    public String advancedMove(boolean[] available, String lastMove) {
        String res = null;
        int mid = available.length / 2;
        int midNext = mid + 1;
        if (this.isFirst == 1) {
            if (lastMove == null) {
                if (available.length % 2 == 0) {
                    int first = mid;
                    res = String.valueOf(first) + " 2";
                } else {
                    int first = mid + 1;
                    res = String.valueOf(first) + " 1";
                }
            } else {
                String[] lastInput = lastMove.split(" ");
                int lastFirst = Integer.valueOf(lastInput[0]);
                int lastSecond = lastFirst + Integer.valueOf(lastInput[1]) - 1;
                int first = available.length + 1 - lastSecond;
                res = String.valueOf(first) + " " + lastInput[1];
            }
        }
        if (this.isFirst == 2) {
            String[] lastInput = lastMove.split(" ");
            int lastFirst = Integer.valueOf(lastInput[0]);
            int lastSecond = lastFirst + Integer.valueOf(lastInput[1]) - 1;
            if (available.length % 2 == 0) {

            } else {

            }
        }
        return res;
    }
}
