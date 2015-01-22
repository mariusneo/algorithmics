package topcoder.srm.div1.srm230;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Problem Statement
 *     
 * You have implemented a sorting algorithm that requires exactly c*n*lg(n) nanoseconds to sort n integers. Here lg denotes the base-2 logarithm. Given time nanoseconds, return the largest double n such that c*n*lg(n) <= time.
 * Definition
 *     
 * Class:
 * SortEstimate
 * Method:
 * howMany
 * Parameters:
 * int, int
 * Returns:
 * double
 * Method signature:
 * double howMany(int c, int time)
 * (be sure your method is public)
 * Limits
 *     
 * Time limit (s):
 * 2.000
 * Memory limit (MB):
 * 64
 * Notes
 * -
 * lg(n) = ln(n)/ln(2) where ln denotes the natural log.
 * -
 * Your return value must have a relative or absolute error less than 1e-9.
 * Constraints
 * -
 * c will be between 1 and 100 inclusive.
 * -
 * time will be between 1 and 2000000000 inclusive.
 * Examples
 * 0)
 * <p/>
 *     
 * 1
 * 8
 * Returns: 4.0
 * Given 8 nanoseconds we can sort 4 integers since
 * 1*4*lg(4) = 4*2 = 8
 * 1)
 * <p/>
 *     
 * 2
 * 16
 * Returns: 4.0
 * Now that c = 2 we need twice as many nanoseconds to sort 4 integers.
 * 2)
 * <p/>
 *     
 * 37
 * 12392342
 * Returns: 23104.999312341137
 * We can almost sort 23105 integers, but not quite.
 * 3)
 * <p/>
 *     
 * 1
 * 2000000000
 * Returns: 7.637495090348122E7
 * Largest possible return.
 * This problem statement is the exclusive and proprietary property of TopCoder, Inc. Any unauthorized use or reproduction of this information without the prior written consent of TopCoder, Inc. is strictly prohibited. (c)2003, TopCoder, Inc. All rights reserved.
 * <p/>
 * <p/>
 * TAG : Binary search
 */
public class SortEstimate {
    public double howMany(int c, int time) {
        double lo = 1.0d;
        double hi = 1e+10d;

        for (int repeat = 0; repeat < 100; repeat++) {
            double n = lo + (hi - lo) / 2;

            double log2n = Math.log(n) / Math.log(2);
            if (n * c * log2n > time) {
                hi = n;
            } else {
                lo = n;
            }
        }
        return lo;
    }

    @Test
    public void test0() {
        SortEstimate se = new SortEstimate();
        double result = se.howMany(1, 8);
        Assert.assertTrue(Math.abs(4.0 - result) < 1e-9);
    }

}