package org.problems.project.euler.problem7;

/**
 * By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see
 * that the 6th prime is 13.
 * 
 * What is the 10 001st prime number?
 * 
 * @author marius
 * 
 */
public class Problem7 {
	public static void main(String[] args) {
		int numbers[] = new int[1000000];

		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = i;
		}

		int k= 0;
		for (int i = 2; i < numbers.length; i++) {
			if (numbers[i] > 0) {
				k++;
				if (k == 10001) {
					System.out.println(i);
					break;
				} else {
					for (int j = i + i; j < numbers.length; j += i) {
						numbers[j] = 0;
					}
				}
			}
		}

	}
}
