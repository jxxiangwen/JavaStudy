package cn.edu.shu.interface_test;

/**
 * Created by jxxiangwen on 16-8-22.
 */
public class C implements B {

    public Integer integer;
    @Override
    public void same() {
        System.out.println("in C");
    }

    public static void main(String[] args) {
        int i;
        C c = new C();
        c.same();
        System.out.println(c.integer);
    }
}