package cn.edu.shu.second;

/**
 * Created by jxxiangwen on 2015/10/9 0009.
 * @version 1.0
 * @author jxxiangwen
 */
public class Welcome {
    public static void main(String[] args) {
        String[] greeting = new String[3];

        greeting[0] = "Welcome to Core Java";
        greeting[1] = "by Zouxiangwen ";
        greeting[2] = "and Hanlu!";

        for (String g : greeting) {
            System.out.print(g);
        }
    }
}
