package com.acupt.amazing.util;

import com.acupt.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liujie on 2017/11/7.
 */
public class LoginUtil {

    private static String KEY_LOGIN_DISTRIBUTOR = "_acupt_login_user";

    public static boolean isLogin(HttpServletRequest request) {
        return getLoginUser(request) != null;
    }

    public static User getLoginUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(KEY_LOGIN_DISTRIBUTOR);
    }

    public static void login(HttpServletRequest request, User user) {
        request.getSession().setAttribute(KEY_LOGIN_DISTRIBUTOR, user);
    }

    public static void exit(HttpServletRequest request) {
        request.getSession().invalidate();
    }
}
