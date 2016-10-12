package cn.edu.shu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jxxiangwen on 2015/10/16 0016.
 *
 * @author jxxiangwen
 * @version 1.0
 */
public class PrimeNumber {

    /**
     * Description:输出区间内的素数
     *
     * @author jxxiangwen
     * @Time 2015-10-16
     * @version 1.0
     */
    public void printPrime(int from, int end) {
        int begin = from;
        List<Integer> primeList = new ArrayList<>();
        if (from > end) {
            throw new RuntimeException("第二个数必须大于等于第一个数");
        } else {
            if (end < 2) {
                System.out.printf("在 %d 和 %d 之间不存在素数", from, end);
                return;
            }
            if (from < 2) {
                from = 2;
            }
            while (from <= end) {
                if (isPrime(from)) {
                    primeList.add(from);
                }
                from++;
            }
            System.out.printf("在 %d 和 %d 之间有 %d 个素数，分别为 %s", begin, end, primeList.size(), primeList.toString());
        }
    }

    /**
     * Description:判断是否是素数
     *
     * @author jxxiangwen
     * @Time 2015-10-16
     * @version 1.0
     */
    public boolean isPrime(int value) {
        int end = value / 2;
        for (int i = 2; i <= end; i++) {
            if (value % i == 0) {
                return false;
            }
        }
        return true;
    }
}
