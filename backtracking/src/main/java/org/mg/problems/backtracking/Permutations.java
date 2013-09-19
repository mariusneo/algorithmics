package org.mg.problems.backtracking;

/**
 * Display all permutations of the elements of a given array.
 * 
 * e.g. [1,2,3] -> [ [1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], [3,2,1]]
 * 
 * 
 * @author Admin
 * 
 */
public class Permutations {

	private static boolean valid(int[] st, int k) {
		for (int i = 0; i <= k - 1; i++) {
			if (st[i] == st[k]) {
				return false;
			}
		}

		return true;
	}

	private static void permutationsIterative(int[] a) {
		System.out.println("INPUT");
		for (int element : a) {
			System.out.print(element + " ");
		}
		System.out.println();

		System.out.println("PERMUTATIONS");
		int n = a.length;
		int k = 0;
		int[] st = new int[n];
		st[k] = -1;
		while (k >= 0) {
			do {
				st[k]++;
			} while ((st[k] < n) && !valid(st, k));

			if (st[k] < n) {
				if (k == n - 1) {
					for (int i : st) {
						System.out.print(a[i] + " ");
					}
					System.out.println();
				} else {
					k++;
					st[k] = -1;
				}
			} else {
				k--;
			}
		}
	}

	private static void permutationsRecursive(int[] a, int[] st, int k) {

		if (k == a.length) {
			for (int i : st) {
				System.out.print(a[i] + " ");
			}
			System.out.println();
		} else {
			for (int i = 0; i < a.length; i++) {
				st[k] = i;
				if (valid(st, k)) {
					permutationsRecursive(a, st, k + 1);
				}
			}
		}

	}

	public static void main(String[] args) {
		int[] a = new int[] { 1, 2, 3 };

		permutationsIterative(a);
		System.out.println();
		int[] st = new int[a.length];
		st[0] = -1;
		permutationsRecursive(a, st, 0);
	}

}
