package cn.edu.shu.leetcode.solution1_20.solution_16;

import java.util.Arrays;

/**
 * Given an array S of n integers, find three integers in S such that the sum is closest to a given number, target.
 * Return the sum of the three integers. You may assume that each input would have exactly one solution.
 * For example, given array S = {-1 2 1 -4}, and target = 1.
 * The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
 */
public class Solution {
    public int threeSumClosest(int[] nums, int target) {
        if (nums.length < 3) {
            return 0;
        }
        int closeNumber = nums[0] + nums[1] + nums[2];
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            int current = nums[i];
            int start = i + 1;
            int end = nums.length - 1;
            while (start < end) {
                int sum = current + nums[start] + nums[end];
                if (Math.abs(current + nums[start] + nums[end] - target) < Math.abs(closeNumber - target)) {
                    closeNumber = sum;
                }
                if (nums[start] + nums[end] + current - target < 0) {
                    start++;
                } else {
                    end--;
                }
            }
        }
        return closeNumber;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().threeSumClosest(new int[]{-1, 2, 1, -4}, 1));
        System.out.println(new Solution().threeSumClosest(new int[]{1,1,1,0}, -100));
    }
}