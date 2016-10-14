package cn.edu.shu.leetcode.solution21_40.solution_27;

/**
 * Given an array and a value, remove all instances of that value in place and return the new length.
 * <p>
 * Do not allocate extra space for another array, you must do this in place with constant memory.
 * <p>
 * The order of elements can be changed. It doesn't matter what you leave beyond the new length.
 * <p>
 * Example:
 * Given input array nums = [3,2,2,3], val = 3
 * <p>
 * Your function should return length = 2, with the first two elements of nums being 2.
 */
public class Solution {
    public int removeElement(int[] nums, int val) {
        int length = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] != val){
                nums[length++] = nums[i];
            }
        }
        return length;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().removeElement(new int[]{3,2,2,3},3));
    }
}