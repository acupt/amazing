package com.acupt.amazing.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liujie on 2017/3/24.
 */
@Component
public class ContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

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

    public static String getAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }

    public static ApplicationContext getContext() {
        return applicationContext;
    }
}
