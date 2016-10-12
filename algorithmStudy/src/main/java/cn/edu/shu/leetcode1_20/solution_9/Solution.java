package cn.edu.shu.leetcode1_20.solution_9;

/**
 * Determine whether an integer is a palindrome. Do this without extra space.
 */
public class Solution {
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        int reverse = 0;
        int temp = x;
        while (temp != 0) {
            reverse = reverse * 10 + temp % 10;
            temp /= 10;
        }
        return reverse == x;
    }


    public static void main(String[] args) {
//        System.out.println(new Solution().isPalindrome(1000021));
        System.out.println(new Solution().isPalindrome(1000110001));
        System.out.println(new Solution().isPalindrome(123321));
        System.out.println(new Solution().isPalindrome(12321));
        System.out.println(new Solution().isPalindrome(1));
    }
}