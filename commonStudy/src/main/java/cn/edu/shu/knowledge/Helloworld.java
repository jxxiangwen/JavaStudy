package cn.edu.shu.knowledge;

/**
 * Created by jxxiangwen on 17-1-18.
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
public class Helloworld {
    private static SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMddHHmmssSSS");//160329225213999

    private static String ipHashCode = "";

    static {
        //取本机IP地址进行哈希取模，起到简单的分布式ID作用，但多机依然有重复的可能，未来会用ID生成器
        //ipHashCode = String.valueOf(InetAddress.getLocalHost().getHostAddress().hashCode() % 1000000);
        //阿里云上用IP地址哈希取模有异常，20161108181311119-16999835133984
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            sb.append(String.valueOf((int)(Math.random() * 10)));
        }
        ipHashCode = sb.toString();
        System.out.println("ip : " + ipHashCode);
    }

    public static Date string2Date(SimpleDateFormat sdf, String dateStr) {
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public synchronized static String getOutTradeNo(int bizType) {
        StringBuffer outTradeNo = new StringBuffer(sdf.format(new Date())).append(ipHashCode);
        for (int i = 0; i < 11; i++) {
            outTradeNo.append(String.valueOf((int) (Math.random() * 10)));
        }
        System.out.println("len:" + outTradeNo.length());
        return outTradeNo.toString();
    }

    public static void main(String []args) {
        System.out.println("Hello World!");
        SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMddHHmmss");
        System.out.println(Helloworld.string2Date(sdf,"20151217141848"));

        System.out.println(Helloworld.getOutTradeNo(1));
    }
}