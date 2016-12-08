package cn.edu.shu.leetcode.solution21_40.solution_30;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by jxxiangwen on 16-10-20.
 */
class Node{
    int val;
    Node next;
    public Node(int val){
        this.val = val;
    }
}
public abstract class LinkLoop{
    public static void printArray(Object... args){
        System.out.println("Object");
        for(Object o:args){
            System.out.println(o);
        }
    }
    public static void printArray(int... args){
        System.out.println("int");
        for(Object o:args){
            System.out.println(o);
        }
    }

    public static boolean hasLoop(Node n){
        //定义两个指针tmp1,tmp2
        Node tmp1 = n;
        Node tmp2 = n.next;

        while(tmp2 != null && tmp2.next!=null){
            tmp1 = tmp1.next;  //每次迭代时，指针1走一步，指针2走两步
            tmp2 = tmp2.next.next;
            if(tmp2 == null)return false;//不存在环时，退出
            int d1 = tmp1.val;
            int d2 = tmp2.val;
            if(d1 == d2)return true;//当两个指针重逢时，说明存在环，否则不存在。
        }
        return false; //如果tmp2为null，说明元素只有一个，也可以说明是存在环
    }

    //方法2：将每次走过的节点保存到hash表中，如果节点在hash表中，则表示存在环
    public static boolean hasLoop2(Node n){
        Node temp1 = n;
        HashMap<Node,Node> ns = new HashMap<Node,Node>();
        while(n!=null){
            if(ns.get(temp1)!=null)return true;
            else ns.put(temp1, temp1);
            temp1 = temp1.next;
            if(temp1 == null)return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
//        Node n5 = new Node(5);

        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = null;
//        n5.next = n1;  //构造一个带环的链表,去除此句表示不带环

        System.out.println(hasLoop(n1));
        System.out.println(hasLoop2(n1));
//        System.getProperties().list(System.out);
        Random random = new Random();
        for(int i = 0;i < 10; i++){
            System.out.println(random.nextInt(10));
        }
        short s = 10;
        s += 1;
        s = (short)(s + 1);
        Integer integer1 = 47;
        Integer integer2 = 47;
        System.out.println(integer1 == integer2);
        float a = 12.1f;
        LinkLoop.printArray((Object) new Integer[]{1,2,3});
//        LinkLoop.printArray();
        outer:
        while (true){
            while (true){
                break outer;
            }
        }
        System.out.println("outer");
        try {
            Thread.sleep(1000000000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
