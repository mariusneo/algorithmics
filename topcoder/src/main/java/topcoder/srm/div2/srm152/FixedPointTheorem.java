package topcoder.srm.div2.srm152;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Problem Statement
 *     
 * The fixed-point theorem is one of those cornerstones of mathematics that reaches towards all disciplines, and oddly enough it is also closely related to the ability of any program to Quine itself (or to print out its own source code). Put simply, the fixed-point theorem states that with certain restrictions on a real-valued function F, there is always a point such that X=F(X). Taking the fixed-point theorem further, you can show that any function that meets certain restrictions will start to cycle through values if you keep on feeding it its own output (doing this with programs and their output is one way of producing programs that Quine themselves).
 * One simple function that does this is the logistic function F(X)=R*X*(1-X) in the interval [0,1] for certain values of R. For example, if you start with the value X=.25 and feed it into F to get a new X, then feed that value into F to get yet another X, and so on, the values of X that are produced will converge to a small set of values that will eventually repeat forever, called a cycle.  Your program will be given a double R between 0.1 and 3.569 inclusive. Starting with X=.25, generate the first 200,000 iterations of F using the given value of R, which will stabilize values of X. Then generate 1000 more values, and return the range of these values (highest value - lowest value). In other words, you will be finding the range of the values produced between iterations 200,001 and 201,000 inclusive.
 * Definition
 *     
 * Class:
 * FixedPointTheorem
 * Method:
 * cycleRange
 * Parameters:
 * double
 * Returns:
 * double
 * Method signature:
 * double cycleRange(double R)
 * (be sure your method is public)
 * Limits
 *     
 * Time limit (s):
 * 2.000
 * Memory limit (MB):
 * 64
 * Notes
 * -
 * Don't worry about overflow. With the given values it'll never happen.
 * Constraints
 * -
 * R will be a value between 0.1 and 3.569 inclusive.
 * -
 * R will always be a value such that the process stated above will produce a result accurate to 1e-9 (absolute or relative).
 * Examples
 * 0)
 * <p/>
 *     
 * 0.1
 * Returns: 0.0
 * At low numbers, there exists only one point in the cycle, so the answer is 0.0.
 * 1)
 * <p/>
 *     
 * 3.05
 * Returns: 0.14754098360655865
 * <p/>
 * 2)
 * <p/>
 *     
 * 3.4499
 * Returns: 0.4175631735867292
 * <p/>
 * 3)
 * <p/>
 *     
 * 3.55
 * Returns: 0.5325704489850351
 * <p/>
 * 4)
 * <p/>
 *     
 * 3.565
 * Returns: 0.5454276003030636
 * <p/>
 * 5)
 * <p/>
 *     
 * 3.5689
 * Returns: 0.5489996297493569
 * <p/>
 * 6)
 * <p/>
 *     
 * 3.00005
 * Returns: 0.004713996108955176
 * Make sure you're iterating 200,000 times.
 * This problem statement is the exclusive and proprietary property of TopCoder, Inc. Any unauthorized use or reproduction of this information without the prior written consent of TopCoder, Inc. is strictly prohibited. (c)2003, TopCoder, Inc. All rights reserved.
 */
public class FixedPointTheorem {
    private FixedPointTheorem fp;

    public double cycleRange(double R) {
        double X = 0.25;
        for (int i = 0; i < 200.000; i++) {
            X = f(X, R);
        }

        X = f(X, R);
        double min = X, max = X;
        for (int i = 1; i < 1000; i++) {
            X = f(X, R);
            min = Math.min(min, X);
            max = Math.max(max, X);
        }

        return max - min;
    }

    public double f(double X, double R) {
        return R * X * (1 - X);
    }


    @Before
    public void setup() {
        fp = new FixedPointTheorem();
    }

    @Test
    public void test0() {
        double value = fp.cycleRange(0.1);
        Assert.assertEquals(0.0, value);
    }

    @Test
    public void test1() {
        double value = fp.cycleRange(3.05);
        Assert.assertEquals(0.14754098360655865, value);
    }
}