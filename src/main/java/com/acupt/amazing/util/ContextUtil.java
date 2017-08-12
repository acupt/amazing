package com.acupt.amazing.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liujie on 2017/3/24.
 */
public class ContextUtil {

    public static String getRemoteIp(HttpServletRequest request) {
        String ip = (String) request.getAttribute("remote-ip");
        if (ip != null) {
            return ip;
        }
        ip = request.getHeader("X-real-ip");//先从nginx自定义配置获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        request.setAttribute("remote-ip", ip);
        return ip;
    }
}
