package en.edu.shu.serial;

import java.io.Serializable;

/**
 * Created by jxxiangwen
 * Time: 17-8-24 上午9:55.
 */
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    String street;
    String country;

    public void setStreet(String street){
        System.out.println("使用set");
        this.street = street;
    }

    public void setCountry(String country){
        System.out.println("使用set");
        this.country = country;
    }

    public String getStreet(){
        return this.street;
    }

    public String getCountry(){
        return this.country;
    }

    @Override
    public String toString() {
        return new StringBuffer(" Street : ")
                .append(this.street)
                .append(" Country : ")
                .append(this.country).toString();
    }
}