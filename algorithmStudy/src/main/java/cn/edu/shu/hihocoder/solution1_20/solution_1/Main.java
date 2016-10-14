package cn.edu.shu.hihocoder.solution1_20.solution_1;

import java.util.*;

/**
 * 描述
 * <p>
 * 求两个整数A+B的和
 * 输入
 * <p>
 * 输入包含多组数据。
 * 每组数据包含两个整数A(1 ≤ A ≤ 100)和B(1 ≤ B ≤ 100)。
 * 输出
 * <p>
 * 对于每组数据输出A+B的和。
 * 样例输入
 * <p>
 * 1 2
 * 3 4
 * <p>
 * 样例输出
 * <p>
 * 3
 * 7
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            System.out.println(a + b);
        }
    }
}