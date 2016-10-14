package cn.edu.shu.common;

import java.util.Arrays;

/**
 * Created by jxxiangwen on 16-10-14.
 * 实现KMP算法
 */
public class KMP {
    /**
     * 得到next数组
     *
     * @param pattern 模式串
     * @return
     */
    public static int[] getNextArray(String pattern) {
        if (pattern.length() == 0) {
            return new int[0];
        }
        int[] next = new int[pattern.length()];
        next[0] = -1;
        for (int i = 1; i < pattern.length(); i++) {
            int k = next[i - 1];
            //递归查找最长前缀后缀
            while (pattern.charAt(i) != pattern.charAt(k + 1) && k > 0) {
                k = next[k];
            }
            //存在最长前缀后缀
            if (pattern.charAt(i) == pattern.charAt(k + 1)) {
                next[i] = k + 1;
            } else {
                //不存在
                next[i] = -1;
            }
        }
        return next;
    }

    /**
     * 得到匹配索引
     *
     * @param match   匹配字符串
     * @param pattern 模式串
     * @return
     */
    public static int getIndex(String match, String pattern) {
        int[] next = getNextArray(pattern);
        return getIndex(match, pattern, next);
    }

    /**
     * 得到匹配索引
     *
     * @param match   匹配字符串
     * @param pattern 模式串
     * @param next    next数组
     * @return
     */
    public static int getIndex(String match, String pattern, int[] next) {
        int match_i = 0;
        int pattern_i = 0;
        while (match_i < match.length() && pattern_i < pattern.length()) {
            //匹配
            if (match.charAt(match_i) == pattern.charAt(pattern_i)) {
                match_i++;
                pattern_i++;
            } else {
                //不匹配时候模式串一个也没匹配
                if (0 == pattern_i) {
                    match_i++;
                } else {
                    //假设模式串为ABCDABD，不匹配发生在D位置，可以从C位置往后开始匹配
                    pattern_i = next[pattern_i - 1] + 1;
                }
            }
        }
        return pattern_i == pattern.length() ? match_i - pattern_i : -1;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(KMP.getNextArray("ABCDABD")));
        System.out.println(KMP.getIndex("ABCDABCDABCDABD","ABCDABD"));
        System.out.println(KMP.getIndex("ABCDABCDABCDABD",""));
        System.out.println(KMP.getIndex("ABCDEABCDFABCDABD","FAB"));
    }
}
