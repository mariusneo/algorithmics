package topcoder.srm.div2.srm530;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class GogoXBallsAndBinsEasy {
	public static int solve(int[] T){
		int movesSum = 0;
		for (int i = 0;i < T.length/2; i++){
			movesSum += T[T.length - i - 1] - T[i];
		}
		return movesSum;
	}
	
	@Test
	public void test1(){
		int[] T = new int[]{0,2,5};
		int movesSum = solve(T);
		assertThat(movesSum, is(5));
	}
	
	@Test
	public void test2(){
		int[] T = new int[]{5, 6, 7, 8};
		int movesSum = solve(T);
		assertThat(movesSum, is(4));
	}
	
	@Test
	public void test3(){
		int[] T = new int[]{0, 1, 2, 10};
		int movesSum = solve(T);
		assertThat(movesSum, is(11));
	}
	
	@Test
	public void test4(){
		int[] T = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
		int movesSum = solve(T);
		assertThat(movesSum, is(16));
	}
}
