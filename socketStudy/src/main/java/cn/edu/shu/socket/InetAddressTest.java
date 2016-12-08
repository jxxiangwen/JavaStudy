package cn.edu.shu.socket;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Created by jxxiangwen on 16-11-10.
 */
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

    public static void main(String[] args) throws UnknownHostException {
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
