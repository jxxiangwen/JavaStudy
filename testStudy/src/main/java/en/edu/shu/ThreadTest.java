package en.edu.shu;

import java.util.function.Supplier;

/**
 * Created by jxxiangwen on 16-10-22.
 */
public class ThreadTest extends Thread implements Runnable{
    int value = 0;

    @Override
    public void run() {
        super.run();
    }

    public static void main(String[] args) throws InterruptedException {
//        final ThreadTest threadTest = new ThreadTest();
//        Thread thread1 = new Thread(() -> {
//            System.out.println(threadTest.value);
//            threadTest.value++;
//            System.out.println(threadTest.value);
//        });
//        thread1.run();
//        Thread.sleep(1000);
//        System.out.println(threadTest.value);
        System.out.println(Integer.MAX_VALUE*1.0/0x61c88647);
        ThreadLocal<Integer> integer = ThreadLocal.withInitial(
                new Supplier<Integer>() {
                    @Override
                    public Integer get() {
                        return null;
                    }
                }
        );
        integer.get();
    }
}
