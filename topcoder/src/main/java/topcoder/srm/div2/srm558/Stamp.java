package topcoder.srm.div2.srm558;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Problem Statement
 *     
 * Little Fox Jiro has a rectangular board. On the board there is a row of N unit cells. The cells are numbered 0 through N-1 from the left to the right. Initially, the cells are not colored. Jiro must color each of the cells red, green, or blue.
 * <p/>
 * You are given a String desiredColor with N characters. For each i, character i of desiredColor represents the color Jiro must use for cell i. If a character is one of 'R' (as red), 'G' (as green), and 'B' (as blue), it means that Jiro must use that particular color. If a character is '*', Jiro may use any of the three colors for the particular cell.
 * <p/>
 * You are also given the ints stampCost and pushCost that describe the cost of the coloring process. The coloring process consists of two phases. In the first phase, Jiro must pick a single stamp he will then use to color all the cells. The length L of the stamp can be any integer between 1 and N, inclusive. A stamp of length L costs L*stampCost.
 * <p/>
 * In the second phase, Jiro must repeatedly use the stamp to color the cells. Each use of the stamp works as follows:
 * Jiro picks one of the three colors and pushes the stamp into ink of the chosen color C.
 * Jiro picks a segment of L contiguous cells such that each of them is either uncolored or already has the color C. The segment must be completely inside the board. That is, the leftmost cell of the segment must be one of the cells 0 through N-L.
 * Jiro pushes the stamp onto the chosen segment of cells. All the cells now have color C.
 * Each use of the stamp costs pushCost.
 * <p/>
 * Return the smallest possible total cost of coloring all the cells using the above process.
 * Definition
 *     
 * Class:
 * Stamp
 * Method:
 * getMinimumCost
 * Parameters:
 * String, int, int
 * Returns:
 * int
 * Method signature:
 * int getMinimumCost(String desiredColor, int stampCost, int pushCost)
 * (be sure your method is public)
 * Limits
 *     
 * Time limit (s):
 * 2.000
 * Memory limit (MB):
 * 64
 * Constraints
 * -
 * desiredColor will contain between 1 and 50 characters, inclusive.
 * -
 * Each character of desiredColor will be either 'R' or 'G' or 'B' or '*'.
 * -
 * stampCost will be between 1 and 100,000, inclusive.
 * -
 * pushCost will be between 1 and 100,000, inclusive.
 * Examples
 * 0)
 * <p/>
 *     
 * "RRGGBB"
 * 1
 * 1
 * Returns: 5
 * The optimal solution is to choose L=2 and then stamp three times: using red color for cells [0,1], green for [2,3], and blue for [4,5]. The stamp costs 2*1 = 2, each of the three uses costs 1, so the total costs is 2*1 + 3*1 = 5.
 * 1)
 * <p/>
 *     
 * "R**GB*"
 * 1
 * 1
 * Returns: 5
 * The optimal solution is the same as in the previous example. Note that you must color all the cells, so choosing L=1 and then using the stamp three times is not a valid solution.
 * 2)
 * <p/>
 *     
 * "BRRB"
 * 2
 * 7
 * Returns: 30
 * Also, note that once a cell is colored, you are not allowed to stamp over it using a different color. Therefore, you can only choose L=1 in this case.
 * 3)
 * <p/>
 *     
 * "R*RR*GG"
 * 10
 * 58
 * Returns: 204
 * It is allowed to stamp the same cell multiple times if all of those stamps use the same color.
 * 4)
 * <p/>
 *     
 * "*B**B**B*BB*G*BBB**B**B*"
 * 5
 * 2
 * Returns: 33
 * <p/>
 * 5)
 * <p/>
 *     
 * "*R*RG*G*GR*RGG*G*GGR***RR*GG"
 * 7
 * 1
 * Returns: 30
 * <p/>
 * This problem statement is the exclusive and proprietary property of TopCoder, Inc. Any unauthorized use or reproduction of this information without the prior written consent of TopCoder, Inc. is strictly prohibited. (c)2003, TopCoder, Inc. All rights reserved.
 */
public class Stamp {
    public int getMinimumCost(String desiredColor, int stampCost, int pushCost) {


        int i = 0;
        Character firstColor = null;
        while (i < desiredColor.length() && firstColor == null) {
            char c = desiredColor.charAt(i);
            if (c == 'R' || c == 'G' || c == 'B') {
                firstColor = c;
                break;
            }
            i++;
        }
        int maxStampCount = 1;
        if (firstColor == null) {
            maxStampCount = desiredColor.length();
        } else {
            i = 0;
            int firstPreviousWildcardIndex = -1;
            int colorLastIndex = -1;
            char color = firstColor;
            int count = 0;
            while (i < desiredColor.length()) {
                char c = desiredColor.charAt(i);
                if (c == '*') {
                    if (firstPreviousWildcardIndex == -1 || desiredColor.charAt(i - 1) != '*') {
                        firstPreviousWildcardIndex = i;
                    }
                    count++;
                } else if (color == c) {
                    colorLastIndex = i;
                    count++;
                } else {
                    if (count > maxStampCount) {
                        maxStampCount = count;
                    }

                    color = c;
                    count = colorLastIndex < firstPreviousWildcardIndex ? i - firstPreviousWildcardIndex + 1 : 1;
                    firstPreviousWildcardIndex = -1;
                }

                i++;
            }

            if (count > maxStampCount) {
                maxStampCount = count;
            }
        }

        int count = maxStampCount;
        // Iterate downwards to find the minimum cost
        int minCost = Integer.MAX_VALUE;
        Character color = firstColor;
        while (count > 0) {
            int cost = count * stampCost + pushCost;
            i = count;
            while (i < desiredColor.length()) {
                if (color != null) {
                    if (desiredColor.charAt(i) == color) {
                        i += 1;
                    }
                }
            }
        }

        return 0;
    }


    @Test
    public void test0() {
        Stamp stamp = new Stamp();
        //int minCost = stamp.getMinimumCost("RRGGBB", 1, 1);
        int minCost = stamp.getMinimumCost("*B**B**B*BB*G*BBB**B**B*", 1, 1);
        Assert.assertEquals(5, minCost);
    }
}