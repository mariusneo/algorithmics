package topcoder.srm.div2.srm558;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Solution taken from the user lordbogy.
 * As it can be seen in the {@link #test5()} method, the solution will fail to find the optimal solution in all
 * situation due to the fact that Dynamic Programming technique was not used to really check all the solutions.
 */
public class StampLordBogy {
    public boolean eq(char c1, char c2) {
        return c1 == c2 || c1 == '*' || c2 == '*';
    }

    public int getMinimumCost(String col, int c1, int c2) {
        int n = col.length();
        int cst = c1 + c2 * n;

        for (int len = n; len >= 2; len--) {
            char x;
            int i = 0;
            int ccst = 0;
            boolean good = true;

            while (i < n) {
                int slen = 0;
                while (i < n && col.charAt(i) == '*') {
                    i++;
                    slen++;
                }
                if (i == n && slen >= len) {
                    ccst += (slen / len + (slen % len != 0 ? 1 : 0)) * c2;
                    break;
                } else if (i == n) {
                    good = false;
                    break;
                }

                x = col.charAt(i);
                while (i < n) {
                    if ((col.charAt(i) == '*' && slen < n) || col.charAt(i) == x) {
                        i++;
                        slen++;
                    } else break;
                }
                if (i < n && i + len > n) {
                    int j;
                    for (j = i; j < n; ++j) {
                        if (col.charAt(j) != '*') break;
                    }
                    if (j == n) {
                        slen += n - i + 1;
                        i = n;
                    }
                }

                if (slen >= len) {
                    ccst += (slen / len + (slen % len != 0 ? 1 : 0)) * c2;
                } else {
                    good = false;
                    break;
                }

            }

            if (good) cst = Math.min(cst, len * c1 + ccst);
        }


        return cst;
    }

//    @Test
//    public void test4(){
//        StampLordBogy stamp = new StampLordBogy();
//        int minCost  = stamp.getMinimumCost("*R*RG*G*GR*RGG*G*GGR***RR*GG", 7, 1);
//        Assert.assertEquals(30, minCost);
//    }

    @Test
    public void test5() {
        StampLordBogy stamp = new StampLordBogy();
        int minCost = stamp.getMinimumCost("B*BB**G*BB", 1, 5);
        Assert.assertEquals(23, minCost);
    }
}
