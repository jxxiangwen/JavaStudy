package cn.edu.shu.leetcode1_20.solution_15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0?
 * Find all unique triplets in the array which gives the sum of zero.
 * Note: The solution set must not contain duplicate triplets.
 * For example, given array S = [-1, 0, 1, 2, -1, -4],
 * A solution set is:
 * [
 * [-1, 0, 1],
 * [-1, -1, 2]
 * ]
 */
public class Solution {
    public List<List<Integer>> threeSum1(int[] num) {
        List<List<Integer>> result = new ArrayList();

        Arrays.sort(num);
        int start, end, temp;
        for (int i = 0; i < num.length; i++) {
            if (i != 0 && num[i] == num[i - 1]) continue;     //num 1：only reserve first of all same values
            int current = num[i];
            start = i + 1;
            end = num.length - 1;

            while (start < end) {
                if (start != i + 1 && num[start] == num[start - 1]) {        //num 2：only reserve first of all same values
                    start++;
                    continue;
                }
                temp = num[start] + num[end];

                if (temp == -current) {                 //find
                    List<Integer> list = new ArrayList<>(3);
                    list.add(current);
                    list.add(num[start]);
                    list.add(num[end]);
                    result.add(list);
                    start++;
                    end--;
                } else if (temp > -current) end--;
                else start++;
            }
        }
        return result;
    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> lists = new ArrayList<>();
        if (nums.length < 3) {
            return lists;
        }
        Arrays.sort(nums);
        int start = 0;
        int end = nums.length - 1;
        int three = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            start = i;
            end = nums.length - 1;
            if (start != 0 && nums[start] == nums[start - 1]) {
                continue;
            }
            if (((nums[start] ^ nums[end]) >>> 31) < 0) {
                break;
            }
            while (start < end) {
                if (end != nums.length - 1 && nums[end] == nums[end + 1]) {
                    end--;
                    continue;
                }
                int sum = nums[start] + nums[end];
                if (sum < 0) {
                    three = end - 1;
                    while (three > start && nums[three] > 0) {
                        if (sum + nums[three] == 0) {
                            List<Integer> list = new ArrayList<>();
                            list.add(nums[start]);
                            list.add(nums[three]);
                            list.add(nums[end]);
                            lists.add(list);
                            break;
                        }
                        three--;
                    }
                } else if (nums[start] + nums[end] > 0) {
                    three = start + 1;
                    while (three < end && nums[three] < 0) {
                        if (sum + nums[three] == 0) {
                            List<Integer> list = new ArrayList<>();
                            list.add(nums[start]);
                            list.add(nums[three]);
                            list.add(nums[end]);
                            lists.add(list);
                            break;
                        }
                        three++;
                    }
                } else {
                    three = start + 1;
                    while (three < end) {
                        if (nums[three] == 0) {
                            List<Integer> list = new ArrayList<>();
                            list.add(nums[start]);
                            list.add(nums[three]);
                            list.add(nums[end]);
                            lists.add(list);
                            break;
                        }
                        three++;
                    }
                }
                end--;
            }
        }
        return lists;
    }

    public static void main(String[] args) {
        List<List<Integer>> lists = new Solution().threeSum(new int[]{-1, 0, 1, 2, -1, -4});
        System.out.println(lists);
        lists = new Solution().threeSum(new int[]{0, 0, 0, 0});
        System.out.println(lists);
    }
}