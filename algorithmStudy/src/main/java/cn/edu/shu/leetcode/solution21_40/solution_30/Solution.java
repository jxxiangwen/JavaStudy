package cn.edu.shu.leetcode.solution21_40.solution_30;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.*;

import static javax.swing.UIManager.get;

/**
 * You are given a string, s, and a list of words, words, that are all of the same length.
 * Find all starting indices of substring(s) in s
 * that is a concatenation of each word in words exactly once and without any intervening characters.
 * <p>
 * For example, given:
 * s: "barfoothefoobarman"
 * words: ["foo", "bar"]
 * <p>
 * You should return the indices: [0,9].
 * (order does not matter).
 */
public class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> list = new ArrayList<>();
        if (words.length == 0) {
            return list;
        }
        int wordLength = words[0].length();
        int arrayLength = words.length;
        StringBuilder stringBuilder = new StringBuilder(s);
        for (int i = 0; i < arrayLength; i++) {
            List<Integer> indexList = new ArrayList<>();
            int start = 0;
            while (start < s.length() - wordLength + 1) {
                int index = stringBuilder.indexOf(words[i], start);
                if (index == -1) {
                    break;
                }
                indexList.add(index);
//                start = index + wordLength;
                start++;
            }
            if (indexList.size() == 0) {
                return list;
            }
            StringBuilder sb = new StringBuilder("");
            for (int k = 0; k < arrayLength; k++) {
                sb.append('0');
            }
            sb.setCharAt(i, '1');
            for (int j = 0; j < indexList.size(); j++) {
                StringBuilder temp = new StringBuilder(sb.toString());
                if (isAllOne(helper(s, indexList.get(j) + wordLength, words, temp))) {
                    list.add(indexList.get(j));
                }
            }
        }
        if (list.size() > 0) {
            Set<Integer> set = new HashSet<>(list);
            return new ArrayList<>(set);
        }
        return list;
    }

    public StringBuilder helper(String s, int startIndex, String[] words, StringBuilder sb) {
        int wordLength = words[0].length();
        if (startIndex + wordLength > s.length()) {
            return sb;
        }
        String temp = s.substring(startIndex, startIndex + wordLength);
        StringBuilder stringBuilder = null;
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == '0') {
                if (temp.equals(words[i])) {
                    sb.setCharAt(i, '1');
                    stringBuilder = new StringBuilder(sb.toString());
                    helper(s, startIndex + wordLength, words, stringBuilder);
                }
            }
        }
        if (stringBuilder != null) {
            return stringBuilder;
        }
        return sb;
    }

    public boolean isAllOne(StringBuilder sb) {
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == '0') {
                return false;
            }
        }
        return true;
    }

    class Food {
    }

    class Fruit extends Food {
    }

    class Apple extends Fruit {
    }

    class RedApple extends Apple {
    }

    public static void main(String[] args) {
        List<? extends Fruit> flist = new ArrayList<>();
        Fruit fruit = flist.get(0);

        String s = new String("我爱中国饕餮░ ▒ ▬");
        byte[] bytes = s.getBytes();
        System.out.println(s.length());
        System.out.println(s.codePointCount(0, s.length()));
        for (int i = 0; i < s.length(); i++) {
            System.out.println(s.charAt(i));
            System.out.println(s.codePointAt(i));
        }
//        System.out.println(new Solution().findSubstring("aaaaaaaa", new String[]{"aa", "aa", "aa"}));
//        System.out.println(new Solution().findSubstring("barfoothefoobarman", new String[]{"foo", "bar"}));
//        System.out.println(new Solution().findSubstring("foodfoo", new String[]{"foo", "ood"}));
//        System.out.println(new Solution().findSubstring("barfoofoobarthefoobarman", new String[]{"foo", "bar", "the"}));
//        System.out.println(new Solution().findSubstring("wordgoodgoodgoodbestword", new String[]{"word", "good", "best", "good"}));
//        System.out.println(new Solution().findSubstring("a", new String[]{"a"}));
    }
}