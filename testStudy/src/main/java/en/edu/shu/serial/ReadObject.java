package en.edu.shu.serial;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * Created by jxxiangwen
 * Time: 17-8-24 上午9:56.
 */
public class ReadObject {
    public static void main (String args[]) {

        Address address;

        try{

            FileInputStream fin = new FileInputStream("/home/jxxiangwen/util/address");
            ObjectInputStream ois = new ObjectInputStream(fin);
            address = (Address) ois.readObject();
            ois.close();

            System.out.println(address);

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
