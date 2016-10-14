package cn.edu.shu.leetcode.solution21_40.solution_26;

/**
 * Given a sorted array, remove the duplicates in place such that each element appear only once and return the new length.
 * <p>
 * Do not allocate extra space for another array, you must do this in place with constant memory.
 * <p>
 * For example,
 * Given input array nums = [1,1,2],
 * <p>
 * Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively.
 * It doesn't matter what you leave beyond the new length.
 */
public class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length < 2) {
            return nums.length;
        }
        int length = 1;
        int last = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != last) {
                length++;
                last = nums[i];
                nums[length - 1] = last;
            }
        }
        return length;
    }

    public int removeDuplicates1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int size = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != nums[size]) {
                nums[++size] = nums[i];
            }
        }
        return size + 1;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().removeDuplicates(new int[]{1, 1, 2}));
    }
}