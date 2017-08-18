package com.acupt.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by liujie on 2017/3/18.
 */
public class GsonUtil {

    private static Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> cls) {
        return gson.fromJson(json, cls);
    }
}
