package topcoder.srm.div1.srm148;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.regex.*;
import java.text.*;
import java.math.*;

/**

 Problem Statement
     
 Create a class DivisorDigits containing a method howMany which takes an int number and returns how many digits in number divide evenly into number itself.
 Definition
     
 Class:
 DivisorDigits
 Method:
 howMany
 Parameters:
 int
 Returns:
 int
 Method signature:
 int howMany(int number)
 (be sure your method is public)
 Limits
     
 Time limit (s):
 2.000
 Memory limit (MB):
 64
 Notes
 -
 No number is divisible by 0.
 Constraints
 -
 number will be between 10000 and 999999999.
 Examples
 0)

     
 12345
 Returns: 3
 12345 is divisible by 1, 3, and 5.
 1)

     
 661232
 Returns: 3
 661232 is divisible by 1 and 2.
 2)

     
 52527
 Returns: 0
 52527 is not divisible by 5, 2, or 7.
 3)

     
 730000000
 Returns: 0
 Nothing is divisible by 0. In this case, the number is also not divisible by 7 or 3.
 This problem statement is the exclusive and proprietary property of TopCoder, Inc. Any unauthorized use or reproduction of this information without the prior written consent of TopCoder, Inc. is strictly prohibited. (c)2003, TopCoder, Inc. All rights reserved.
 */
public class DivisorDigits
{
    private DivisorDigits dd;

    public int howMany(int number)
    {
        int count = 0;
        int anumber = number;
        while (anumber > 0) {
            int digit = anumber % 10;
            if (digit != 0 && number % digit == 0){
                count++;
            }
            anumber = anumber / 10;
        }
        return count;
    }

    @Before
    public void setup(){
        dd = new DivisorDigits();
    }

    @Test
    public void test0(){
        int number = 12345;
        int digits = dd.howMany(number);
        Assert.assertEquals(3, digits);
    }

}