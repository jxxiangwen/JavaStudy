package cn.edu.shu.function;

import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;

/**
 * Created by jxxiangwen on 17-5-27.
 */
@FunctionalInterface
public interface Lamda {
    public void test();
    public static void main(String[] args) {
        List<Long> list = new ArrayList<>();
        LongSummaryStatistics longSummaryStatistics = list.stream().mapToLong(i -> i + 1).summaryStatistics();
    }
    default public <T> void  test(List<? super T>list,T args){
        list.add(args);
    }
}
