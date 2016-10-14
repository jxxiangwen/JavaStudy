package cn.edu.shu.leetcode.solution1_20.solution_3;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a string, find the length of the longest substring without repeating characters.
 * Examples:
 * Given "abcabcbb", the answer is "abc", which the length is 3.
 * Given "bbbbb", the answer is "b", with the length of 1.
 * Given "pwwkew", the answer is "wke", with the length of 3.
 * Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
 */
public class Solution {
    public int lengthOfLongestSubstring(String s) {
        List<Character> list = new ArrayList<>();
        int size = s.length();
        int start = 0;
        int max = 0;
        for (int i = 0; i < size; i++) {
            if (list.contains(s.charAt(i))) {
                if (i - start > max) {
                    max = i - start;
                }
                int indexOf = list.indexOf(s.charAt(i)) + 1;
                for(int j = 0;j < indexOf; j++){
                    list.remove(0);
                }
                start += indexOf;
            }
            list.add(s.charAt(i));
        }
        if(size - start > max){
            return size - start;
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().lengthOfLongestSubstring("bpfbhmipx"));
        System.out.println(new Solution().lengthOfLongestSubstring("abcabcbb"));
        System.out.println(new Solution().lengthOfLongestSubstring("bbbbb"));
        System.out.println(new Solution().lengthOfLongestSubstring("pwwkew"));
    }
}