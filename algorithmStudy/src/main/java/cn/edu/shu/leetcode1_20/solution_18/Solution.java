package cn.edu.shu.leetcode1_20.solution_18;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an array S of n integers, are there elements a, b, c, and d in S such that a + b + c + d = target?
 * Find all unique quadruplets in the array which gives the sum of target.
 * Note: The solution set must not contain duplicate quadruplets.
 * For example, given array S = [1, 0, -1, 0, -2, 2], and target = 0.
 * A solution set is:
 * [
 * [-1,  0, 0, 1],
 * [-2, -1, 1, 2],
 * [-2,  0, 0, 2]
 * ]
 */
public class Solution {
    public ArrayList<ArrayList<Integer>> fourSum1(int[] num, int target) {
        ArrayList<ArrayList<Integer>> rst = new ArrayList<ArrayList<Integer>>();
        Arrays.sort(num);

        for (int i = 0; i < num.length - 3; i++) {
            if (i != 0 && num[i] == num[i - 1]) {
                continue;
            }

            for (int j = i + 1; j < num.length - 2; j++) {
                if (j != i + 1 && num[j] == num[j - 1])
                    continue;

                int left = j + 1;
                int right = num.length - 1;
                while (left < right) {
                    int sum = num[i] + num[j] + num[left] + num[right];
                    if (sum < target) {
                        left++;
                    } else if (sum > target) {
                        right--;
                    } else {
                        ArrayList<Integer> tmp = new ArrayList<Integer>();
                        tmp.add(num[i]);
                        tmp.add(num[j]);
                        tmp.add(num[left]);
                        tmp.add(num[right]);
                        rst.add(tmp);
                        left++;
                        right--;
                        while (left < right && num[left] == num[left - 1]) {
                            left++;
                        }
                        while (left < right && num[right] == num[right + 1]) {
                            right--;
                        }
                    }
                }
            }
        }

        return rst;
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> lists = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            int start = i;
            int end = nums.length - 1;
            if (start != 0 && nums[start] == nums[start - 1]) {
                continue;
            }
            while (start < end) {
                if (end != nums.length - 1 && nums[end] == nums[end + 1]) {
                    end--;
                    continue;
                }
                int left = start + 1;
                int right = end - 1;
                int last = target - nums[start] - nums[end];
                int status = 0;
                while (left < right) {
                    if (status == 1) {
                        if (nums[left] == nums[left - 1]) {
                            left++;
                            continue;
                        }
                    }
                    if (status == 2) {
                        if (nums[right] == nums[right + 1]) {
                            right--;
                            continue;
                        }
                    }
                    if(status == 3){
                        if (nums[left] == nums[left - 1] && nums[right] == nums[right + 1]) {
                            left++;
                            right--;
                            continue;
                        }
                    }
                    if (nums[left] + nums[right] < last) {
                        status = 1;
                        left++;
                    } else if (nums[left] + nums[right] > last) {
                        right--;
                        status = 2;
                    } else {
                        List<Integer> list = new ArrayList<>();
                        list.add(nums[start]);
                        list.add(nums[left]);
                        list.add(nums[right]);
                        list.add(nums[end]);
                        lists.add(list);
                        left++;
                        right--;
                        status = 3;
                    }
                }
                end--;
            }
        }
        return lists;
    }

    public static void main(String[] args) {
//        System.out.println(new Solution().fourSum(new int[]{1, 0, -1, 0, -2, 2},0));
//        System.out.println(new Solution().fourSum(new int[]{-1, -5, -5, -3, 2, 5, 0, 4}, -7));
//        System.out.println(new Solution().fourSum(new int[]{-5, 5, 4, -3, 0, 0, 4, -2}, 4));
//        System.out.println(new Solution().fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0));
//        System.out.println(new Solution().fourSum(new int[]{0, 0, 0, 0}, 0));
//        System.out.println(new Solution().fourSum(new int[]{5, 5, 3, 5, 1, -5, 1, -2}, 4));
//        System.out.println(new Solution().fourSum(new int[]{-1,2,2,-5,0,-1,4}, 3));
//        System.out.println(new Solution().fourSum(new int[]{0, -5, 5, 1, 1, 2, -5, 5, -3}, -11));
        System.out.println(new Solution().fourSum(new int[]{-3, -4, -5, 0, -5, -2, 5, 2, -3}, 3));
    }
}