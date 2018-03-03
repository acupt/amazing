package com.acupt.world;

import java.util.Random;

/**
 * Created by liujie on 2018/3/2.
 */
public enum PointOrigin {

    NULL(0, "无"), SOIL(1, "地"), WATER(2, "水"), FIRE(3, "火"), WIND(4, "风");

    private int code;

    private String name;

    private static Random tao = new Random();

    PointOrigin(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static PointOrigin get(int code) {
        for (PointOrigin origin : values()) {
            if (origin.getCode() == code) {
                return origin;
            }
        }
        return NULL;
    }

    public static PointOrigin create() {
        int i = tao.nextInt(values().length);
        return values()[i];
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
