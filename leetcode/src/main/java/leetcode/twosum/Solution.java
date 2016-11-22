package leetcode.twosum;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Solution {
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }


    public int[] twoSum2(int[] nums, int target) {
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > target) continue;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }


    @Test
    public void testTwoSum() {
        Solution s = new Solution();
        int[] solution = s.twoSum(new int[]{2, 7, 11, 15}, 9);
        assertThat(solution, is(new int[]{0, 1}));
    }
}