package topcoder.srm.div2.srm151;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Problem Statement
 *     
 * Forgetting a close friend's birthday is quite embarrassing, but forgetting it two years in a row is a catastrophe. So what can a coder do to prevent this from happening again? Well, the thing he possibly can do best: code...  Given a String date (the current date) and a String[] birthdays, a list of people's birthdays and names, return a String, the date of the next occurring birthday, starting from the current date.  date is in the format "MM/DD" (quotes for clarity), where MM represents the two-digit month and DD represents the two-digit day (leading zero if necessary). Each element of birthdays is in the format "MM/DD <Name>" (quotes for clarity), where MM/DD is the date of <Name>'s birthday. <Name> is a sequence of characters from 'A'-'Z' and 'a'-'z'. There is exactly one space character between the date and <Name>. The date returned also has to be in the format "MM/DD" (quotes for clarity).
 * Definition
 *     
 * Class:
 * Birthday
 * Method:
 * getNext
 * Parameters:
 * String, String[]
 * Returns:
 * String
 * Method signature:
 * String getNext(String date, String[] birthdays)
 * (be sure your method is public)
 * Limits
 *     
 * Time limit (s):
 * 2.000
 * Memory limit (MB):
 * 64
 * Constraints
 * -
 * birthdays contains between 1 and 50 elements, inclusive.
 * -
 * Each element of birthdays contains between 7 and 50 characters, inclusive.
 * -
 * date and each element of birthdays follow the format described in the problem statement.
 * -
 * All dates are legal dates and neither date nor any date in birthdays is the 29th of February.
 * Examples
 * 0)
 * <p/>
 *     
 * "06/17"
 * {"02/17 Wernie", "10/12 Stefan"}
 * Returns: "10/12"
 * <p/>
 * 1)
 * <p/>
 *     
 * "06/17"
 * {"10/12 Stefan"}
 * Returns: "10/12"
 * <p/>
 * 2)
 * <p/>
 *     
 * "02/17"
 * {"02/17 Wernie", "10/12 Stefan"}
 * Returns: "02/17"
 * <p/>
 * 3)
 * <p/>
 *     
 * "12/24"
 * {"10/12 Stefan"}
 * Returns: "10/12"
 * <p/>
 * 4)
 * <p/>
 *     
 * "01/02"
 * {"02/17 Wernie",
 * "10/12 Stefan",
 * "02/17 MichaelJordan",
 * "10/12 LucianoPavarotti",
 * "05/18 WilhelmSteinitz"}
 * Returns: "02/17"
 * <p/>
 * This problem statement is the exclusive and proprietary property of TopCoder, Inc. Any unauthorized use or reproduction of this information without the prior written consent of TopCoder, Inc. is strictly prohibited. (c)2003, TopCoder, Inc. All rights reserved.
 */
public class Birthday {
    private Birthday b;

    public String getNext(String date, String[] birthdays) {
        String[] components = date.split("/");
        int currentMonth = Integer.parseInt(components[0]);
        int currentDay = Integer.parseInt(components[1]);

        int minMonth = 99, minDay = 99;
        int nextMonth = 99, nextDay = 99;

        for (String birthday : birthdays) {
            components = birthday.split(" ")[0].split("/");
            int month = Integer.parseInt(components[0]);
            int day = Integer.parseInt(components[1]);

            if (month < currentMonth || (month == currentMonth && day < currentDay)) {
                // date in the past
                if (month < minMonth || (month == minMonth && day < minDay)) {
                    minMonth = month;
                    minDay = day;
                }
            } else {
                // date in the future
                if (month < nextMonth || (month == nextMonth && day < nextDay)) {
                    nextMonth = month;
                    nextDay = day;
                }
            }
        }

        if (nextMonth != 99) {
            return String.format("%02d/%02d", nextMonth, nextDay);
        } else if (minDay != 99) {
            return String.format("%02d/%02d", minMonth, minDay);
        }

        return null;
    }


    @Before
    public void setup() {
        b = new Birthday();
    }

    @Test
    public void test0() {
        String next = b.getNext("06/17", new String[]{"02/17 Wernie", "10/12 Stefan"});
        Assert.assertEquals("10/12", next);
    }

    @Test
    public void test4() {
        String next = b.getNext("01/02", new String[]{"02/17 Wernie",
                "10/12 Stefan",
                "02/17 MichaelJordan",
                "10/12 LucianoPavarotti",
                "05/18 WilhelmSteinitz"});
        Assert.assertEquals("02/17", next);
    }
}
