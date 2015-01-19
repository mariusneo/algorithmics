package topcoder.srm.div1.srm145;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

/**
 * Problem Statement
 *     
 * You have a certain amount of money to give out as a bonus to employees. The trouble is, who do you pick to receive what bonus? You decide to assign a number of points to each employee, which corresponds to how much they helped the company in the last year. You are given an int[] points, where each element contains the points earned by the corresponding employee (i.e. points[0] is the number of points awarded to employee 0). Using this, you are to calculate the bonuses as follows:
 * - First, add up all the points, this is the pool of total points awarded. - Each employee gets a percentage of the bonus money, equal to the percentage of the point pool that the employee got. - Employees can only receive whole percentage amounts, so if an employee's cut of the bonus has a fractional percentage, truncate it. - There may be some percentage of bonus left over (because of the fractional truncation). If n% of the bonus is left over, give the top n employees 1% of the bonus. There will be no more bonus left after this. If two or more employees with the same number of points qualify for this "extra" bonus, but not enough bonus is left to give them all an extra 1%, give it to the employees that come first in points.
 * The return value should be a int[], one element per employee in the order they were passed in. Each element should be the percent of the bonus that the employee gets.
 * Definition
 *     
 * Class:
 * Bonuses
 * Method:
 * getDivision
 * Parameters:
 * int[]
 * Returns:
 * int[]
 * Method signature:
 * int[] getDivision(int[] points)
 * (be sure your method is public)
 * Limits
 *     
 * Time limit (s):
 * 2.000
 * Memory limit (MB):
 * 64
 * Constraints
 * -
 * points will have between 1 and 50 elements, inclusive.
 * -
 * Each element of points will be between 1 and 500, inclusive.
 * Examples
 * 0)
 * <p>
 *     
 * {1,2,3,4,5}
 * Returns: { 6,  13,  20,  27,  34 }
 * The total points in the point pool is 1+2+3+4+5 = 15. Employee 1 gets 1/15 of the total pool, or 6.66667%, Employee 2 gets 13.33333%, Employee 3 gets 20% (exactly), Employee 4 gets 26.66667%, and Employee 5 gets 33.33333%. After truncating, the percentages look like: {6,13,20,26,33} Adding up all the fractional percentages, we see there is 2% in extra bonuses, which go to the top two scorers. These are the employees who received 4 and 5 points.
 * 1)
 * <p>
 *     
 * {5,5,5,5,5,5}
 * Returns: { 17,  17,  17,  17,  16,  16 }
 * The pool of points is 30. Each employee got 1/6 of the total pool, which translates to 16.66667%. Truncating for all employees, we are left with 4% in extra bonuses. Because everyone got the same number of points, the extra 1% bonuses are assigned to the four that come first in the array.
 * 2)
 * <p>
 *     
 * {485, 324, 263, 143, 470, 292, 304, 188, 100, 254, 296,
 * 255, 360, 231, 311, 275,  93, 463, 115, 366, 197, 470}
 * Returns:
 * { 8,  6,  4,  2,  8,  5,  5,  3,  1,  4,  5,  4,  6,  3,  5,  4,  1,  8,
 * 1,  6,  3,  8 }
 * <p>
 * This problem statement is the exclusive and proprietary property of TopCoder, Inc. Any unauthorized use or reproduction of this information without the prior written consent of TopCoder, Inc. is strictly prohibited. (c)2003, TopCoder, Inc. All rights reserved.
 */
public class Bonuses {
    private Bonuses bonuses;

    public int[] getDivision(int[] points)
    {
        int total = 0;
        for (int point : points){
            total += point;
        }

        double[] division = new double[points.length];
        int sumPercents = 0;
        Map<Integer, Double> index2percent = new HashMap<>();
        for (int i = 0;i< division.length;i++){
            division[i] = points[i] * 100.0 / total;
            sumPercents += division[i];
            index2percent.put(i, division[i]);
        }




        if (sumPercents < 100){
            Stream<Map.Entry<Integer, Double>> st = index2percent.entrySet().stream();
            List<Integer> sortedIndexes = new ArrayList<>();

            Comparator<Map.Entry<Integer, Double>> byValueDescending = (e1, e2) -> -1 * Double.compare(
                    e1.getValue(), e2.getValue());

            st.sorted(byValueDescending)
                    .forEach(e -> sortedIndexes.add(e.getKey()));

            for (int i= 0; i< 100 - sumPercents;i++){
                division[sortedIndexes.get(i)]++;
            }
        }

        return Arrays.stream(division).mapToInt(x -> (int)x).toArray();
    }

    @Before
    public void setup(){
        bonuses = new Bonuses();
    }

    @Test
    public void test0(){
        int[] points = {1,2,3,4,5};
        int[] division = bonuses.getDivision(points);
        int[] expectedDivision = { 6,  13,  20,  27,  34 };
        Assert.assertArrayEquals(expectedDivision, division);
    }

    @Test
    public void test2(){
        int[] points = {485, 324, 263, 143, 470, 292, 304, 188, 100, 254, 296,
             255, 360, 231, 311, 275,  93, 463, 115, 366, 197, 470};
        int[] expectedDivision = { 8,  6,  4,  2,  8,  5,  5,  3,  1,  4,  5,  4,  6,  3,  5,  4,  1,  8,
             1,  6,  3,  8 };
        int[] division = bonuses.getDivision(points);
        Assert.assertArrayEquals(expectedDivision, division);

    }
}
