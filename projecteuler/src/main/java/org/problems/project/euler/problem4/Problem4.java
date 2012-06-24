package org.problems.project.euler.problem4;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Stack;

/**
 * A palindromic number reads the same both ways. The largest palindrome made
 * from the product of two 2-digit numbers is 9009 = 91 99.
 * 
 * Find the largest palindrome made from the product of two 3-digit numbers.
 * 
 * @author marius
 * 
 * 
 * Open issues on the present algorithm:
 * - when dealing 
 */
public class Problem4 {
	public static void main(String[] args) {
		for (int i = 999; i > 99; i--) {
			// not such an elegant manner to obtain the palindrome.
			int number = Integer.parseInt(i + new StringBuffer().append(i).reverse().toString());
			List<Integer> factors = getPrimeFactors(number);

			boolean[] usedFactors = new boolean[factors.size()];

			int value1 = 1;
			int value2 = 1;
			
			ListIterator<Integer> reversedIterator = factors
					.listIterator(factors.size());
			while (reversedIterator.hasPrevious()) {
				int currentIndex = reversedIterator.previousIndex();
				int currentValue = reversedIterator.previous();
				if (value2 * currentValue < 1000) {
					value2 *= currentValue;
					usedFactors[currentIndex] = true;
				}
			}
						
			ListIterator<Integer> iterator = factors.listIterator();			
			while (iterator.hasNext()){
				int currentIndex = iterator.nextIndex();
				int currentValue = iterator.next();
				if (!usedFactors[currentIndex]) {
					value1 *= currentValue;
					usedFactors[currentIndex] = true;
				}				
			}
			
			StringBuilder sb  = new StringBuilder();
			for (int factor : factors){
				sb.append(factor).append(" ");
			}
			
			if ((value1 > 100 && value1 < 1000)
					&& (value2 > 100 && value2< 1000)){
				System.out.println("---------------------------------------------------");
				System.out.println("SOLUTION: " + number + " = " + value1 + " x " + value2 + " FACTORS " + sb.toString()) ;
				break;
			}else{
				System.out.println("ATTEMPT: " + number + " = " + value1 + " x " + value2 + " FACTORS " + sb.toString());
			}
		}
	}

	public static Stack reverseSet(Set<Integer> set) {
		Stack<Integer> stack = new Stack<Integer>();
		for (Integer value : set) {
			stack.add(value);
		}

		return stack;

	}

	public static List<Integer> getPrimeFactors(int number) {
		List<Integer> factors = new ArrayList<Integer>();
		for (int i = 2; i < Math.sqrt(number); i++) {
			while (number % i == 0) {
				number = number / i;
				factors.add(i);
			}

			if (number == 1) {
				break;
			}

		}

		if (number > 1) {
			// prime number
			factors.add(number);
		}

		return factors;
	}
}
