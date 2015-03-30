package topcoder.srm.div2.srm270;

import org.junit.Test;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.TreeSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


public class BuyingCheap {
    public int thirdBestPrice(int[] prices) {
        if (prices.length < 3)
            return -1;

        Arrays.sort(prices);
        int index = 0;
        int i = 1;
        int previous = prices[index];
        int thirdLowest = -1;
        while (i < prices.length) {
            if (prices[i] == previous)
                continue;
            index = i;
            previous = prices[index];
            if (index == 2) {
                thirdLowest = previous;
                break;
            }
        }

        return thirdLowest;
    }


    public int thirdBestPriceImproved(int[] prices) {
        if (prices.length < 3)
            return -1;


        TreeSet<Integer> minSet = new TreeSet<>();

        Arrays.stream(prices).forEach(price -> {
            minSet.add(price);
        });

        if (minSet.size() < 3)
            return -1;

        minSet.pollFirst();
        minSet.pollFirst();
        return minSet.pollFirst();
    }


    public int[] firstKBestPrices(int[] prices, int k) {
        // the validations are skipped

        // PriorityQueue can be used as a heap structure in java
        PriorityQueue<Integer> minHeap = new PriorityQueue<>((i1, i2) -> i2 - i1);

        // Build a heap set containing the first k elements in a descending order
        int index = 0;
        for (; index < prices.length; index++) {
            int price = prices[index];
            if (minHeap.contains(price))
                continue;
            minHeap.add(price);
            if (minHeap.size() == k)
                break;
        }

        int maxMinHeap = minHeap.peek();
        for (; index < prices.length; index++) {
            int price = prices[index];
            // fill into the minHeap the price only if it is smaller than the larges element from minHeap
            if (price < maxMinHeap) {
                if (minHeap.contains(price))
                    continue;
                // discard the maximum from the minHeap
                minHeap.poll();
                minHeap.add(price);
                maxMinHeap = minHeap.peek();
            }
        }

        return minHeap.stream().sorted().mapToInt(i -> i).toArray();
    }


    @Test
    public void test1() {
        BuyingCheap bc = new BuyingCheap();
        int price = bc.thirdBestPriceImproved(new int[]{10, 10, 10, 10, 20, 20, 30, 30, 40, 40});
        assertThat(30, is(equalTo(price)));
    }

    @Test
    public void test2() {
        BuyingCheap bc = new BuyingCheap();
        int[] prices = bc.firstKBestPrices(new int[]{10, 10, 10, 10, 20, 20, 30, 30, 40, 40, 23, 12, 15, 51, 66}, 3);

        assertTrue(Arrays.equals(new int[]{10, 12, 15}, prices));
    }
}