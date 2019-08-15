package en.edu.shu;

import org.apache.tools.ant.types.resources.Archives;

import javax.xml.transform.Source;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.*;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static class Father {

    }

    public static class Son extends Father {

    }

    public static class MyList<E> extends ArrayList<E> {

        // toArray() 的同名方法
        public String[] toArray() {
            return new String[]{"1", "2", "3"};
        }

    }

    static <T extends Number> T getObject() {
        return (T) Long.valueOf(1L);
    }


    public void get() throws Exception {
        ProtectionDomain protectionDomain = getClass().getProtectionDomain();
        CodeSource codeSource = protectionDomain.getCodeSource();
        URI location = (codeSource == null ? null : codeSource.getLocation().toURI());
        String path = (location == null ? null : location.getSchemeSpecificPart());
        if (path == null) {
            throw new IllegalStateException("Unable to determine code source archive");
        }
        File root = new File(path);
        if (!root.exists()) {
            throw new IllegalStateException(
                    "Unable to determine code source archive from " + root);
        }

    }

    public class TestPool implements Runnable {
        @Override
        public void run() {
            while (true) {

            }
        }
    }
    public static final ThreadLocal<String> local = new ThreadLocal<String>(){
        @Override
        public String initialValue() {
            return "test";
        }
    };

    public static void main(String... args) throws Exception {
        Thread.currentThread().interrupt();
        if(Thread.currentThread().isInterrupted()){
            Thread.currentThread().interrupt();
        }
        System.out.println(local.get());
        local.remove();
        System.out.println(local.get());
        System.out.println(1 << 1);
        System.out.println((1 << 16) - 1);
        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock ();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        lock.readLock().lock();
                        Thread.sleep(i * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.readLock().unlock();
                    }
                    System.out.println("innner" + i);
                }
            }
        });
        thread.start();
        for (int i = 0; i < 10; i++) {
            try {
                lock.readLock().lock();
                Thread.sleep(i * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.readLock().unlock();
            }
            System.out.println("outter" + i);
        }
        thread.join();
    }

    private static ThreadLocal<Integer> pos = new ThreadLocal<Integer>() {
        public Integer initialValue() {
            return 0;
        }
    };

    private static ThreadLocal<Integer> neg = new ThreadLocal<Integer>() {
        public Integer initialValue() {
            return 0;
        }
    };

//    public static void main(String args[]) throws Exception {
//        System.out.println(System.currentTimeMillis() / 86400000);
//        String day = Long.toHexString(System.currentTimeMillis() / 86400000);
//        System.out.println(day);
//        Son[] sons = new Son[]{new Son(), new Son()};
//        System.out.println(sons.getClass());            // class [Lcom.johnnie.test.Test$Son;
//
//        Father[] fathers = sons;
//        System.out.println(fathers.getClass());     // class [Lcom.johnnie.test.Test$Son;
//
////        fathers[0] = new Father();                          // java.lang.ArrayStoreException
//        List<String> ss = new LinkedList<String>();             // LinkedList toArray() 返回的本身就是 Object[]
//        ss.add("123");
//        Object[] objs = ss.toArray();
//        objs[0] = new Object();
//
//        // 此处说明了：c.toArray might (incorrectly) not return Object[] (see 6260652)
//        ss = new MyList<String>();
//        objs = ss.toArray();
//        System.out.println(objs.getClass());        // class [Ljava.lang.String;
//        objs[0] = new Object();                         // java.lang.ArrayStoreException: java.lang.Object
//    }

    public static List<Integer> getFlower(int begin, int end) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = begin; i <= end; i++) {
            if (Main.isFlower(i)) {
                list.add(i);
            }
        }
        return list;
    }

    public static boolean isFlower(int number) {
        int result = 0;
        int contains = number;
        while (number > 0) {
            result += (number % 10) * (number % 10) * (number % 10);
            number = number / 10;
        }
        if (result == contains) {
            return true;
        }
        return false;
    }
}
