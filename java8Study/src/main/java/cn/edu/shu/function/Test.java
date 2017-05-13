package cn.edu.shu.function;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by jxxiangwen on 16-9-14.
 */
public class Test {
    private int stackLength = 1;

    public void stackLeak(){
        stackLength++;
        stackLeak();
    }

    /**
     * 获得指定文件的byte数组
     */
    public static byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static void parseExcle(InputStream in){
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        int totalCount = 0;
        try {
            String rec = null;// 一行
            // 读取一行
            while ((rec = br.readLine()) != null) {
                if(StringUtils.isEmpty(rec)){
                    continue;
                }
                totalCount ++ ;
            }
            System.out.println(totalCount);
        } catch (Exception e) {
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (br != null) {
                    br.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String s = "❤☀★☂☻♞\uD801\uDC01";
        System.out.println(s);
        System.out.println(s.length());
        System.out.println(s.codePointCount(0,s.length()));
        System.out.println(s.codePointAt(0));
//        byte[] bytes = getBytes("/home/jxxiangwen/util/test.xlsx");
//        InputStream inputStream = new ByteArrayInputStream(bytes);
//        parseExcle(inputStream);
//        String property = System.getProperty("user.dir");
//        String jar = property + File.separator + "c3p0-0.9.1.2.jar";
//        JarFile jarFile  = new JarFile(jar);
//        Enumeration<JarEntry> enu = jarFile.entries();
//        while (enu.hasMoreElements()) {
//            JarEntry entry = (JarEntry)enu.nextElement();
//            String name = entry.getName();
//            //System.out.println(name);
//            if(name.endsWith(File.separator)){
//                continue;
//            }
//            if(!name.endsWith(".class")){
//                continue;
//            }
//            System.out.println(name);
//        }
//        jarFile.close();
//        List<Integer> list = new ArrayList<>();
//        list.addAll(Arrays.asList(new Integer(0),new Integer(1),new Integer(2)));
//
//        for (Iterator<Integer> itr = list.iterator(); itr.hasNext();) {
//            Integer tmp =  itr.next();
//            if(tmp.equals(1)){
////                itr.remove();
//                list.remove(tmp);
//            }
//        }
//        Test test = new Test();
//        try {
//            test.stackLeak();
//        }catch (Throwable e){
//            System.out.println(test.stackLength);
//            throw e;
//        }
    }
}
