package org.mg.problems;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.max;
import static org.hamcrest.Matchers.containsInAnyOrder;

/**
 * The longest common subsequence problem is a classic computer science problem,
 * the basis of data comparison programs such as the diff utility, and has applications in bioinformatics.
 * <p>
 * This problem is similar to the longest common substring problem, by retrieving common parts
 * from two strings. Compared to the longest common substring problem, this problem retrieves
 * not only continuous substrings, but subsequences.
 * <p>
 * Given the two strings:
 * <p>
 * <ul>
 * <li>ACGAT</li>
 * <li>ATGT</li>
 * </ul>
 * <p>
 * Their longest common substrings is:
 * <ul>
 * <li>AT</li>
 * </ul>
 * <p>
 * On the other hand, the longest common subsequence is:
 * <ul>
 * <li>AGT</li>
 * </ul>
 * <p>
 * More explanations about this problem and its corresponding solution can be found on
 * <a href="https://en.wikipedia.org/wiki/Longest_common_subsequence_problem">Wikipedia</a>
 * <p>
 * <p>
 * The algorithm for solving this problem is presented visually below:
 * <pre>
 *           A  C  G  A  T
 *        0  0  0  0  0  0
 *     A  0  1  1  1  1  1
 *     T  0  1  1  1  1  2
 *     G  0  1  1  2  2  2
 *     T  0  1  1  2  2  3
 * </pre>
 * <p>
 * The formula corresponding to each element of the matrix is :
 * <p>
 * <pre>
 *     a[i, j] =                       a[i-1, j-1] + 1, if s1[i] = s2[j]
 *                                     max(a[i-1, j], a[i, j-1]), if s1[i] != s2[j]
 *
 *     where s1 and s2 are the string vectors being compared
 * </pre>
 *
 * @see LongestCommonSubstring
 */
public class LongestCommonSubsequence {
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
                    a[i + 1][j + 1] = max(a[i][j + 1], a[i + 1][j]);
                }
            }
        }
        //System.out.println(matrixRepresentation(a));

        if (max > 0) {
            return maxCoordinates.stream()
                    .map(coordinate -> {
                        int i = coordinate.i, j = coordinate.j;
                        StringBuilder sb = new StringBuilder();
                        while (a[i][j] > 0) {
                            int prevMax = max(a[i - 1][j - 1], max(a[i - 1][j], a[i][j - 1]));
                            if (a[i - 1][j - 1] == prevMax) {
                                if (a[i - 1][j - 1] + 1 == a[i][j]) {
                                    sb.append(s1.charAt(i - 1));
                                }
                                i--;
                                j--;
                            } else if (a[i][j - 1] == prevMax) {
                                j--;
                            } else {
                                i--;
                            }
                        }
                        return sb.reverse().toString();
                    })
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    public static String matrixRepresentation(int[][] matrix) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                sb.append(String.format("%-3s", matrix[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Test
    public void testAccuracy() {
        Assert.assertThat(longestCommonSubstring("ATGT", "ACGAT"), containsInAnyOrder("AGT"));
        Assert.assertThat(longestCommonSubstring("abcdaf", "acbcf"), containsInAnyOrder("abcf"));
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
