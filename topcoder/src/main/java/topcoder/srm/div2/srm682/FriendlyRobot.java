package topcoder.srm.div2.srm682;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

// code taken from donli TC user
public class FriendlyRobot {
    public int findMaximumReturns(String instructions, int changesAllowed) {

        int n = instructions.length(), m = changesAllowed;

        if (m >= n / 2) return n / 2;

        // counting the differences on the x and y axis for the instructions performed
        int[] x = new int[n + 1], y = new int[n + 1];
        for (int i = 0; i < n; i++) {
            int dx = 0, dy = 0;

            switch (instructions.charAt(i)) {
                case 'U':
                    dy = 1;
                    break;
                case 'D':
                    dy = -1;
                    break;
                case 'L':
                    dx = -1;
                    break;
                case 'R':
                    dx = 1;
                    break;
            }
            x[i + 1] = x[i] + dx;
            y[i + 1] = y[i] + dy;
        }

        // dynamic programming coming up
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], -1);
        }
        dp[0][0] = 0;
        int ans = 0;
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                ans = Math.max(ans, dp[i][j]);
                if (dp[i][j] >= 0) {
                    for (int k = i + 1; k < n; k++) {
                        int dx = x[k + 1] - x[i], dy = y[k + 1] - y[i];
                        if ((dx + dy) % 2 == 0) {
                            // the possibility for creating a new loop has been discovered (e.g. : UU or ULDR)
                            int t = (Math.abs(dx) + Math.abs(dy)) / 2;
                            if (j + t <= m) {
                                // recording a change for the instruction with the index k
                                dp[k + 1][j + t] = Math.max(dp[k + 1][j + t], dp[i][j] + 1);
                            }
                        }
                    }
                }
            }
        }


        return ans;
    }


    @Test
    public void test3() {
        FriendlyRobot fr = new FriendlyRobot();
        assertEquals(8, fr.findMaximumReturns("ULDRRLRUDUDLURLUDRUDL", 4));
    }
}