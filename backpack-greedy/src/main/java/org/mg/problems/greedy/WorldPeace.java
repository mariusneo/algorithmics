package org.mg.problems.greedy;

import java.util.Arrays;
import java.util.Collections;

/**
 * You have decided to organize a grassroots campaign for world peace. Your plan
 * is to assign ordinary citizens into groups of k penpals such that each group
 * contains citizens from k different countries. People in each group will
 * exchange letters in an effort to increase their understanding of each other's
 * cultures. Given k and the populations of the participating countries as a
 * int[] countries, you must determine the maximum number of groups that can be
 * formed.
 * 
 * Note that no individual may be assigned to more than one group, and that some
 * individuals may be left without a group.
 * 
 * <pre>
 * Definition
 * 
 * Class: WorldPeace Method: numGroups 
 * Parameters: int, int[] Returns: long
 * Method signature: long numGroups(int k, int[] countries) 
 * (be sure your method is public)
 * </pre>
 * 
 * <pre>
 * 0)	
 *     	
 * 4
 * { 4,4,4,4,4 }
 * Returns: 5
 * Suppose the countries are Canada, China, Poland, Sweden, and the USA. Then you can make 5 groups as follows:
 *      Canada, China,  Poland, Sweden
 *      Canada, China,  Poland, USA
 *      Canada, China,  Sweden, USA
 *      Canada, Poland, Sweden, USA
 *      China,  Poland, Sweden, USA
 * 1)	
 *     	
 * 5
 * { 1,2,3,4,5,6 }
 * Returns: 3
 * Suppose the countries are designated 1 through 6, according to population. Then three groups are possible:
 *    2,3,4,5,6
 *    2,3,4,5,6
 *    1,3,4,5,6
 * There are six people left unassigned, but they come from only three different countries, so they cannot be made into another group.
 * 2)	
 *     	
 * 2
 * { 1000000000, 1000000000, 1000000000, 1000000000, 1000000000, 1000000000 }
 * Returns: 3000000000
 * </pre>
 * 
 * 
 * @author mga
 * @see solution explained here :
 *      http://community.topcoder.com/tc?module=Static&d1=tutorials&d2=greedyAlg
 */
public class WorldPeace {
	public long numGroups(int k, int[] countries) {
		int groups = 0;
		int min = 0;

		do {
			// order descending the array
			Arrays.sort(countries);
			reverse(countries);

			min = countries[k - 1];

			if (min > 0) {
				groups += 1;

				for (int i = 0; i < k; i++) {
					countries[i] = countries[i] - 1;
				}
			}

		} while (min > 0);
		return groups;
	}

	public static void reverse(int[] b) {
		int left = 0; // index of leftmost element
		int right = b.length - 1; // index of rightmost element

		while (left < right) {
			// exchange the left and right elements
			int temp = b[left];
			b[left] = b[right];
			b[right] = temp;

			// move the bounds toward the center
			left++;
			right--;
		}
	}// endmethod reverse

	public static void main(String[] args) {
		WorldPeace wp = new WorldPeace();
		int k1 = 4;
		int[] countries1 = { 4, 4, 4, 4, 4 };
		System.out.println(wp.numGroups(k1, countries1));

		int k2 = 5;
		int[] countries2 = { 1, 2, 3, 4, 5, 6 };
		System.out.println(wp.numGroups(k2, countries2));

	}
}
