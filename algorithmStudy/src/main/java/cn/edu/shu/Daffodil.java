package cn.edu.shu;

/**
 * Created by jxxiangwen on 2015/10/19 0019.
 *
 * @author jxxiangwen
 * @version 1.0
 */
public class Daffodil {

    /**
     * 题目：打印出所有的 "水仙花数 "，所谓 "水仙花数 "是指一个三位数，其各位数字立方和等于该数本身。
     * 例如：153是一个 "水仙花数 "，因为153=1的三次方＋5的三次方＋3的三次方。
     *
     * @param number 要判断的数值
     * @return 是否是水仙花数
     */
    public static boolean isDaffodil(int number) {
        int sum = 0;//存储各位数值立方和
        int remainder = number % 10;//存储各位余数
        int result = number / 10;//存储各位除后结果
        while (true) {
            sum += (int) Math.pow(remainder, 3);
            if (result == 0) {
                break;
            } else {
                remainder = result % 10;
                result = result / 10;
            }
        }
        return sum == number ? true : false;
    }

}
