package test2;

import java.util.*;

/**
 * Created by jxxiangwen on 16-8-2.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            Map<String, Integer> map = new HashMap<>();
            int[] value = new int[n];
            for (int i = 0; i < n; i++) {
                value[i] = sc.nextInt();
            }
            Arrays.sort(value);
            for (int i = 0; i < m; i++) {
                String food = sc.next();
                if (!map.containsKey(food)) {
                    map.put(food, 1);
                } else {
                    map.put(food, map.get(food) + 1);
                }
            }
            Iterator<String> stringIterator = map.keySet().iterator();
            int[] foodKinds = new int[map.size()];
            int index = 0;
            while (stringIterator.hasNext()) {
                foodKinds[index] = map.get(stringIterator.next());
                index++;
            }
            Arrays.sort(foodKinds);
            System.out.println(getMin(value, foodKinds) + " " + getMax(value, foodKinds));
        }
    }

    public static int getMin(int[] value, int[] foodKinds) {
        int result = 0;
        int size = foodKinds.length;
        for (int i = 0; i < size; i++) {
            result += foodKinds[size - i - 1] * value[i];
        }
        return result;
    }

    public static int getMax(int[] value, int[] foodKinds) {
        int result = 0;
        int size = foodKinds.length;
        int valueSize = value.length;
        for (int i = 0; i < size; i++) {
            result += foodKinds[size - i - 1] * value[valueSize - i - 1];
        }
        return result;
    }
}
//5 3
//        4 2 1 10 5
//        apple
//        orange
//        mango
//        6 5
//        3 5 1 6 8 1
//        peach
//        grapefruit
//        banana
//        orange
//        orange