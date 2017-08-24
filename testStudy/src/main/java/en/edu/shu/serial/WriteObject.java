package en.edu.shu.serial;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Created by jxxiangwen
 * Time: 17-8-24 上午9:55.
 */
public class WriteObject {
    public static void main (String args[]) {

        Address address = new Address();
        address.setStreet("wall street");
        address.setCountry("united states");

        try{

            FileOutputStream fout = new FileOutputStream("/home/jxxiangwen/util/address");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(address);
            oos.close();
            System.out.println("Done");

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
