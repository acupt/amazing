package com.acupt.domain;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liujie on 2018/2/12.
 */
public enum Tea {
    BIU(10001, "biu"),
    JSON(20001, "json"),
    TIMESTAMP(20002, "timestamp"),
    QQ(30001, "qq");

    private int code;
    private String name;

    Tea(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Map<Integer, Tea> getCodeMap() {
        Map<Integer, Tea> map = new TreeMap<>();
        for (Tea e : values()) {
            map.put(e.getCode(), e);
        }
        return map;
    }

    public static Map<String, Tea> getNameMap() {
        Map<String, Tea> map = new TreeMap<>();
        for (Tea e : values()) {
            map.put(e.getName(), e);
        }
        return map;
    }

    public String getUri() {
        return "/" + name;
    }

    public String getResource() {
        return "tea/" + name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return name();
    }
}
