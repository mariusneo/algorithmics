package org.mg.problems;

/**
 * Given a list of N coins, their values (V1, V2, ... , VN), and the total sum
 * S. Find the minimum number of coins the sum of which is S (we can use as many
 * coins of one type as we want), or report that it's not possible to select
 * coins in such a way that they sum up to S.
 * 
 * @author mga
 * @see solution in pseudocode :
 *      http://www.topcoder.com/tc?d1=tutorials&d2=dynProg&module=Static
 */
public class Coins {

	public int getMinimumCoinsCount(int sum, int[] coinValues) {

		int[] minCoinsCount = new int[sum + 1];
		// stores the sum from which each minCoinsCount element was obtained
		// by adding the appropriate coin in order to minimize the coins count.
		// It is used track which coins were added into the sum.
		int[] minCoinsPath = new int[sum + 1];
		for (int i = 1; i <= sum; i++) {
			minCoinsCount[i] = Integer.MAX_VALUE;
		}

		for (int i = 1; i <= sum; i++) {
			for (int j = 0; j < coinValues.length; j++) {
				if (coinValues[j] <= i
						&& minCoinsCount[i - coinValues[j]] + 1 < minCoinsCount[i]) {
					minCoinsPath[i] = i - coinValues[j];
					minCoinsCount[i] = minCoinsCount[i - coinValues[j]] + 1;
				}
			}

		}

		System.out.println("Coins");
		int temp = sum;
		while (temp > 0) {
			System.out.println(temp - minCoinsPath[temp]);
			temp = minCoinsPath[temp];
		}

		System.out.println("Count");
		System.out.println(minCoinsCount[sum]);

		return minCoinsCount[sum];
	}

	public static void main(String[] args) {
		Coins c = new Coins();

		int sum1 = 13;
		int[] coinValues1 = new int[] { 1, 3, 5 };

		System.out.println(c.getMinimumCoinsCount(sum1, coinValues1));
	}

}
