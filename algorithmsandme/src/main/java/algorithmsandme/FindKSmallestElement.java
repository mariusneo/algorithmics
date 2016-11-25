package algorithmsandme;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Find K smallest element from a given array
 * <p>
 * Given an unsorted array of N integers where N is so large that all of integers cannot be bought
 * into memory at once, find K smallest element from it.
 * <p>
 * http://algorithmsandme.in/2013/11/heaps-find-k-smallest-element-from-a-given-array/
 */
public class FindKSmallestElement {

    public int[] smallesElements(int[] a, int k) {
        if (k == 0) return new int[]{};
        MyHeap<Integer> heap = new MyHeap<>();
        Arrays.stream(a).forEach(heap::insert);

        int[] result = new int[a.length < k ? a.length : k];

        for (int i = 0; i < k; i++) {
            result[i] = heap.deleteMin();
        }
        return result;
    }

    @Test
    public void testAccuracy() {
        int[] a = new int[]{4, 2, 6, 7, 1, 5, 9, 8, 3};

        IntStream.range(0, a.length + 1)
                .mapToObj(k -> smallesElements(a, k))
                .map(array -> Arrays.stream(array)
                        .mapToObj(Integer::toString)
                        .collect(Collectors.joining(" ")))
                .forEach(s -> System.out.println(s));
    }
}
