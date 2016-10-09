package org.mg.problems;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.containsInAnyOrder;

/**
 * Given two strings, this problem is about finding their longest common substring.
 * <p>
 * For the two strings:
 * <ul>
 * <li>ABACTGA</li>
 * <li>TTBACGA</li>
 * </ul>
 * <p>
 * The longest string sequence in this case is BAC
 * <p>
 * This is a problem solved via dynamic programming similar to the string distance
 * or Levenshtein distance problem
 * <p>
 * Below is presented visually the algorithm for solving this problem:
 * <p>
 * <pre>
 *           A  B  A  C  T  G  A
 *           0  0  0  0  0  0  0
 *     T  0  0  0  0  0  1  0  0
 *     T  0  0  0  0  0  1  0  0
 *     B  0  0  1  0  0  0  0  0
 *     A  0  1  0  2  0  0  0  1
 *     C  0  0  0  0  3  0  0  0
 *     G  0  0  0  0  0  0  1  0
 *     A  0  1  0  1  0  0  0  2
 * </pre>
 * <p>
 * <p>
 * The formula corresponding to each element of the matrix described above is:
 * <p>
 * <pre>
 *     a [i, j] =       0 , if s1[i] != s2[j]
 *                      a[i-1, j-1] + 1, if s1[i] = s2[j]
 *
 *     where s1 and s2 are the string vectors being compared
 * </pre>
 * <p>
 * The element from the matrix with the maximum value corresponds to the length of the longest common sequence
 * between the two strings. This means that by taking either s1[i-maxLength, i] or s2[j-maxLength, j] we get the
 * actual common sequence.
 */
public class LongestCommonSubstring {

    public static List<String> longestCommonSubstring(String s1, String s2) {

        int[][] a = new int[s1.length() + 1][s2.length() + 1];
        IntStream.range(0, s1.length() + 1).forEach(i -> a[i][0] = 0);
        IntStream.range(0, s2.length() + 1).forEach(j -> a[0][j] = 0);

        List<Coordinate> maxCoordinates = new ArrayList<>();
        int max = 0;
        for (int i = 0; i < s1.length(); i++) {
            for (int j = 0; j < s2.length(); j++) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    a[i + 1][j + 1] = a[i][j] + 1;
                    if (a[i + 1][j + 1] == max) {
                        maxCoordinates.add(new Coordinate(i + 1, j + 1));
                    } else if (a[i + 1][j + 1] > max) {
                        maxCoordinates = new ArrayList<>();
                        maxCoordinates.add(new Coordinate(i + 1, j + 1));
                        max = a[i + 1][j + 1];
                    }
                } else {
                    a[i + 1][j + 1] = 0;
                }
            }
        }

        if (max > 0) {
            final int maxLength = max;
            return maxCoordinates.stream()
                    .map(coordinate -> s1.substring(coordinate.i - maxLength, coordinate.i))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Test
    public void testAccuracy() {
        Assert.assertThat(longestCommonSubstring("ABACTGA", "TTBACGA"), containsInAnyOrder("BAC"));
        Assert.assertThat(longestCommonSubstring("aabbcc", "aaccbb"), containsInAnyOrder("aa", "bb", "cc"));
        Assert.assertThat(longestCommonSubstring("ABBCCC", "BBACCC"), containsInAnyOrder("CCC"));
    }


    private static class Coordinate {
        int i;
        int j;

        public Coordinate(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }
}
