package cn.edu.shu.function;

import java.util.HashMap;

/**
 * Created by jxxiangwen on 16-9-14.
 */
public class TestGc {
    public Object instance = null;
    private int _1MB = 1024 * 1024;
    private byte[] bigSize = new byte[_1MB * 2];

    public static void main(String[] args) {
        TestGc a = new TestGc();
        TestGc b = new TestGc();
        a.instance = b;
        b.instance = a;
        a = null;
        b = null;
        System.gc();
        HashMap map;
    }
}
