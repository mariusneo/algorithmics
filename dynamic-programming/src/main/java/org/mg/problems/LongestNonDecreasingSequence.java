package org.mg.problems;

/**
 * Given a sequence of N numbers - A[1] , A[2] , ..., A[N] . Find the length of
 * the longest non-decreasing sequence.
 * 
 * e.g. : getLongestNonDecreasingSequence{ 5, 3, 4, 8, 6, 7} = {3,4,6,7}
 * 
 * @author mga
 * @see problem solution explained
 *      http://www.topcoder.com/tc?d1=tutorials&d2=dynProg&module=Static
 */
public class LongestNonDecreasingSequence {

	public int[] getLongestNonDecreasingSequence(int[] a) {
		int n = a.length;
		int[] s = new int[n];
		int[] prev = new int[n];
		for (int i = 0; i < n; i++) {
			s[i] = 1;
			prev[i] = i;
		}

		for (int i = 1; i < a.length; i++) {
			for (int j = 0; j < i; j++) {
				if (a[j] <= a[i] && s[j] + 1 > s[i]) {
					s[i] = s[j] + 1;
					prev[i] = j;

				}
			}
		}

		// get the last index of the sequence with the maximum length
		int maxindex = 0;
		int maxlength = 1;
		for (int i = 0; i < n; i++) {
			if (s[i] > maxlength) {
				maxlength = s[i];
				maxindex = i;
			}
		}

		int count = s[maxindex];
		int[] maxs = new int[count];
		int index = maxindex;
		do {
			count--;
			maxs[count] = a[index];
			index = prev[index];
		} while (count > 0);

		return maxs;
	}

	public static void main(String[] args) {
		LongestNonDecreasingSequence l = new LongestNonDecreasingSequence();

		int[] a = { 5, 3, 4, 8, 6, 7, 2, 8 };
		int[] maxs = l.getLongestNonDecreasingSequence(a);
		for (int element : maxs) {
			System.out.print(element + " ");
		}
	}
}
