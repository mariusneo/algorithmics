package topcoder.srm.div2.srm149;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

/**
 * Problem Statement
 *     
 * In documents, it is frequently necessary to write monetary amounts in a standard format. We have decided to format amounts as follows:
 * the amount must start with '$'
 * the amount should have a leading '0' if and only if it is less then 1 dollar.
 * the amount must end with a decimal point and exactly 2 following digits.
 * the digits to the left of the decimal point must be separated into groups of three by commas (a group of one or two digits may appear on the left).
 * Create a class FormatAmt that contains a method amount that takes two int's, dollars and cents, as inputs and returns the properly formatted String.
 * Definition
 *     
 * Class:
 * FormatAmt
 * Method:
 * amount
 * Parameters:
 * int, int
 * Returns:
 * String
 * Method signature:
 * String amount(int dollars, int cents)
 * (be sure your method is public)
 * Limits
 *     
 * Time limit (s):
 * 2.000
 * Memory limit (MB):
 * 64
 * Notes
 * -
 * One dollar is equal to 100 cents.
 * Constraints
 * -
 * dollars will be between 0 and 2,000,000,000 inclusive
 * -
 * cents will be between 0 and 99 inclusive
 * Examples
 * 0)
 * <p/>
 *     
 * 123456
 * 0
 * Returns: "$123,456.00"
 * Note that there is no space between the $ and the first digit.
 * 1)
 * <p/>
 *     
 * 49734321
 * 9
 * Returns: "$49,734,321.09"
 * <p/>
 * 2)
 * <p/>
 *     
 * 0
 * 99
 * Returns: "$0.99"
 * Note the leading 0.
 * 3)
 * <p/>
 *     
 * 249
 * 30
 * Returns: "$249.30"
 * <p/>
 * 4)
 * <p/>
 *     
 * 1000
 * 1
 * Returns: "$1,000.01"
 * <p/>
 * This problem statement is the exclusive and proprietary property of TopCoder, Inc. Any unauthorized use or reproduction of this information without the prior written consent of TopCoder, Inc. is strictly prohibited. (c)2003, TopCoder, Inc. All rights reserved.
 */
public class FormatAmt {
    private FormatAmt formatAmt;

    public String amount(int dollars, int cents) {
        Stack<Integer> stack = new Stack<>();
        do {
            int digit = dollars % 10;
            dollars = dollars / 10;
            stack.add(digit);
        } while (dollars > 0);

        String format = "$";
        int index = 0;
        while (!stack.empty()) {
            if (index == 3) {
                format += ",";
                index = 0;
            }
            format += stack.pop();
            index++;
        }

        int digit2 = cents % 10;
        cents /= 10;
        int digit1 = cents % 10;
        format += "." + digit1 + digit2;
        return format;
    }

    @Before
    public void setup() {
        formatAmt = new FormatAmt();
    }

    @Test
    public void test0() {
        String format = formatAmt.amount(123456, 0);
        Assert.assertEquals("$123,456.00", format);
    }

    @Test
    public void test3() {
        String format = formatAmt.amount(249, 30);
        Assert.assertEquals("$249.30", format);
    }

}