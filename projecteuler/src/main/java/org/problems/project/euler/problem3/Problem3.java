package org.problems.project.euler.problem3;

/**
 * The prime factors of 13195 are 5, 7, 13 and 29.
 * 
 * What is the largest prime factor of the number 600851475143 ?
 * 
 * @author marius
 * 
 */
public class Problem3 {
	
	public static long calculateMaximumPrimeFactor(long number){
		long maximumPrimeFactor = 1;
		
		long sqrtNumber = (long)Math.sqrt(number);
		for (long i = 2; i< sqrtNumber;i++){
			while (number % i == 0){
				number = number/i;				
			}
			
			if (number == 1){
				maximumPrimeFactor = i;
				break;
			}
			
		}
		
		if (maximumPrimeFactor ==1){
			maximumPrimeFactor = number;
		}
		
		return maximumPrimeFactor;
	}
	
	public static void main(String[] args){
		long number= 600851475143L;
		// Ana are mere
		// some small changes
		
		// some changes on local master branch.
		
		System.out.println(calculateMaximumPrimeFactor(number));
	}
}
