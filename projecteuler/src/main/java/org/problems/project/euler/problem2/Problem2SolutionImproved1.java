package org.problems.project.euler.problem2;

public class Problem2SolutionImproved1 {
	public static void main(String[] args){
		final int MAXIMUM = 4000000;
		int x =1, y=1;
		int sum = 0;
		do{
			if (x%2 == 0){
				sum +=x;
			}
			int z= x;
			x +=y;
			y=z;
		}while (x < MAXIMUM);
		
		System.out.println(sum);
	}
}
