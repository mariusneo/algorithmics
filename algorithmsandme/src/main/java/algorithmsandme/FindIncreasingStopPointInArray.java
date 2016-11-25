package algorithmsandme;

import org.junit.Test;

/**
 * A array is increasing and then decreasing find the point where it stops increasing
 */
public class FindIncreasingStopPointInArray {

    public int findIncreasingStopIndex(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[i - 1]) return i - 1;
        }

        return -1;
    }


    public int findIncreasingStopIndexDivideAndConquer(int[] a, int start, int end) {
        if (start == end) return -1;
        if (start + 1 == end) {
            if (a[start] > a[end]) return start;
            else return -1;
        }
        int mid = (start + end) / 2;

        if (a[mid - 1] < a[mid]) {
            return findIncreasingStopIndexDivideAndConquer(a, mid, end);
        } else {
            return findIncreasingStopIndexDivideAndConquer(a, start, mid);
        }

    }

    @Test
    public void testAccuracy() {
        int[] a = new int[]{1, 2, 3, 4, 5, 4, 3, 2, 1};

        int index = findIncreasingStopIndex(a);
        System.out.println(index + " - " + a[index]);

        index = findIncreasingStopIndexDivideAndConquer(a, 0, a.length - 1);
        System.out.println(index + " - " + a[index]);
    }
}
