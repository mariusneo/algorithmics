package algorithmsandme;

import org.junit.Test;

/**
 * Find an element in sorted rotated array
 * A sorted rotated array is an array which was sorted in the first place,
 * but rotated that mean last element becomes first and rest all shift to right by one position.
 * <p>
 * If array is rotated K times then last K elements come in start of array in the same order
 * in which they were in original array.
 * <p>
 * For example if A = [1,2,3,4,5,6,7]; then after 3 rotations array becomes A = [5,6,7,1,2,3,4]
 */
public class FindElementInSortedRotatedArray {

    public int findElement(int[] a, int elem, int start, int end) {
        int mid = (start + end) / 2;

        if (a[mid] == elem) return mid;
        if (start >= end) return -1;

        if (a[start] <= a[mid]) {
            if (elem >= a[start] && elem <= a[mid]) {
                return findElement(a, elem, start, mid - 1);
            } else {
                return findElement(a, elem, mid + 1, end);
            }
        } else {
            if (elem > a[mid] && elem <= a[end]) {
                return findElement(a, elem, mid + 1, end);
            } else {
                return findElement(a, elem, start, mid - 1);
            }
        }
    }


    @Test
    public void testAccuracy() {
        FindElementInSortedRotatedArray f = new FindElementInSortedRotatedArray();
//        int[] a = new int[]{2,3,4,5,1};
        int[] a = new int[]{5, 6, 7, 1, 2, 3, 4};

        for (int elem : a) {
            int index = f.findElement(a, elem, 0, a.length - 1);
            System.out.println(index);
        }
    }
}
