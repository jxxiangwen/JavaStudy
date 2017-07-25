package cn.edu.shu;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jxxiangwen
 * Time: 17-7-19 下午2:38.
 */
public class CountDownStudy {
    private CountDownLatch countDownLatch = new CountDownLatch(3);

    class Work implements Runnable {
        @Override
        public void run() {
            System.out.println("开始工作");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("工作完成");
            countDownLatch.countDown();
        }
    }

    class Boss implements Runnable {
        @Override
        public void run() {
            System.out.println("等待检查工作");
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("开始检查工作");
        }
    }

    class SyncAdd implements Runnable{
        private int i = 0;
        @Override
        public void run() {
            for (int j = 0; j < 10000000; j++){
                add();
            }
            System.out.println("i = " + i);
        }

        private synchronized void add(){
            i++;
        }
    }

    class LockAdd implements Runnable{
        private int i = 0;
        private Semaphore lock = new Semaphore(1);
        @Override
        public void run() {
            for (int j = 0; j < 10000000; j++){
                add();
            }
            System.out.println("i = " + i);
        }

        private void add() {
            try {
                lock.acquire();
                i++;
                lock.release();
            }catch (InterruptedException e){

            }
            finally {
            }
        }
    }

    public void countTest(){
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
        CountDownStudy countDownStudy = new CountDownStudy();
        Work work1 = countDownStudy.new Work();
        Work work2 = countDownStudy.new Work();
        Work work3 = countDownStudy.new Work();
        Boss boss = countDownStudy.new Boss();
        fixedThreadPool.submit(boss);
        fixedThreadPool.submit(work1);
        fixedThreadPool.submit(work2);
        fixedThreadPool.submit(work3);
        fixedThreadPool.shutdown();
    }

    public void syncAddTest(){
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        CountDownStudy countDownStudy = new CountDownStudy();
        long start = System.currentTimeMillis();
        for(int i= 0; i < 10;i++){
            SyncAdd add = countDownStudy.new SyncAdd();
            fixedThreadPool.submit(add);
        }
        fixedThreadPool.shutdown();
        try {//等待直到所有任务完成
            fixedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    public void lockAddTest(){
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        CountDownStudy countDownStudy = new CountDownStudy();
        long start = System.currentTimeMillis();
        for(int i= 0; i < 10;i++){
            LockAdd add = countDownStudy.new LockAdd();
            fixedThreadPool.submit(add);
        }
        fixedThreadPool.shutdown();
        try {//等待直到所有任务完成
            fixedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    public static void main(String[] args) {
        CountDownStudy countDownStudy = new CountDownStudy();
        countDownStudy.syncAddTest();
        countDownStudy.lockAddTest();
    }

}
