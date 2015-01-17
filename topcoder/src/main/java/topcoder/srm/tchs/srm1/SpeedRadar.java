package topcoder.srm.tchs.srm1;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Problem Statement
 *     
 * A speed radar is installed in a highway zone where the maximum speed limit is maxLimit mph, and the minimum speed
 * limit is minLimit mph. Any reading that is strictly above or below this interval is an infringement.
 * Periodically, the radar readings are analyzed to make sure that the sensors are working properly.
 * It is assumed that most drivers obey speed limits, and therefore, the radar will be considered faulty if more
 * than 10% of its readings are infringements.
 * Given the radar readings over a period of time, return the average speed of all cars that are driving within the
 * speed limits. If the radar is faulty, return 0.0.
 * Definition
 *     
 * Class:
 * SpeedRadar
 * Method:
 * averageSpeed
 * Parameters:
 * int, int, int[]
 * Returns:
 * double
 * Method signature:
 * double averageSpeed(int minLimit, int maxLimit, int[] readings)
 * (be sure your method is public)
 * Limits
 *     
 * Time limit (s):
 * 2.000
 * Memory limit (MB):
 * 64
 * Notes
 * -
 * The returned value must be accurate to within a relative or absolute value of 1E-9.
 * Constraints
 * -
 * maxLimit will be between 1 and 200, inclusive.
 * -
 * minLimit will be between 1 and maxLimit, inclusive.
 * -
 * readings will contain between 1 and 50 elements, inclusive.
 * -
 * Each element of readings will be between 1 and 200, inclusive.
 * Examples
 * 0)
 * <p/>
 *     
 * 1
 * 50
 * {45, 40, 50}
 * Returns: 45.0
 * With all drivers within the speed limits, the return value is just the average speed.
 * 1)
 * <p/>
 *     
 * 1
 * 50
 * {42,43,44,45,46,47,48,49,50,51}
 * Returns: 46.0
 * There is only one driver infringing the limit, and it represents 10% of the total readings. The average speed of the other readings is 46.0.
 * 2)
 * <p/>
 *     
 * 1
 * 50
 * {42,46,48,50,52}
 * Returns: 0.0
 * Only one reading is outside the given limits, but it represents 20% of the total number of readings. We therefore assume that the radar is not working and return 0.0.
 * 3)
 * <p/>
 *     
 * 20
 * 60
 * {25,45,45,43,24,9,51,55,60,34,61,23,40,40,47,49,33,23,47,54,54}
 * Returns: 41.68421052631579
 * <p/>
 * This problem statement is the exclusive and proprietary property of TopCoder, Inc. Any unauthorized use or reproduction of this information without the prior
 * written consent of TopCoder, Inc. is strictly prohibited. (c)2003, TopCoder, Inc. All rights reserved.
 */
public class SpeedRadar {
    private SpeedRadar speedRadar;

    public double averageSpeed(int minLimit, int maxLimit, int[] readings) {
        double average = 0.0d;

        int tenPercentCount = readings.length / 10;
        int invalidReadingCount = 0;
        double sum = 0;
        for (int i = 0; i < readings.length; i++) {
            double reading = readings[i];
            if (reading < minLimit || reading > maxLimit) {
                invalidReadingCount++;
                if (invalidReadingCount > tenPercentCount) {
                    break;
                }
            } else {
                sum += reading;
            }
        }

        if (invalidReadingCount <= tenPercentCount) {
            average = sum / (readings.length - invalidReadingCount);
        }


        return average;
    }

    @Before
    public void setup() {
        speedRadar = new SpeedRadar();
    }

    @Test
    public void test1() {
        double averageSpeed = speedRadar.averageSpeed(1, 50, new int[]{45, 40, 50});
        assertEquals(45.0, averageSpeed);
    }

    @Test
    public void test2() {
        double averageSpeed = speedRadar.averageSpeed(1,
                50,
                new int[]{42, 46, 48, 50, 52});
        assertEquals(0.0, averageSpeed);
    }

    @Test
    public void test3(){
        double averageSpeed = speedRadar.averageSpeed(20,
                60,
        new int[]{25,45,45,43,24,9,51,55,60,34,61,23,40,40,47,49,33,23,47,54,54});
        assertEquals(41.68421052631579, averageSpeed);
    }
}
