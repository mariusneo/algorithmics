package algorithmsandme;

import org.junit.Test;

import static java.lang.Math.min;

/**
 * Find Kth smallest element in two sorted arrays
 * <p>
 * For example if
 * <p>
 * A = [10, 20, 40, 60]
 * and
 * B =[15, 35, 50, 70, 100]
 * and
 * K = 4
 * then solution should be 35 because union of above arrays will be
 * <p>
 * C= [10,15,20,35,40,50,60,70,100] and fourth smallest element is 35.
 */
public class FindKthElementInTwoSortedArrays {

    /**
     * This algorithm goes  iteratively through the entries of the array.
     * Complexity : O(k)
     *
     * @param a
     * @param b
     * @param k
     * @return
     */
    public int find(int[] a, int[] b, int k) {
        if (k > a.length + b.length) return -1;

        int i = 0;
        int j = 0;

        int index = 0;
        while (i < a.length && j < b.length) {
            index++;
            if (a[i] < b[j]) {
                if (index == k) return a[i];
                i++;
            } else {
                if (index == k) return b[j];
                j++;
            }
        }

        if (i == a.length) {
            return b[(k - a.length) - 1];
        } else {
            return a[(k - b.length) - 1];
        }
    }


    /**
     * This algorithm uses divide & conquer method.
     * Complexity: O(log(k))
     *
     * @param a
     * @param b
     * @param starta
     * @param startb
     * @param k
     * @return
     */
    public int find_divide_and_conquer(int[] a, int[] b, int starta, int startb, int k) {
        if (k > a.length + b.length) return -1;

        if (starta == a.length) {
            if (b.length - startb > 0) {
                return b[startb + k - 1];
            }
            return -1;
        }
        if (startb == b.length) {
            if (a.length - starta > 0) {
                return a[startb + k - 1];
            }
            return -1;
        }

        if (k == 1) {
            return min(a[starta], b[startb]);
        }

        int i = min(a.length - starta, k / 2);
        int j = min(b.length - startb, k / 2);

        if (a[starta + i - 1] > b[startb + j - 1]) {
            return find_divide_and_conquer(a, b, starta, startb + j, k - j);
        } else {
            return find_divide_and_conquer(a, b, starta + i, startb, k - i);
        }
    }


    @Test
    public void testAccuracy() {

        int[] a = new int[]{10, 20, 40, 60};
        int[] b = new int[]{15, 35, 50, 70, 100};
        for (int k = 1; k <= a.length + b.length + 1; k++) {
            System.out.println(find(a, b, k));
            System.out.println(find_divide_and_conquer(a, b, 0, 0, k));
            System.out.println();
        }
    }
}
