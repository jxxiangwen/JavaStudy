package cn.edu.shu.five;

import java.util.List;

/**
 * Created by jxxiangwen on 17-5-20.
 */
public class ExtendTest {
    interface Fruit{}
    interface Apple extends Fruit{}
    interface Pear extends Fruit{}
    class Test implements Apple,Pear{

    }

    public void test(List<? extends Fruit> list){
        Fruit fruit = list.get(0);
    }

    public static void main(String[] args) {

    }
}
