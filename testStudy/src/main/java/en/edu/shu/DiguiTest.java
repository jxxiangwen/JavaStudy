package en.edu.shu;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jxxiangwen on 16-9-24.
 */
public class DiguiTest {
    public int testFuction(int level,String side){
        if(level < 0)
            return 0;
        System.out.println(level + side);
        return testFuction(level -1, side + " + L") + testFuction(level -1,side + " + R");
    }

    public static void main(String[] args) {
        new DiguiTest().testFuction(3,"M");
        ConcurrentHashMap<String, String> stringStringConcurrentHashMap = new ConcurrentHashMap<>();
        Hashtable<String,String> hashTable = new Hashtable<>();
        Iterator<String> iterator = hashTable.keySet().iterator();
        for(Map.Entry<String, String> entry :hashTable.entrySet()){

        }
    }
}
