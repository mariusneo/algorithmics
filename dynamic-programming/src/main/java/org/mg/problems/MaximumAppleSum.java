package org.mg.problems;

/**
 * A table composed of N x M cells, each having a certain quantity of apples, is
 * given. You start from the upper-left corner. At each step you can go down or
 * right one cell. Find the maximum number of apples you can collect.
 * 
 * @author mga
 * @see solution described at
 *      http://www.topcoder.com/tc?d1=tutorials&d2=dynProg&module=Static
 */
public class MaximumAppleSum {

	public int getMaxSum(int[][] apples) {
		// initialize the array containing the maximum sum of apples
		int[][] s = new int[apples.length][];
		// holder for data used to reconstruct the path of the maximum apple sum
		// Possible values : 
		// 0 - means go left
		// 1 - means go up
		int[][] path =new int[apples.length][];
		for (int i = 0; i < apples.length; i++) {
			s[i] = new int[apples[i].length];
			path[i] = new int[apples[i].length];
		}

		// the first row will contain the sum of the elements 
		// s[0][j] =  sum(a[0][i], where i = 0:j 
		// (there can be only towards right summed up)
		for (int j = 0; j < s[0].length; j++) {
			s[0][j] += apples[0][j];
			if (j > 0) {
				s[0][j] += s[0][j - 1];
				path[0][j] = 0;
			}
			
		}

		// the first column will contain the sum of the elements 
		// s[i][0] =  sum(a[j][0], where  j= 0:i 
		// (there can be only towards down summed up)
		for (int i = 1; i < s.length; i++) {
			s[i][0] += apples[i][0];
			if (i > 0) {
				s[i][0] += s[i - 1][0];
				path[i][0] = 1;
			}
		}
		
		// the maximum sum will be found either on the last row or on the last column 
		int maxsum = 0;
		int maxi = 0;
		int maxj = 0;
		
		for (int i=1;i<s.length;i++){
			for (int j=1;j<s[0].length;j++){
				s[i][j] = Math.max(s[i-1][j] + apples[i][j], s[i][j-1] + apples[i][j]);
				if (s[i][j] - apples[i][j]== s[i-1][j] ){
					path[i][j]=1;
				}else{
					path[i][j]=0;
				}
				if (i==s.length-1 || j == s[0].length - 1){
					if (s[i][j]> maxsum){
						maxsum = s[i][j];
						maxi = i;
						maxj = j;
					}
				}
			}
		}
		
		
		System.out.println("SUMS");
		for (int i=0;i<s.length;i++){
			for (int j=0;j<s[0].length;j++){
				System.out.print(s[i][j] + " ");
			}
			System.out.println();
		}
		
		
		System.out.println();
		System.out.println("PATHS");
		for (int i=0;i<path.length;i++){
			for (int j=0;j<path[0].length;j++){
				System.out.print(path[i][j] + " ");
			}
			System.out.println();
		}
		
		
		return maxsum;
	}

	public static void main(String[] args) {
		int[][] apples = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };

		MaximumAppleSum m = new MaximumAppleSum();
		int sum = m.getMaxSum(apples);
		System.out.println("Sum is : " + sum);
	}
}
