package cn.edu.shu.leetcode.solution1_20.solution_5;

/**
 * Given a string S, find the longest palindromic substring in S.
 * You may assume that the maximum length of S is 1000, and there exists one unique longest palindromic substring.
 */
public class Solution {
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        int length = s.length();
        int max = 0;
        String result = "";
        for (int i = 1; i <= 2 * length - 1; i++) {
            int count = 1;
            while (i - count >= 0 && i + count <= 2 * length && get(s, i - count) == get(s, i + count)) {
                count++;
            }
            count--; // there will be one extra count for the outbound #
            if (count > max) {
                result = s.substring((i - count) / 2, (i + count) / 2);
                max = count;
            }
        }

        return result;
    }

    private char get(String s, int i) {
        if (i % 2 == 0)
            return '#';
        else
            return s.charAt(i / 2);
    }

    private String expandCenter(String s, int start, int end) {
        int n = s.length();
        while (start >= 0 && end <= n - 1 && s.charAt(start) == s.charAt(end)) {
            start--;
            end++;
        }
        //此时start已经不是指向回文开始了，指向了回文开始的左边一个字符
        return s.substring(start + 1, end);
    }


    public String longestPalindrome1(String s) {
        int n = s.length();
        if (0 == n) return "";
        String result = s.substring(0, 1);
        for (int i = 0; i < n; i++) {
            String s1 = expandCenter(s, i, i);
            if (s1.length() > result.length()) {
                result = s1;
            }
            s1 = expandCenter(s, i, i + 1);
            if (s1.length() > result.length()) {
                result = s1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().longestPalindrome1("asabccbaasf"));
        System.out.println(new Solution().longestPalindrome1("bb"));
        System.out.println(new Solution().longestPalindrome1("asabebaasdf"));
    }
}