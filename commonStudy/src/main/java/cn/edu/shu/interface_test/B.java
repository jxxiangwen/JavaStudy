package cn.edu.shu.interface_test;

/**
 * Created by jxxiangwen on 16-8-22.
 */
public interface B {
    default void same(){
        System.out.println("in B");
    }
}
