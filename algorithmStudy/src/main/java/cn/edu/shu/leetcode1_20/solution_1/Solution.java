package cn.edu.shu.leetcode1_20.solution_1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
 * You may assume that each input would have exactly one solution.
 * Example:
 * Given nums = [2, 7, 11, 15], target = 9,
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 */
public class Solution {
    public int[] twoSum1(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0;i < nums.length; i++){
            if(map.get(nums[i]) != null){
                return new int[]{map.get(nums[i]),i};
            }
            map.put(target - nums[i],i);
        }
        int[] result = {};
        return result;
    }

    public int[] twoSum2(int[] nums, int target) {
        int[] copyNums = Arrays.copyOf(nums, nums.length);
        //排序
        Arrays.sort(nums);
        int start = 0, end = nums.length - 1;
        //寻找符合的两个值
        while (start < end) {
            if (nums[start] + nums[end] == target) {
                break;
            } else if (nums[start] + nums[end] < target) {
                start++;
            } else {
                end--;
            }
        }
        int[] result = new int[2];
        //从前往后寻找start索引
        for (int i = 0; i < copyNums.length; i++) {
            if (copyNums[i] == nums[start]) {
                result[0] = i;
                break;
            }
        }
        //从后往前寻找end索引
        for (int i = copyNums.length -1; i >= 0 ; i--) {
            if (copyNums[i] == nums[end]) {
                result[1] = i;
                break;
            }
        }
        return result;
    }
}