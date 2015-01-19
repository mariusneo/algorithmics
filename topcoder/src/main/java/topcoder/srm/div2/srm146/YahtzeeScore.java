package topcoder.srm.div2.srm146;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Problem Statement
 *     
 * This task is about the scoring in the first phase of the die-game Yahtzee, where five dice are used. The score is determined by the values on the upward die faces after a roll. The player gets to choose a value, and all dice that show the chosen value are considered active. The score is simply the sum of values on active dice.
 * Say, for instance, that a player ends up with the die faces showing 2, 2, 3, 5 and 4. Choosing the value two makes the dice showing 2 active and yields a score of 2 + 2 = 4, while choosing 5 makes the one die showing 5 active, yielding a score of 5.
 * Your method will take as input a int[] toss, where each element represents the upward face of a die, and return the maximum possible score with these values.
 * Definition
 *     
 * Class:
 * YahtzeeScore
 * Method:
 * maxPoints
 * Parameters:
 * int[]
 * Returns:
 * int
 * Method signature:
 * int maxPoints(int[] toss)
 * (be sure your method is public)
 * Limits
 *     
 * Time limit (s):
 * 2.000
 * Memory limit (MB):
 * 64
 * Constraints
 * -
 * toss will contain exactly 5 elements.
 * -
 * Each element of toss will be between 1 and 6, inclusive.
 * Examples
 * 0)
 * <p>
 *     
 * { 2, 2, 3, 5, 4 }
 * Returns: 5
 * The example from the text.
 * 1)
 * <p>
 *     
 * { 6, 4, 1, 1, 3 }
 * Returns: 6
 * Selecting 1 as active yields 1 + 1 = 2, selecting 3 yields 3, selecting 4 yields 4 and selecting 6 yields 6, which is the maximum number of points.
 * 2)
 * <p>
 *     
 * { 5, 3, 5, 3, 3 }
 * Returns: 10
 * <p>
 * This problem statement is the exclusive and proprietary property of TopCoder, Inc. Any unauthorized use or reproduction of this information without the prior written consent of TopCoder, Inc. is strictly prohibited. (c)2003, TopCoder, Inc. All rights reserved.
 */
public class YahtzeeScore {
    private YahtzeeScore score;

    public int maxPoints(int[] toss) {
        if (toss.length == 0){
            return 0;
        }
        Map<Integer, Integer> dice2sum = new HashMap<>();
        for (int dice : toss){
            if (dice2sum.containsKey(dice)){
                dice2sum.put(dice, dice2sum.get(dice)+dice);
            }else{
                dice2sum.put(dice, dice);
            }
        }

        return dice2sum.entrySet().stream().max(Comparator.comparing(e -> e.getValue())).get().getValue();
    }

    @Before
    public void setup() {
        score = new YahtzeeScore();
    }

    @Test
    public void test0() {
        int[] toss = { 2, 2, 3, 5, 4 };
        int maxPoints = score.maxPoints(toss);
        Assert.assertEquals(5, maxPoints);
    }

}