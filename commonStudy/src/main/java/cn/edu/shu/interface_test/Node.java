package cn.edu.shu.interface_test;

/**
 * Created by jxxiangwen on 16-9-11.
 */
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Node{
    int value;
    List<Node> child;
    public Node(int value){
        this.value = value;
    }
    public void setChild(List<Node> child){
        this.child = child;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Queue<Node> currentLayer = new ArrayDeque<>();
        while(sc.hasNext()){
            String line = sc.nextLine();
            String [] values = line.split("\\s+");
            List<Node> child = new ArrayList<>();
            for(String value : values){
                if(currentLayer.isEmpty()){
                    currentLayer.add(new Node(Integer.parseInt(value)));
                }else{

                }
            }
        }
    }
}
