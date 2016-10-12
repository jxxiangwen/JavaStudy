package cn.edu.shu;

/**
 * Created by jxxiangwen on 2015/10/10 0010.
 *
 * @author jxxiangwen
 * @version 1.0
 */
public class Fibonacci {

    /**
     * 题目：古典问题：有一对兔子，从出生后第3个月起每个月都生一对兔子，小兔子长到第四个月后每个月又生一对兔子，假如兔子都不死，问每个月的兔子总数为多少？
     * 兔子的规律为数列1,1,2,3,5,8,13,21....
     * @param value 月份
     * @return 兔子总数
     */
    public static int fibonacci(int value) {
        if (value == 1 || value == 2) {
            return 1;
        } else {
            return fibonacci(value - 1) + fibonacci(value - 2);
        }

    }
}
