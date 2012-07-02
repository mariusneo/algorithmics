package org.problems.project.euler.problem10;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.
 * 
 * Find the sum of all the primes below two million.
 * 
 * @author marius
 * 
 */
public class Problem10 {
	
	public static void main(String[] args) {
		//int upperLimit= 10;
		int upperLimit = 2000000;
		List<Integer> primeNumbers = new ArrayList<Integer>();
		primeNumbers.add(2);
		primeNumbers.add(3);
		for (int number=4;number<=upperLimit;number++){
			boolean isPrime = true;
			double sqrtOfNumber = Math.sqrt(number);
			for (Integer factor : primeNumbers){
				if (factor > sqrtOfNumber){
					break;
				}
				
				if (number % factor == 0){
					isPrime = false;
					break;
				}
			}
			
			if (isPrime){
				primeNumbers.add(number);
			}
		}
		
		long sumOfPrimeNumbers = 0;
		for (Integer number : primeNumbers){
			sumOfPrimeNumbers += number;
		}
		System.out.println(sumOfPrimeNumbers);
	}
}
