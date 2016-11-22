package leetcode.twosum;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SumBacktracking {

    public boolean isValid(int[] st, int k, int[] nums, int target) {
        int sum = 0;
        for (int i = 0; i <= k; i++) {
            sum += nums[st[i]];
        }

        return sum <= target;
    }

    public void sumIterative(int[] nums, int target, int n) {
        int[] st = new int[n];
        int k = 0;
        st[k] = -1;
        while (k >= 0) {
            do {
                st[k]++;
            } while (st[k] < n && !isValid(st, k, nums, target));

            if (st[k] < nums.length) {
                if (k == n - 1) {
                    int sum = Arrays.stream(st).map(s -> nums[s]).sum();
                    if (sum == target) {
                        System.out.println(Arrays
                                .stream(st)
                                .mapToObj(i -> Integer.toString(nums[i]))
                                .collect(Collectors.joining(" ")));
                    }
                } else {
                    k++;
                    st[k] = st[k - 1];
                }
            } else {
                k--;
            }
        }
    }


    public void sumRecursive(int[] nums, int target, int n, int[] st, int k) {
        if (k == n) {
            int sum = Arrays.stream(st).map(s -> nums[s]).sum();
            if (sum == target) {
                System.out.println(Arrays
                        .stream(st)
                        .mapToObj(i -> Integer.toString(nums[i]))
                        .collect(Collectors.joining(" ")));
            }
        } else {
            int start = k == 0 ? 0 : st[k - 1] + 1;
            for (int i = start; i < nums.length; i++) {
                st[k] = i;
                if (isValid(st, k, nums, target)) {
                    sumRecursive(nums, target, n, st, k + 1);
                }
            }
        }
    }

    @Test
    public void testSumIterative() {
        int[] nums = new int[]{2, 5, 7, 11, 4, 3, 8, 1};
        SumBacktracking sb = new SumBacktracking();
        sb.sumIterative(nums, 9, 2);
    }

    @Test
    public void testSumRecursive() {
        int[] nums = new int[]{2, 5, 7, 11, 4, 3, 8, 1};
        int n = 2;
        int[] st = new int[n];
        SumBacktracking sb = new SumBacktracking();
        sb.sumRecursive(nums, 9, n, st, 0);
    }
}
