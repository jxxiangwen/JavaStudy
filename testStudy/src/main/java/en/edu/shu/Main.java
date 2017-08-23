package en.edu.shu;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static ThreadLocal<Integer> pos = new ThreadLocal<Integer>() {
        public Integer initialValue() {
            return 0;
        }
    };

    private static ThreadLocal<Integer> neg = new ThreadLocal<Integer>() {
        public Integer initialValue() {
            return 0;
        }
    };

    public int i;
    public static void main(String args[]) throws Exception {
        System.out.println(128 << 1);
        Pattern emoji = Pattern.compile(
                "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = emoji.matcher("\uD83C\uDF4E");
        System.out.println(matcher.find());
        System.out.println("--------------------------------------------------");
        Thread one = new Thread() {
            public void run() {
                Integer integer = pos.get();
                pos.set(integer++);
//                integer = neg.get();
//                pos.set(integer--);
            }
        };
        one.start();
        one.dumpStack();
        System.out.println(31 - Integer.numberOfLeadingZeros(8));
        System.out.println(Integer.numberOfLeadingZeros(-1));
        System.out.println(Integer.numberOfLeadingZeros(-2));
        System.out.println(Integer.numberOfLeadingZeros(-3));
        System.out.println(Integer.numberOfLeadingZeros(4));
        System.out.println(Integer.numberOfLeadingZeros(8));
        System.out.println(Integer.numberOfLeadingZeros(16));
        System.out.println((4 & (4 - 1)));
        System.out.println(0x00010000 >>> 15);
        List<Integer> list = Collections.singletonList(1);
        Method method = list.getClass().getMethod("size");
        System.out.println(list.size());
        System.out.println(method);

        System.out.println(method.getModifiers());
        System.out.println(Modifier.isPublic(method.getModifiers()));
        System.out.println(method.getClass());
        System.out.println(method.getDeclaringClass());
        System.out.println(Modifier.isPublic(method.getDeclaringClass().getModifiers()));
        System.out.println(method.isAccessible());

        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !(method.isAccessible())) {
            System.out.println("Collections set");
        }

        Field i = Main.class.getField("i");
        System.out.println(i.isAccessible());

        System.out.println("---------------------------------");
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        Method method1 = list1.getClass().getMethod("size");
        System.out.println(method1);
        System.out.println(method1.getModifiers());
        System.out.println(Modifier.isPublic(method1.getModifiers()));
        System.out.println(method1.getDeclaringClass());
        System.out.println(Modifier.isPublic(method1.getDeclaringClass().getModifiers()));
        System.out.println(method1.isAccessible());

        if ((!Modifier.isPublic(method1.getModifiers()) || !Modifier.isPublic(method1.getDeclaringClass().getModifiers())) && !(method1.isAccessible())) {
            System.out.println("Arraylist set");
        }

//        Scanner cin = new Scanner(System.in);
//        int begin, end;
//        while(cin.hasNextInt())
//        {
//            begin = cin.nextInt();
//            end = cin.nextInt();
//            List<Integer> list = Main.getFlower(begin,end);
//            if(list.size() == 0){
//                System.out.println("no");
//            }else{
//                for(int i = 0; i < list.size();i++){
//                    System.out.println(list.get(i));
//                }
//            }
//        }
    }

    public static List<Integer> getFlower(int begin, int end) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = begin; i <= end; i++) {
            if (Main.isFlower(i)) {
                list.add(i);
            }
        }
        return list;
    }

    public static boolean isFlower(int number) {
        int result = 0;
        int contains = number;
        while (number > 0) {
            result += (number % 10) * (number % 10) * (number % 10);
            number = number / 10;
        }
        if (result == contains) {
            return true;
        }
        return false;
    }
}
