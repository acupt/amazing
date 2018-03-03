package com.acupt.world;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by liujie on 2018/3/2.
 */
public class Pangu {

    private Queue<Point> road = new LinkedBlockingQueue<>();

    private int[][] originMap;

    private int size;

    public Pangu(int size) {
        this.size = size;
        originMap = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                originMap[i][j] = PointOrigin.create().getCode();
            }
        }
    }

    public int[][] originMap() {
        return originMap;
    }

    public static void main(String[] args) {
        Pangu p = new Pangu(3);
        int[][] map = p.originMap;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
