package org.mg.problems;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 1. Given a brick of size 1 x 4, how many ways you can construct a wall of size N x 4
 * Let me clarify the question, as shown in figure 1 X 4 brick can be used as 4 X 1 brick also,
 * and we have a wall of N X 4 or 4 X N unit.
 */
public class Wall {

    public static void main(String[] args) {
        Wall wall = new Wall();
        wall.countWays(10);
    }

    public int countWays(int n) {
        int[] ways = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            if (i < 4) {
                ways[i] = 1;
            } else if (i == 4) {
                ways[i] = 2;
            } else {
                ways[i] = ways[i - 1] + ways[i - 4];
            }
        }

        System.out.println(Arrays.stream(ways).mapToObj(Integer::toString).collect(Collectors.joining(" ")));
        return ways[n];
    }
}
