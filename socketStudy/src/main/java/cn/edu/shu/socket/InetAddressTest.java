package cn.edu.shu.socket;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jxxiangwen on 16-11-10.
 */
class Person{}
class Student extends Person{}
class Employee extends Person{}
public class InetAddressTest {
    float sum_elements(float a[], int length) {
        int i;
        float result = 0;
        for (i = 0; i <= length - 1; i++) {
            System.out.printf("i = %d\n", i);
            result += a[i];
        }
        return result;
    }

    private static String test;

    @PostConstruct
    public void init(){
        System.out.println("postConstruct");
        test = "etst";
    }

    public static void main(String[] args) throws UnknownHostException {
        System.out.println(test);
//        Person[] per = new Person[2];
        Person[] per = new Student[2];
//        per[1] = new Person();
        int monthInt = 4;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, monthInt);
        System.out.println(calendar.getTime());
        StringBuilder stringBuilder = new StringBuilder("sadfasf");
        System.out.println(stringBuilder.deleteCharAt(stringBuilder.length() -1));
        InetAddress localHost = InetAddress.getLocalHost();
        InetAddress byName = InetAddress.getByName("116.214.11.98");

        System.out.println(byName.getHostAddress());
        InetAddress byName1 = InetAddress.getByName("google.com");
        System.out.println(byName1.getHostAddress());
        System.out.println(byName1.getHostName());
        byte[] address = localHost.getAddress();
        System.out.println(Arrays.toString(address));
        System.out.println(localHost.getCanonicalHostName());
        System.out.println(localHost.getHostAddress());
        System.out.println(localHost.getHostName());
        new InetAddressTest().sum_elements(new float[]{0.1f,1.1f},1);
    }
}
