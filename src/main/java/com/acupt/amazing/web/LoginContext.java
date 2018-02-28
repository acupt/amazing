package com.acupt.amazing.web;

import com.acupt.amazing.util.ContextUtil;
import com.acupt.domain.Tea;
import com.acupt.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liujie on 2017/9/15.
 */
public class LoginContext implements Serializable {

    private static final long serialVersionUID = -1857541855641247126L;

    private User user;

    private long loginTime;

    private String ip;

    private List<Tea> tea = Arrays.asList(Tea.values());

    private Map<String, Object> data = new HashMap<>();

    public LoginContext() {
    }

    private LoginContext(User user) {
        this.user = user;
        this.loginTime = System.currentTimeMillis();
    }

    public static LoginContext get(HttpServletRequest request) {
        LoginContext ctx = (LoginContext) request.getSession().getAttribute(SessionKey.LOGIN_CONTEXT);
        if (ctx == null) {
            ctx = new LoginContext();
        }
        ctx.setIp(ContextUtil.getRemoteIp(request));
        return ctx;
    }

    public static void login(HttpServletRequest request, User user) {
        request.getSession().setAttribute(SessionKey.LOGIN_CONTEXT, new LoginContext(user));
    }

    public static void logout(HttpServletRequest request) {
        request.getSession().removeAttribute(SessionKey.LOGIN_CONTEXT);
    }

    public boolean isLogined() {
        return user != null;
    }

    public Object putData(String key, Object value) {
        return data.put(key, value);
    }

    public Object getData(String key) {
        return data.get(key);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<Tea> getTea() {
        return tea;
    }

    public void setTea(List<Tea> tea) {
        this.tea = tea;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
