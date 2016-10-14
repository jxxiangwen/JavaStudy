package cn.edu.shu.leetcode.solution1_20.solution_14;

/**
 * Write a function to find the longest common prefix string amongst an array of strings.
 */
public class Solution {
    // 1. Method 1, start from the first one, compare prefix with next string, until end;
    public String longestCommonPrefix1(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            int j = 0;
            while (j < strs[i].length() && j < prefix.length() && strs[i].charAt(j) == prefix.charAt(j)) {
                j++;
            }
            if (j == 0) {
                return "";
            }
            prefix = prefix.substring(0, j);
        }
        return prefix;
    }

    // 2. Method 2, start from the first char, compare it with all string, and then the second char
    public String longestCommonPrefix2(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        if (strs.length == 1) {
            return strs[0];
        }
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < strs[0].length(); i++) {
            for (int j = 0; j < strs.length - 1; j++) {
                if (strs[j].charAt(i) != strs[j + 1].charAt(i)) {
                    return stringBuilder.toString();
                }
            }
            stringBuilder.append(strs[0].charAt(i));
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(new Solution().longestCommonPrefix1(new String[]{"abc", "abcd", "ab"}));
        System.out.println(new Solution().longestCommonPrefix1(new String[]{}));
        System.out.println(new Solution().longestCommonPrefix1(new String[]{"abc"}));
    }
}