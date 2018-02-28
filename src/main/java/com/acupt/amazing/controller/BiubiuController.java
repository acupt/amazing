package com.acupt.amazing.controller;

import com.acupt.amazing.util.ContextUtil;
import com.acupt.amazing.web.LoginContext;
import com.acupt.dao.UserDAO;
import com.acupt.domain.Result;
import com.acupt.entity.Biubiu;
import com.acupt.entity.User;
import com.acupt.service.BiubiuService;
import com.acupt.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by liujie on 2017/8/14.
 */
@RequestMapping("/biu")
@Controller
public class BiubiuController {

    @Resource
    private BiubiuService biubiuService;

    @Resource
    private UserDAO userDAO;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> get() {
        return biubiuService.biu();
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> post(HttpServletRequest request, @RequestParam("biu") String biu) {
        if (StringUtil.isBlank(biu)) {
            return new Result<>();
        }
        biu = biu.trim();
        Result result = login(request, biu);
        if (result.isOk()) {
            return result;
        }
        Biubiu biubiu = new Biubiu();
        biubiu.setContent(biu);
        biubiu.setIp(ContextUtil.getRemoteIp(request));
        biubiu.setAgent(ContextUtil.getAgent(request));
        biubiu.setGmtCreated(new Date());
        biubiuService.insert(biubiu);
        return new Result<>(biubiu.getContent());
    }

    private Result<String> login(HttpServletRequest request, String order) {
        if (!order.startsWith("-u")) {
            return new Result<>(2);
        }
        if (!order.contains(" ")) {
            return new Result<>(3);
        }
        String[] arr = order.substring(2).split(" ");
        if (arr.length != 2) {
            return new Result<>(4);
        }
        User user = userDAO.findByName(arr[0]);
        if (user == null) {
            return new Result<>(5);
        }
        if (!user.getPassword().equals(arr[1])) {
            return new Result<>(6);
        }
        LoginContext.login(request, user);
        return new Result<>("welcome, " + user.getNick());
    }
}
