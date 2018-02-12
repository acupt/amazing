package com.acupt.tool;

import com.acupt.util.StringUtil;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liujie on 2018/2/12.
 */
public enum ToolEnum {
    JSON(1, "json", ""),
    TIMESTAMP(2, "timestamp", "");

    private int code;
    private String name;
    private String desc;

    ToolEnum(int code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public static Map<Integer, ToolEnum> getCodeMap() {
        Map<Integer, ToolEnum> map = new TreeMap<>();
        for (ToolEnum e : values()) {
            map.put(e.getCode(), e);
        }
        return map;
    }

    public static Map<String, ToolEnum> getNameMap() {
        Map<String, ToolEnum> map = new TreeMap<>();
        for (ToolEnum e : values()) {
            map.put(e.getName(), e);
        }
        return map;
    }

    public String getUri() {
        return "/" + name;
    }

    public String getResource() {
        return "/tool/" + name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        if (StringUtil.isBlank(desc)) {
            return name();
        }
        return desc;
    }
}
