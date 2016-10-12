package en.edu.shu;

import java.util.*;
public class Main
{
    public static void main(String args[])
    {
        Scanner cin = new Scanner(System.in);
        int begin, end;
        while(cin.hasNextInt())
        {
            begin = cin.nextInt();
            end = cin.nextInt();
            List<Integer> list = Main.getFlower(begin,end);
            if(list.size() == 0){
                System.out.println("no");
            }else{
                for(int i = 0; i < list.size();i++){
                    System.out.println(list.get(i));
                }
            }
        }
    }

    public static List<Integer> getFlower(int begin,int end){
        List<Integer> list = new ArrayList<Integer>();
        for(int i = begin; i <= end;i++){
            if(Main.isFlower(i)){
                list.add(i);
            }
        }
        return list;
    }

    public static boolean isFlower(int number){
        int result = 0;
        int contains = number;
        while(number > 0 ){
            result += (number % 10) * (number % 10) * (number % 10);
            number = number / 10;
        }
        if(result == contains){
            return true;
        }
        return false;
    }
}
