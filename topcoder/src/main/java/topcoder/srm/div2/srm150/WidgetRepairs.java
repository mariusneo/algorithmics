package topcoder.srm.div2.srm150;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Problem Statement
 *     
 * When a widget breaks, it is sent to the widget repair shop, which is capable of repairing at most numPerDay widgets per day. Given a record of the number of widgets that arrive at the shop each morning, your task is to determine how many days the shop must operate to repair all the widgets, not counting any days the shop spends entirely idle.
 * For example, suppose the shop is capable of repairing at most 8 widgets per day, and over a stretch of 5 days, it receives 10, 0, 0, 4, and 20 widgets, respectively. The shop would operate on days 1 and 2, sit idle on day 3, and operate again on days 4 through 7. In total, the shop would operate for 6 days to repair all the widgets.
 * Create a class WidgetRepairs containing a method days that takes a sequence of arrival counts arrivals (of type int[]) and an int numPerDay, and calculates the number of days of operation.
 * Definition
 *     
 * Class:
 * WidgetRepairs
 * Method:
 * days
 * Parameters:
 * int[], int
 * Returns:
 * int
 * Method signature:
 * int days(int[] arrivals, int numPerDay)
 * (be sure your method is public)
 * Limits
 *     
 * Time limit (s):
 * 2.000
 * Memory limit (MB):
 * 64
 * Constraints
 * -
 * arrivals contains between 1 and 20 elements, inclusive.
 * -
 * Each element of arrivals is between 0 and 100, inclusive.
 * -
 * numPerDay is between 1 and 50, inclusive.
 * Examples
 * 0)
 * <p/>
 *     
 * { 10, 0, 0, 4, 20 }
 * 8
 * Returns: 6
 * The example above.
 * 1)
 * <p/>
 *     
 * { 0, 0, 0 }
 * 10
 * Returns: 0
 * <p/>
 * 2)
 * <p/>
 *     
 * { 100, 100 }
 * 10
 * Returns: 20
 * <p/>
 * 3)
 * <p/>
 *     
 * { 27, 0, 0, 0, 0, 9 }
 * 9
 * Returns: 4
 * <p/>
 * 4)
 * <p/>
 *     
 * { 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6 }
 * 3
 * Returns: 15
 * <p/>
 * This problem statement is the exclusive and proprietary property of TopCoder, Inc. Any unauthorized use or reproduction of this information without the prior written consent of TopCoder, Inc. is strictly prohibited. (c)2003, TopCoder, Inc. All rights reserved.
 */
public class WidgetRepairs {
    private WidgetRepairs wr;

    public int days(int[] arrivals, int numPerDay) {
        int days = 0;
        int remainingWidgets = 0;
        for (int widgets : arrivals) {
            remainingWidgets += widgets;

            if (remainingWidgets > 0) {
                remainingWidgets -= Math.min(remainingWidgets, numPerDay);
                days++;
            }
        }

        days += remainingWidgets / numPerDay + (remainingWidgets % numPerDay == 0 ? 0 : 1);

        return days;
    }

    @Before
    public void setup() {
        wr = new WidgetRepairs();
    }

    @Test
    public void test0() {
        int days = wr.days(new int[]{10, 0, 0, 4, 20}, 8);
        Assert.assertEquals(6, days);
    }
}