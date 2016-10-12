package cn.edu.shu.leetcode1_20.solution_20;

import java.util.Stack;

/**
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
 * The brackets must close in the correct order, "()" and "()[]{}" are all valid but "(]" and "([)]" are not.
 */
public class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<Character>();
        for (Character c : s.toCharArray()) {
            if ("({[".contains(String.valueOf(c))) {
                stack.push(c);
            } else {
                if (!stack.isEmpty() && is_valid(stack.peek(), c)) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    private boolean is_valid(char c1, char c2) {
        return (c1 == '(' && c2 == ')') || (c1 == '{' && c2 == '}')
                || (c1 == '[' && c2 == ']');
    }

    public boolean isValid1(String s) {
        boolean flag = true;
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char current = s.charAt(i);
            if (current == '(' || current == '[' || current == '{') {
                stack.push(current);
                continue;
            }
            if (current == ')') {
                if(stack.empty()){
                    flag = false;
                    break;
                }
                char temp = stack.pop();
                if(temp == '('){
                    continue;
                }else {
                    flag = false;
                    break;
                }
            }
            if (current == ']') {
                if(stack.empty()){
                    flag = false;
                    break;
                }
                char temp = stack.pop();
                if(temp == '['){
                    continue;
                }else {
                    flag = false;
                    break;
                }
            }
            if (current == '}') {
                if(stack.empty()){
                    flag = false;
                    break;
                }
                char temp = stack.pop();
                if(temp == '{'){
                    continue;
                }else {
                    flag = false;
                    break;
                }
            }
        }
        if(!stack.empty()){
            flag = false;
        }
        return flag;
    }

    public static void main(String[] args) {
//        System.out.println(new Solution().isValid("(){sdfg}"));
//        System.out.println(new Solution().isValid("({(sdfg}"));
        System.out.println(new Solution().isValid("]"));
    }
}