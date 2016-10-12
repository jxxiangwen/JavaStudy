package cn.edu.shu.function;

/**
 * Created by jxxiangwen on 16-9-14.
 */
public class Test {
    private int stackLength = 1;

    public void stackLeak(){
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        Test test = new Test();
        try {
            test.stackLeak();
        }catch (Throwable e){
            System.out.println(test.stackLength);
            throw e;
        }
    }
}
