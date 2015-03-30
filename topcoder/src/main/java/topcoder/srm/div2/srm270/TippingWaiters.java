package topcoder.srm.div2.srm270;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class TippingWaiters {

    public int possiblePayments(int bill, int cash) {
        int round = bill % 5 == 0 ?
                bill :
                bill + (5 - (bill % 5));

        int possible = 0;
        for (int i = round; i <= cash; i += 5) {
            int x = i - bill;
            if (x > i * 0.1) {
                break;
            }
            if (x >= (i * 0.05) && x <= (i * 0.1)) possible++;
        }

        return possible;
    }

    @Test
    public void test1() {
        TippingWaiters tw = new TippingWaiters();
        assertThat(1, is(equalTo(tw.possiblePayments(23, 100))));
    }

    @Test
    public void test3() {
        TippingWaiters tw = new TippingWaiters();
        assertThat(1, is(equalTo(tw.possiblePayments(220, 239))));
    }

    @Test
    public void test4() {
        TippingWaiters tw = new TippingWaiters();
        assertThat(14440, is(equalTo(tw.possiblePayments(1234567, 12345678))));
    }
}
