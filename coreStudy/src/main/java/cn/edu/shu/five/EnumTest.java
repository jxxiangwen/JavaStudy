package cn.edu.shu.five;

import java.util.Scanner;

/**
 * Created by jxxiangwen on 15-12-29.
 */
public class EnumTest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a size:(SMALL,MEDIUM,LARGE,EXTRA_LARGE)");
        String input = in.next().toUpperCase();
        Size size = Enum.valueOf(Size.class,input);
        System.out.println("size=" + size);
        System.out.println("abbreviation=" + size.getAbbreviation());
        if(size == Size.EXTRA_LARGE){
            System.out.println("Good Job!");
        }
    }
}

enum Size {
    SMALL("S"), MEDIUM("M"), LARGE("L"), EXTRA_LARGE("XL");

    private String abbreviation;

    public String getAbbreviation() {
        return abbreviation;
    }

    private Size(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}