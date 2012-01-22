package topcoder.srm.div2.srm298;

import static org.junit.Assert.*;

import org.junit.Test;

public class FibonacciPositioning {
	public double getFPosition(int n) {
		int fi = 1;
		int fiplus1 = 1;

		int i = 2;
		if (n > i) {
			while (fiplus1 < n) {
				int fnext = fi + fiplus1;
				fi = fiplus1;
				fiplus1 = fnext;
				i++;
			}
		}
		
		if (fiplus1 == 1) {
			return 2.0;
		} else if (fiplus1 == n) {
			return fiplus1 + 0.0;
		} else {
			return ((double) (i-1) + ((double)(n - fi)) / (fiplus1 - fi));
		}
	}

	@Test
	public void testSample1() {
		FibonacciPositioning fp = new FibonacciPositioning();
		assertEquals(2.0, fp.getFPosition(1), 0.0);
	}

	@Test
	public void testSample2() {
		FibonacciPositioning fp = new FibonacciPositioning();
		assertEquals(5.0, fp.getFPosition(5), 0.0);
	}

	@Test
	public void testSample3() {
		FibonacciPositioning fp = new FibonacciPositioning();
		assertEquals(4.5, fp.getFPosition(4), 0.0);
	}

	@Test
	public void testSample4() {
		FibonacciPositioning fp = new FibonacciPositioning();
		assertEquals(11.2, fp.getFPosition(100), 0.001);
	}
}
