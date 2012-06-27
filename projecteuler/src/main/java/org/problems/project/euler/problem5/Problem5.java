package org.problems.project.euler.problem5;

/**
 * 2520 is the smallest number that can be divided by each of the numbers from 1
 * to 10 without any remainder.
 * 
 * What is the smallest positive number that is evenly divisible by all of the
 * numbers from 1 to 20?
 * 
 * @author marius
 * 
 */
public class Problem5 {

	private static int calculateSmallestProduct(int number) {
		int[] factors = new int[number + 1];
		for (int i = 2; i <= number; i++) {
			factors[i] = i;
		}

		for (int i = 2; i <= number; i++) {

			if (factors[i] != 1) {
				for (int j = 2; i * j <= number; j++) {
					factors[i * j] /= factors[i];
					
				}
			}
		}
		int product = 1;

		for (int i = 2; i <= number; i++) {
			if (factors[i] != 1) {
				System.out.println(factors[i]);
				product *= factors[i];
			}
		}

		return product;
	}

	public static void main(String[] args) {
		System.out.println(calculateSmallestProduct(10));
		System.out.println(calculateSmallestProduct(20));
	}
}
