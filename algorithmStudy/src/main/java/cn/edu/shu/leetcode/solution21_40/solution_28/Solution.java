package cn.edu.shu.leetcode.solution21_40.solution_28;

/**
 * Implement strStr().
 * Returns the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
 */
public class Solution {
    public int strStr(String haystack, String needle) {
        if (needle.length() == 0) {
            return 0;
        }
        for (int i = 0; i < haystack.length() - needle.length() + 1; i++) {
            if (haystack.charAt(i) == needle.charAt(0)) {
                int index = i + 1;
                int start = 1;
                while (index < haystack.length() && start < needle.length()) {
                    if (haystack.charAt(index) != needle.charAt(start)) {
                        break;
                    }
                    index++;
                    start++;
                }
                if (start == needle.length()) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().strStr("asdabcasdf", "abc"));
        System.out.println(new Solution().strStr("", ""));
    }
}