package com.acupt.amazing.web;

import com.acupt.amazing.util.ContextUtil;
import com.acupt.entity.User;
import com.acupt.tool.ToolEnum;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liujie on 2017/9/15.
 */
public class LoginContext implements Serializable {

    private static final long serialVersionUID = -1857541855641247126L;

    private User user;

    private long loginTime;

    private String ip;

    private List<ToolEnum> tool = Arrays.asList(ToolEnum.values());

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

    public List<ToolEnum> getTool() {
        return tool;
    }

    public void setTool(List<ToolEnum> tool) {
        this.tool = tool;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
