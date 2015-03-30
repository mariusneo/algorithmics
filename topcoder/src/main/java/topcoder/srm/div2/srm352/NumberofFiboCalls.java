package topcoder.srm.div2.srm352;

import org.junit.Assert;
import org.junit.Test;

/**
 * Problem Statement
 *     
 * Look at the following pseudo-code, which computes the n-th Fibonacci number:
 * int fibonacci(int n)
 * begin
 * if n equals 0
 * begin
 * print(0)
 * return 0
 * end
 * if n equals 1
 * begin
 * print(1)
 * return 1
 * end
 * return fibonacci(n - 1) + fibonacci(n - 2)
 * end
 * For example, if one calls fibonacci(3), then the following will happen:
 * fibonacci(3) calls fibonacci(2) and fibonacci(1) (the first call).
 * fibonacci(2) calls fibonacci(1) (the second call) and fibonacci(0).
 * The second call of fibonacci(1) prints 1 and returns 1.
 * fibonacci(0) prints 0 and returns 0.
 * fibonacci(2) gets the results of fibonacci(1) and fibonacci(0) and returns 1.
 * The first call of fibonacci(1) prints 1 and returns 1.
 * fibonacci(3) gets the results of fibonacci(2) and fibonacci(1) and returns 2.
 * In total, '1' will be printed twice and '0' will be printed once.
 * We want to know how many times '0' and '1' will be printed for a given n. You are to return a int[] which contains exactly two elements. The first and second elements of the return value must be equal to the number of times '0' and '1', respectively, will be printed during a fibonacci(n) call.
 * Definition
 *     
 * Class:
 * NumberofFiboCalls
 * Method:
 * fiboCallsMade
 * Parameters:
 * int
 * Returns:
 * int[]
 * Method signature:
 * int[] fiboCallsMade(int n)
 * (be sure your method is public)
 * Limits
 *     
 * Time limit (s):
 * 2.000
 * Memory limit (MB):
 * 64
 * Constraints
 * -
 * n will be between 0 and 40, inclusive.
 * Examples
 * 0)
 * <p/>
 *     
 * 0
 * Returns: {1, 0 }
 * If I call the Fibonacci function with n = 0, it just calls the 1st base case. Hence, the result is {1,0}.
 * 1)
 * <p/>
 *     
 * 3
 * Returns: {1, 2 }
 * The test case given in the problem statement.
 * 2)
 * <p/>
 *     
 * 6
 * Returns: {5, 8 }
 * <p/>
 * 3)
 * <p/>
 *     
 * 22
 * Returns: {10946, 17711 }
 * <p/>
 * This problem statement is the exclusive and proprietary property of TopCoder, Inc. Any unauthorized use or reproduction of this information without the prior written consent of TopCoder, Inc. is strictly prohibited. (c)2003, TopCoder, Inc. All rights reserved.
 * TAG : DYNAMIC PROGRAMMING
 */
public class NumberofFiboCalls {
    public int[] fiboCallsMade(int n) {
        if (n == 0) return new int[]{1, 0};
        if (n == 1) return new int[]{0, 1};
        if (n == 2) return new int[]{1, 1};

        int[] counts0 = new int[n + 1];
        int[] counts1 = new int[n + 1];
        counts0[0] = 1;
        counts1[1] = 1;
        counts0[2] = 1;
        counts1[2] = 1;
        for (int i = 3; i <= n; i++) {
            counts0[i] = counts0[i - 1] + counts0[i - 2];
            counts1[i] = counts1[i - 1] + counts1[i - 2];
        }

        return new int[]{counts0[n], counts1[n]};
    }

    @Test
    public void test2() {
        NumberofFiboCalls fibo = new NumberofFiboCalls();
        int[] result = fibo.fiboCallsMade(6);
        Assert.assertArrayEquals(new int[]{5, 8}, result);
    }

}