package cn.edu.shu.leetcode1_20.solution_8;

/**
 * Implement atoi to convert a string to an integer.
 */
public class Solution {
    public int myAtoi(String str) {
        str = str.trim();
        if (str.equals("")) {
            return 0;
        }
        //去除两个以上++或--
        if(str.indexOf("+") != str.lastIndexOf("+") || str.indexOf("-") != str.lastIndexOf("-")){
            return 0;
        }
        //去除同时包含+和-
        if (str.contains("+") && str.contains("-")) {
            return 0;
        }
        //去除+
        str = str.replace("+", "");
        //正负号
        boolean below = str.contains("-");
        //去除-
        str = str.replace("-", "");
        int value = 0;
        for (int i = 0; i < str.length(); i++) {
            int b = str.charAt(i) - '0';
            if (b > 9 || b < 0) {
                break;
            }
            int temp = value * 10 + (str.charAt(i) - '0');
            if (temp / 10 != value) {
                if(below){
                    return Integer.MIN_VALUE;
                }else {
                    return Integer.MAX_VALUE;
                }
            }
            value = temp;
        }
        return below ? -value : value;
    }

    public static void main(String[] args) {

        System.out.println(new Solution().myAtoi("+1234"));
        System.out.println(new Solution().myAtoi("  -0012a42"));
        System.out.println(new Solution().myAtoi("+-2"));
        System.out.println(new Solution().myAtoi("-2147483648"));
        System.out.println(new Solution().myAtoi("   +0 123"));
    }
}