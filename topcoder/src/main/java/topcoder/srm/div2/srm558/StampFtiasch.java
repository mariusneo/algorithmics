package topcoder.srm.div2.srm558;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Dynamic programming solution of the {@link topcoder.srm.div2.srm558.Stamp} problem taken from the
 * TopCoder user ftiasch.
 */
public class StampFtiasch {
    public int getMinimumCost(String colors, int stampCost, int pushCost) {
        int n = colors.length();
        boolean[][] valid = new boolean[n][n];
        // Check all the subsequences of the colors string whether they
        // have at most one color (i.e. : 'B*' or 'BBB' or '****'
        for (int i = 0; i < n; ++i) {
            Set<Character> set = new HashSet<>();
            for (int j = i; j < n; ++j) {
                if (colors.charAt(j) != '*') {
                    set.add(colors.charAt(j));
                }
                valid[i][j] = set.size() <= 1;
            }
        }

        int result = Integer.MAX_VALUE;

        // Check all the lengths that can be applied on the string.
        // A possible optimisation would be to go only up to the maximum possible size of the stamp
        // which can be calculated in the fashion already implemented in {@link Stamp} problem.
        for (int length = 1; length <= n; ++length) {
            int[] minimum = new int[n + 1];
            Arrays.fill(minimum, Integer.MAX_VALUE);
            minimum[0] = 0;
            // use of dynamic programming technique for finding the most efficient way to paint the cells of the row
            // Go through ALL the subsequences (i,j) (i = 1,n ; j= i+lenght-1, n) of the string and find the
            // representation which requires the minimum amount of stamps applied on it
            // In this fashion can be found solutions for the following problem :
            // B*BB**G*BB
            // with the stamps : B*B, *BB, **G, *BB
            for (int i = 0; i < n; ++i) {
                if (minimum[i] < Integer.MAX_VALUE) {
                    for (int j = i + length - 1; j < n; ++j) {
                        if (valid[i][j]) {
                            minimum[j + 1] = Math.min(minimum[j + 1], minimum[i] + (j - i + length) / length);
                        }
                    }
                }
            }
            if (minimum[n] < Integer.MAX_VALUE) {
                result = Math.min(result, length * stampCost + minimum[n] * pushCost);
            }
        }

        return result;
    }

    @Test
    public void test4() {
        StampFtiasch stamp = new StampFtiasch();
        int minCost = stamp.getMinimumCost("*R*RG*G*GR*RGG*G*GGR***RR*GG", 7, 1);
        Assert.assertEquals(30, minCost);
    }

    @Test
    public void test5() {
        StampFtiasch stamp = new StampFtiasch();
        int minCost = stamp.getMinimumCost("B*BB**G*BB", 1, 5);
        Assert.assertEquals(23, minCost);
    }

}
