package org.mg.sort;

import org.junit.Test;

public class QuickSortTest {

	@Test
	public void demoTest(){
		int[] values = new int[]{3,7,8,5,2,1,9,5,4};
		
		QuickSort sorter = new QuickSort();

		for (int value : values){
			System.out.print(value + " ");
		}
		System.out.println();
		System.out.println("----------------------------------");
		sorter.sort(values);
		for (int value : values){
			System.out.print(value + " ");
		}
	}
}
