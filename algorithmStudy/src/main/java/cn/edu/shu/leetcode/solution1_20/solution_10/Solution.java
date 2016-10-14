package cn.edu.shu.leetcode.solution1_20.solution_10;

/**
 * Implement regular expression matching with support for '.' and '*'.
 * '.' Matches any single character.
 * '*' Matches zero or more of the preceding element.
 * The matching should cover the entire input string (not partial).
 * The function prototype should be:
 * bool isMatch(const char *s, const char *p)
 * Some examples:
 * isMatch("aa","a") → false
 * isMatch("aa","aa") → true
 * isMatch("aaa","aa") → false
 * isMatch("aa", "a*") → true
 * isMatch("aa", ".*") → true
 * isMatch("ab", ".*") → true
 * isMatch("aab", "c*a*b") → true
 */
public class Solution {
    public boolean isMatch(String s, String p) {
        boolean flag = true;
        char point = '.';
        char star = '*';
        int sPos = 0;
        int pPos = 0;
        while (pPos < p.length() && sPos < s.length()) {
            if (p.charAt(pPos) != point && p.charAt(pPos) != star) {
                if (p.charAt(pPos) != s.charAt(sPos)) {
                    if (pPos < p.length() - 1 && p.charAt(pPos + 1) == star) {
                        pPos++;
                        pPos++;
                        continue;
                    }
                    flag = false;
                    break;
                } else {
                    pPos++;
                    sPos++;
                }
            } else {
                if (p.charAt(pPos) == point) {
                    pPos++;
                    sPos++;
                } else {
                    if (pPos == 0) {
                        flag = false;
                        break;
                    }
                    if (p.charAt(pPos - 1) == point) {
                        sPos++;
                        continue;
                    }
                    if (p.charAt(pPos - 1) == star) {
                        flag = false;
                        break;
                    }
                    if (p.charAt(pPos - 1) == s.charAt(sPos)) {
                        sPos++;
                    } else {
                        pPos++;
                    }
                }
            }
        }
        if (pPos < p.length() && p.charAt(pPos) == star) {
            int piot = p.length() - 1;
            int sPoit = s.length() -1;
            while(piot > pPos && sPoit > 0){
                if(p.charAt(piot) != s.charAt(sPoit)){
                    flag = false;
                    break;
                }else {
                    piot--;
                    sPoit--;
                }
            }
            if(piot == pPos){
                pPos = p.length();
            }
        }
        if (pPos != p.length() || sPos != s.length()) {
            flag = false;
        }
        return flag;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().isMatch("aa", "a"));
        System.out.println(new Solution().isMatch("aa", "aa"));
        System.out.println(new Solution().isMatch("aaa", "aa"));
        System.out.println(new Solution().isMatch("aa", "a*"));
        System.out.println(new Solution().isMatch("aa", ".*"));
        System.out.println(new Solution().isMatch("ab", ".*"));
        System.out.println(new Solution().isMatch("aab", "c*a*b"));
        System.out.println(new Solution().isMatch("aaa", "a*a"));
    }
}