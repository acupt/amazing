package com.acupt.util;

import java.util.Map;

/**
 * Created by liujie on 2018/3/3.
 */
public class XmlUtil {

    public static String toXmlStr(Map map) {
        StringBuilder sb = new StringBuilder("<xml>");
        for (Object key : map.keySet()) {
            String k = key.toString();
            Object v = map.get(key);
            sb.append("<").append(k).append(">");
            if (v instanceof String) {
                sb.append("<![CDATA[").append(v).append("]]>");
            } else if (v instanceof Number) {
                sb.append(v);
            } else if (v instanceof Map) {
                sb.append(toXmlStr((Map) v));
            }
            sb.append("</").append(k).append(">");
        }
        return sb.append("</xml>").toString();
    }
}
