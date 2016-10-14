package cn.edu.shu.leetcode.solution1_20.solution_17;

import java.util.ArrayList;
import java.util.List;

/**
 * Total Accepted: 100710
 * Total Submissions: 326083
 * Difficulty: Medium
 * Given a digit string, return all possible letter combinations that the number could represent.
 * A mapping of digit to letters (just like on the telephone buttons) is given below.
 * Input:Digit string "23"
 * Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 */
public class Solution {
    public List<String> letterCombinations(String digits) {
        List<String> list = new ArrayList<>();
        if (digits == null || digits.equals("")) {
            return list;
        }
        String[] numbersArray = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        StringBuilder builder = new StringBuilder();
        helper(list, digits, builder, numbersArray);
        return list;
    }

    public void helper(List<String> list, String digits, StringBuilder builder, String[] numbersArray) {
        if (builder.length() == digits.length()) {
            list.add(builder.toString());
            return;
        }
        int number = digits.charAt(builder.length()) - '0';
        for (int i = 0; i < numbersArray[number].length(); i++) {
            builder.append(numbersArray[number].charAt(i));
            helper(list, digits, builder, numbersArray);
            builder.deleteCharAt(builder.length()-1);
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().letterCombinations("23"));
        System.out.println(new Solution().letterCombinations(""));
    }
}