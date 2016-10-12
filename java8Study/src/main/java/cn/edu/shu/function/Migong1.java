package cn.edu.shu.function;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by jxxiangwen on 16-8-2.
 */
public class Migong1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = "";
        //初始化数组
        while ((str = sc.nextLine()) != null && str.length() != 0) {
            String[] sizeArray = str.split(" ");
            int xSize = Integer.parseInt(sizeArray[0]);
            int ySize = Integer.parseInt(sizeArray[1]);
            String[][] migong = new String[xSize][ySize];
            for (int i = 0; i < xSize; i++) {
                str = sc.nextLine();
                String[] migongArray = str.split(" ");
                for (int j = 0; j < ySize; j++) {
                    migong[i][j] = migongArray[j];
                }
            }
            //初始步骤
            str = sc.nextLine();
            Direction first = new Direction();
            String[] direction = str.split(" ");
            first.x = Integer.parseInt(direction[0]);
            first.y = Integer.parseInt(direction[1]);
            int step = Integer.parseInt(sc.nextLine());
            Direction[] directions = new Direction[step];
            for (int i = 0; i < step; i++) {
                str = sc.nextLine();
                String[] directionString = str.split(" ");
                directions[i] = new Direction();
                directions[i].x = Integer.parseInt(directionString[0]);
                directions[i].y = Integer.parseInt(directionString[1]);
            }
            System.out.println(Migong1.getMinStep(migong, directions, first));
        }
    }

    public static int getMinStep(String[][] migon, Direction[] directions, Direction first) {
        Queue<StepObject> stepObjects = new LinkedList<>();
        stepObjects.add(new StepObject(first.x, first.y));
        int minStep = Integer.MAX_VALUE;
        while (!stepObjects.isEmpty()) {
            StepObject stepObject = stepObjects.remove();
            int x = stepObject.x;
            int y = stepObject.y;
            migon[y][x] = "H";
            for (int i = 0; i < directions.length; i++) {
                int nextX = x + directions[i].x;
                int nextY = y + directions[i].y;
                if (check(nextX, nextY, migon)) {
                    stepObjects.add(new StepObject(nextX, nextY, stepObject.step + directions[i].getStep()));
                    minStep = getMinStep(nextX, nextY, migon, minStep, stepObject.step + directions[i].getStep());
                }
            }
        }
        if(Integer.MAX_VALUE == minStep){
            return -1;
        }else {
            return minStep;
        }
    }

    public static boolean check(int x, int y, String[][] migon) {
        if (migon.length > y && migon[0].length > x && 0 < y && 0 < x && !migon[y][x].equals("X") && !migon[y][x].equals("H")) {
            return true;
        }
        return false;
    }

    public static int getMinStep(int x, int y, String[][] migon, int minStep, int step) {
        if (y == migon.length -1 && x == migon[0].length -1) {
            if (step < minStep) {
                return step;
            }
        }
        return minStep;
    }
}

class StepObject {
    int x;
    int y;
    int step;

    public StepObject() {

    }

    public StepObject(int x, int y) {
        this.x = x;
        this.y = y;
        this.step = 0;
    }

    public StepObject(int x, int y, int step) {
        this.x = x;
        this.y = y;
        this.step = step;
    }
}

class Direction {
    int x;
    int y;

    public int getStep() {
        return Math.abs(this.x) > Math.abs(this.y) ? Math.abs(this.x) : Math.abs(this.y);
    }
}
