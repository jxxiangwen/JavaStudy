package cn.edu.shu.leetcode21_40.solution_22;

import java.util.ArrayList;
import java.util.List;

/**
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
 * <p>
 * For example, given n = 3, a solution set is:
 * <p>
 * [
 * "((()))",
 * "(()())",
 * "(())()",
 * "()(())",
 * "()()()"
 * ]
 */
public class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> list = new ArrayList<>();
        generate(list, 0, 0, "", n);
        return list;
    }

    public void generate(List<String> list, int left, int right, String value, int n) {
        if (right == n) {
            list.add(value);
            return;
        }
        if (left < n) {
            generate(list, left + 1, right, value + "(", n);
            if (left > right) {
                generate(list, left, right + 1, value + ")", n);
            }
        } else {
            generate(list, left, right + 1, value + ")", n);
        }
    }

    public ArrayList<String> generateParenthesis1(int n) {
        ArrayList<String> result = new ArrayList<String>();
        if (n <= 0) {
            return result;
        }
        helper(result, "", n, n);
        return result;
    }

    public void helper(ArrayList<String> result,
                       String paren, // current paren
                       int left,     // how many left paren we need to add
                       int right) {  // how many right paren we need to add
        if (left == 0 && right == 0) {
            result.add(paren);
            return;
        }
        if (left > 0) {
            helper(result, paren + "(", left - 1, right);
        }
        if (right > 0 && left < right) {
            helper(result, paren + ")", left, right - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().generateParenthesis(3));
        System.out.println(new Solution().generateParenthesis(1));
    }
}