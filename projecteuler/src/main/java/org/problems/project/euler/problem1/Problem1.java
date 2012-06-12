package org.problems.project.euler.problem1;

/**
 * If we list all the natural numbers below 10 that are multiples of 3 or 5, we
 * get 3, 5, 6 and 9. The sum of these multiples is 23.
 * 
 * Find the sum of all the multiples of 3 or 5 below 1000.
 * 
 * @author marius
 * 
 */
public class Problem1 {
	
	private static int sum(int n){
		int s = n * (n+1) / 2;
		return s;
	}
	
	private static int sumWithoutMultiples(int number, int excludedNumber, int maximum){
		int maxMultiple = (maximum -1) / number;
		int sum = sum(maxMultiple);
		
		int maxExcludedMultiple = maxMultiple / excludedNumber;
		int sumExcludedNumbers = sum(maxExcludedMultiple) * excludedNumber;
		
		sum = (sum - sumExcludedNumbers) * number;
		return sum;
	}
	
	private static int sumOfMultiples(int number, int maximum){
		int maxMultiple = (maximum -1) / number;
		int sumOfMultiples = sum(maxMultiple) * number;
		
		return sumOfMultiples;
	}
	
	public static void main(String[] args) {
		final int MAX = 1000;
		int sumOfMultiplesOf3 = sumOfMultiples(3, MAX);
		int sumOfMultiplesOf5WithoutMultiplesOf3 = sumWithoutMultiples(5, 3,  MAX);
		
		int sumOfBothMultiples = sumOfMultiplesOf3 + sumOfMultiplesOf5WithoutMultiplesOf3;
		System.out.println(sumOfBothMultiples);
	}
}
