package cn.edu.shu.leetcode1_20.solution_7;

/**
 * Reverse digits of an integer.
 * Example1: x = 123, return 321
 * Example2: x = -123, return -321
 */
public class Solution {
    public int reverse(int x) {
        int result = 0;
        while(x != 0){
            int temp = result * 10 + x % 10;
            x /= 10;
            if (temp / 10 != result) {
                result = 0;
                break;
            }
            result = temp;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().reverse(0));
        System.out.println(new Solution().reverse(-123));
        System.out.println(new Solution().reverse(-2147483648));
    }
}