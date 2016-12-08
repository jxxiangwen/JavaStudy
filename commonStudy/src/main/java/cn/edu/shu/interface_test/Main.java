package cn.edu.shu.interface_test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static {
        x = 1;
    }

    public static int x;

    public static void main(String[] args) {
        String a = "aa";
        String s = new String("aa");
        System.out.println(s == s.intern());
        try {
            Class<?> aClass = Class.forName("");
            Connection connection = DriverManager.getConnection("");
            PreparedStatement preparedStatement = connection.prepareStatement("");
            ResultSet resultSet = preparedStatement.executeQuery();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Thread t1 = new Thread();
        Thread t2 = new Thread();
        Thread t3 = new Thread();
        Thread t4 = new Thread();
        Thread t5 = new Thread();
        //将线程放入池中进行执行
        executorService.execute(t1);
        executorService.execute(t2);
        executorService.execute(t3);
        executorService.execute(t4);
        executorService.execute(t5);
//        for (int i=1;i < 33; i++){
//            num = num >> 1;
//            System.out.println(i + ":" + (num));
//        }
    }

    public <E> E returnE(E object){
        return null;
    }

    class Handler implements InvocationHandler{
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Proxy.newProxyInstance(proxy.getClass().getClassLoader(),proxy.getClass().getInterfaces(),this);
            return null;
        }
    }

    class Test<T>{
        public T returnT(){
            return null;
        }
    }
}
