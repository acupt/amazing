package com.acupt.world;

import java.util.Random;

/**
 * Created by liujie on 2018/3/2.
 */
public enum PointOrigin {

    NULL(0), SOIL(1), WATER(2), FIRE(3), WIND(4);

    private int code;

    private static Random tao = new Random();

    PointOrigin(int code) {
        this.code = code;
    }

    public static PointOrigin create() {
        int i = tao.nextInt(values().length);
        return values()[i];
    }

    public int getCode() {
        return code;
    }
}
