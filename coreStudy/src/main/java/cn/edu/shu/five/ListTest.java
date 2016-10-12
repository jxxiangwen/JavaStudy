package cn.edu.shu.five;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jxxiangwen on 16-1-2.
 */
public class ListTest {
    public static void main(String[] args) {
        List<String> listTest = new ArrayList<>();
        listTest.add("this");
        listTest.add("this");
        listTest.add("that");
        listTest.remove("this");
        for (String s : listTest) {
            System.out.println(s);
        }
    }
}
