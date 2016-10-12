package cn.edu.shu.function;

import java.util.Scanner;

/*
 * 第一个位置选定，第二个位置和最后个位置不能选
 * 第一个位置不选定，则第二个位置选定，后面不用管
 * */
public class RedBag {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        for (int ii = 0; ii < n; ii++) {
            String s = scanner.next();
            String[] ss = s.split(" ");
            int[] hb = new int[ss.length];
            for (int i = 0; i < ss.length; i++) {
                hb[i] = Integer.parseInt(ss[i]);
            }
            int len = hb.length;
            int[] dp1 = new int[len];
            dp1[0] = hb[0];
            dp1[1] = hb[0];
            for (int i = 2; i < len - 1; i++)
                dp1[i] = Math.max(dp1[i - 2] + hb[i], dp1[i - 1]);
            int result = dp1[len - 2];
            for (int i = 1; i < len; i++) {
                int[] dp2 = new int[len];
                dp2[i] = hb[i];
                if (i + 1 < len)
                    dp2[i + 1] = dp2[i];
                for (int k = i + 2; k < len; k++) {
                    dp2[k] = Math.max(dp2[k - 2] + hb[k], dp2[k - 1]);
                }
                result = Math.max(dp2[len - 1], result);
            }
            System.out.println(result);
        }
    }
//    public static void main(String[] args) {
//        Scanner scanner=new Scanner(System.in);
//        int N=Integer.parseInt(scanner.nextLine());
//        while(N-->0){
//            String nums=scanner.nextLine();
//            String bagstring[]=nums.split(" ");
//            int[] bags  = new int[bagstring.length];
//            for(int i = 0; i < bagstring.length; i++){
//                bags[i] = Integer.parseInt(bagstring[i]);
//            }
//            System.out.println(getMax1(bags,bags.length-1));
//            System.out.println(getMax1(reverse(bags),bags.length-1));
//            System.out.println(Math.max(getMax1(bags,bags.length-1),getMax1(reverse(bags),bags.length-1)));
//        }
//        scanner.close();
//    }

    public static int getMax1(int bags[], int index) {
        if (bags.length == 0) {
            return 0;
        }
        //第二个红包不拿
        if (bags.length == 1 || index == 0 || bags.length == 2 || index == 1) {
            return bags[0];
        }
        if (index == bags.length - 1) {
            //最后个红包不拿
            return getMax1(bags, index - 1);
        }
        return Math.max((bags[index] + getMax1(bags, index - 2)), getMax1(bags, index - 1));
    }

    public static int[] reverse(int bags[]) {
        int[] b = new int[bags.length];
        for (int i = 0; i < bags.length; i++) {
            b[i] = bags[bags.length - i - 1];
        }
        return b;
    }
}