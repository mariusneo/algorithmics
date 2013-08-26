package org.mg.problems.partition;

/**
 * You have to paint N boards of length {A0, A1, A2 … AN-1}. There are K
 * painters available and you are also given how much time a painter takes to
 * paint 1 unit of board. You have to get this job done as soon as possible
 * under the constraints that any painter will only paint continuous sections of
 * board, say board {2, 3, 4} or only board {1} or nothing but not board {2, 4,
 * 5}.
 * 
 * Example: linearPartition([9,2,6,3,8,5,8,1,7,3,4], 3) => [[9,2,6,3],[8,5,8],[1,7,3,4]]
 * 
 * 
 * @author marius
 * 
 * @see http://leetcode.com/2011/04/the-painters-partition-problem.html
 * @see http://en.wikipedia.org/wiki/Partition_problem
 * @see http://www8.cs.umu.se/kurser/TDBAfl/VT06/algorithms/BOOK/BOOK2/NODE45.HTM
 * @see http://www.crispymtn.com/stories/the-algorithm-for-a-perfectly-balanced-photo-gallery
 * 
 */
public class PainterPartition {
	public int sum(int[] a, int from, int to){
		int sum = 0;
		for (int i=from; i<=to;i++){
			sum+=a[i];
		}
		
		return sum;
	} 
	
	
	public int[][] linearPartition(int[] a, int k){
		int n = a.length;
		
		// compute prefix sums p[k] = sum(a[i], i=1 .. k))
		int[] p = new int[n];
		p[0] = a[0];
		for (int i = 1; i< n;i++){
			p[i]= p[i-1] + a[i];
		}
		
		int[][] m = new int[n][k];
		int[][] d = new int[n][k];
		
		// initialize boundary conditions
		for (int i= 0; i<n; i++){
			m[i][0]=p[i];
		}
		for (int i=0;i<k;i++){
			m[0][i] = 1;
		}
		
		for (int i=1; i< n; i++){
			for (int j=1; j<k; j++){
				int best = Integer.MAX_VALUE;
				
				for (int x=0; x<=i;x++){
					int s = Math.max(m[x][j-1], p[i]-p[x]);
					if (best > s){
						m[i][j] = s;
						d[i][j] = x;
					}
				}
			}
		}
		
		System.out.println(" -------- m ----------------");		
		for (int i=0;i<n;i++){
			for (int j=0;j<k;j++){
				System.out.print(m[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println(" -------- d ----------------");		
		for (int i=0;i<n;i++){
			for (int j=0;j<k;j++){
				System.out.print(d[i][j] + " ");
			}
			System.out.println();
		}

		
		return null;
	}
	
	public static void main(String[] args){
		PainterPartition partitioner = new PainterPartition();
		int[] a = new int[]{1,2,3,4,5,6,7,8,9};
		int k = 3;
		partitioner.linearPartition(a, k);
	}
}

