package en.edu.shu;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by jxxiangwen on 17-4-24.
 */
public class GzipUtils {

    /**
     * 对字符串进行gzip压缩
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static String compress(String data) throws IOException {
        if (null == data || data.length() <= 0) {
            return data;
        }
        //创建一个新的byte数组输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //使用默认缓冲区大小创建新的输出流
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        //将b.length个字节写入此输出流
        gzip.write(data.getBytes());
        gzip.flush();
        gzip.close();

        //使用指定的charsetName，通过解码字节将缓冲区内容转换为字符串
        return out.toString("UTF-8");
    }

    /**
     * 对字符串进行解压缩
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String unCompress(String data) throws Exception {
        if (null == data && data.length() <= 0) {
            return data;
        }
        //创建一个新的byte数组输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //创建一个byte数组输入流
        ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes("ISO-8859-1"));
        //创建gzip输入流
        GZIPInputStream gzip = new GZIPInputStream(in);
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = gzip.read(buf)) >= 0) {
            out.write(buf, 0, len);
        }
        // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
        return out.toString("UTF-8");
    }

    public static void main(String[] args) throws IOException{
        System.out.println(compress("{ \"device_type\": \"android\", \"event_type\": \"view\", \"app_type\": 2, \"business_tags\": { \"app_id\": \"10018\" }, \"deviceModel\": \"web\", \"app_version\": \"803\", \"network\": \"-1\", \"sourceId\": 0, \"timestamp\": 1473412429746, \"longitude\": 0, \"user_id\": 2, \"log_id\": \"log_id:1473412431427\", \"platform\": \"android\", \"local_city_id\": 0, \"ip\": \"192.168.201.201\", \"element_id\": \"pageview\", \"platform_version\": \"-1\", \"user_agent\": \"Mozilla/5.0 Chrome/53.0.2785.116 Safari/537.36\", \"latitude\": 0, \"page_name\": \"detail_10018\" }"));
    }

}