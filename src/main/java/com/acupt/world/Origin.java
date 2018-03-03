package com.acupt.world;

import java.util.Random;

/**
 * Created by liujie on 2018/3/2.
 */
public enum Origin {

    NULL(0, "空"), SOIL(1, "地"), WATER(2, "水"), FIRE(3, "火"), WIND(4, "风");

    private int code;

    private String name;

    private static Random tao = new Random();

    Origin(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Origin get(int code) {
        for (Origin origin : values()) {
            if (origin.getCode() == code) {
                return origin;
            }
        }
        return NULL;
    }

    public static Origin create() {
        int i = tao.nextInt(values().length);
        return values()[i];
    }

    public String getMsg() {
        if (this == NULL) {
            return "空空如也";
        }
        return "满满的，都是" + getName();
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
