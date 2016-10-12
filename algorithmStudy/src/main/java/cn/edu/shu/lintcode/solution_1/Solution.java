package cn.edu.shu.lintcode.solution_1;

class Solution {
    /**
     * 给出两个整数a和b, 求他们的和, 但不能使用 + 等数学运算符。
     * param a: The first integer
     * param b: The second integer
     * return: The sum of a and b
     */
    public int aplusb(int a, int b) {
        // write your code here, try to do it without arithmetic operators.
        int res = 0;
        int carry = 0;
        for (int i = 0; i < 32; i++) {
            int aa = (a >> i) & 1;
            int bb = (b >> i) & 1;
            res |= (aa ^ bb ^ carry) << i;
            if (aa == 1 && bb == 1 || ((aa == 1 || bb == 1) && carry == 1)) {
                carry = 1;
            } else {
                carry = 0;
            }
        }
        return res;
    }
};