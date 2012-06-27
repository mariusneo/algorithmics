package org.problems.project.euler.problem6;

/**
 * Problem statement:
 * The sum of the squares of the first ten natural numbers is,
 * 
 * 12 + 22 + ... + 102 = 385 The square of the sum of the first ten natural
 * numbers is,
 * 
 * (1 + 2 + ... + 10)2 = 552 = 3025 Hence the difference between the sum of the
 * squares of the first ten natural numbers and the square of the sum is 3025
 * 385 = 2640.
 * 
 * Find the difference between the sum of the squares of the first one hundred
 * natural numbers and the square of the sum.
 * 
 * Thoughts on the problem:
 * (a+b)^2 = a^2 + b^2 + 2*a*b
 * (a+b+c)^2 = a^2 + b^2 +c^2 + 2*(a*b+b*c+a*c)
 * 
 * (a+b)^2 - (a^2 + b^2) = 2*a*b
 * (a+b+c)^2 - (a^2 + b^2 + c^2) = 2*(a*b + a*c + b*c)
 * 
 * @author marius
 * 
 */
public class Problem6 {
	public static int calculateDifferenceOfTheSquaresSum(int n){
		int sum = 0;
		for (int i=1;i<=n;i++){
			for (int j=i+1;j<=n;j++){
				sum += i*j;
			}
		}

		return sum*2;
	}
	
	public static void main(String[] args){
		System.out.println(calculateDifferenceOfTheSquaresSum(100));
	}
}
