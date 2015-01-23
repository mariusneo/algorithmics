package topcoder.srm.div1.srm277;

import junit.framework.Assert;
import org.junit.Test;

import java.util.stream.IntStream;

/**
 * Problem Statement
 *     
 * Given a list of integers, find the n-th smallest number, i.e., the number that appears at index n (0-based) when they are sorted in non-descending order. The numbers will be given in intervals. For example, the intervals (1, 3) and (5, 7) represent the list of numbers { 1, 2, 3, 5, 6, 7 }. A number may be present in more than one interval, and it appears in the list once for each interval it is in. For example, the intervals (1, 4) and (3, 5) represent the list of numbers { 1, 2, 3, 3, 4, 4, 5 }.
 * The intervals will be given as two int[]s, lowerBound and upperBound. The i-th elements of these int[]s represent the smallest and largest numbers in the i-th interval, inclusive.
 * Definition
 *     
 * Class:
 * UnionOfIntervals
 * Method:
 * nthElement
 * Parameters:
 * int[], int[], int
 * Returns:
 * int
 * Method signature:
 * int nthElement(int[] lowerBound, int[] upperBound, int n)
 * (be sure your method is public)
 * Limits
 *     
 * Time limit (s):
 * 2.000
 * Memory limit (MB):
 * 64
 * Notes
 * -
 * n is 0-based, meaning that the first element is indexed 0.
 * -
 * A sequence is sorted in non-descending order if and only if for each pair of indices i and j, where i is smaller than j, the element at position i is less than or equal to the element at position j.
 * Constraints
 * -
 * lowerBound will contain between 1 and 50 elements, inclusive.
 * -
 * upperBound will contain the same number of elements as lowerBound.
 * -
 * Each element of lowerBound and upperBound will be between -2,000,000,000 and 2,000,000,000, inclusive.
 * -
 * The i-th element of lowerBound will be less than or equal to the i-th element of upperBound.
 * -
 * n will be a non-negative integer less than the total number of elements in the list, but no greater than 2,000,000,000.
 * Examples
 * 0)
 * <p/>
 *     
 * { 1, 5 }
 * { 3, 7 }
 * 4
 * Returns: 6
 * The numbers are 1, 2, 3, 5, 6 and 7. The number at index 4 is 6.
 * 1)
 * <p/>
 *     
 * { 1, 3 }
 * { 4, 5 }
 * 3
 * Returns: 3
 * <p/>
 * 2)
 * <p/>
 *     
 * { -1500000000 }
 * { 1500000000 }
 * 1500000091
 * Returns: 91
 * Watch out for overflow errors.
 * This problem statement is the exclusive and proprietary property of TopCoder, Inc. Any unauthorized use or reproduction of this information without the prior written consent of TopCoder, Inc. is strictly prohibited. (c)2003, TopCoder, Inc. All rights reserved.
 * <p/>
 * TAG : Binary Search
 */
public class UnionOfIntervals {
    public int nthElement(int[] lowerBound, int[] upperBound, int n) {
        int lo = IntStream.of(lowerBound).min().getAsInt();
        int hi = IntStream.of(upperBound).max().getAsInt();

        // if the listing of the merged intervals would be [x1, x1, x2, x2, x3, x4] and n 2
        // (corresponds to 1st occurrence of x2)
        // the algorithm is based on the the relation lastIndex(x1) < lastIndex(x2) < lastIndex(x3)
        while (lo < hi) {
            int x = lo + (hi - lo) / 2;
            // the value -1 is used as initial value for the index, because indexing for n starts at 0 (and not 1)
            int lastIndexOfx = -1;

            // count the index of the mid value by iterating each interval and see if there is an overlap or
            // if mid is greater than the upper bound of the interval
            for (int i = 0; i < lowerBound.length; i++) {
                if (x >= lowerBound[i] && x <= upperBound[i]) {
                    lastIndexOfx += x - lowerBound[i] + 1;
                }
                if (x > upperBound[i]) {
                    lastIndexOfx += upperBound[i] - lowerBound[i] + 1;
                }
            }

            // interval is shrinked in binary search fashion until lo is equal to hi
            if (lastIndexOfx < n) {
                lo = x + 1;
            } else {
                hi = x;
            }
        }

        return lo;
    }


    @Test
    public void test0() {
        UnionOfIntervals uoi = new UnionOfIntervals();
        int element = uoi.nthElement(new int[]{1, 5}, new int[]{3, 7}, 4);
        Assert.assertEquals(6, element);
    }

    @Test
    public void test1() {
        UnionOfIntervals uoi = new UnionOfIntervals();
        int element = uoi.nthElement(new int[]{1, 3}, new int[]{4, 5}, 3);
        Assert.assertEquals(3, element);
    }

    @Test
    public void test4() {
        UnionOfIntervals uoi = new UnionOfIntervals();
        int[] mergeResult = new int[]{1, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 6, 7};
        for (int index = 0; index < mergeResult.length; index++) {
            int element = uoi.nthElement(new int[]{1, 2, 3}, new int[]{5, 7, 4}, index);
            Assert.assertEquals(mergeResult[index], element);
        }
    }
}
