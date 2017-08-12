package com.acupt.amazing.controller;

import com.acupt.amazing.util.ContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liujie on 2017/8/12.
 */
@RequestMapping("/ip")
@Controller
public class IpController {

    @RequestMapping("")
    @ResponseBody
    public String index(HttpServletRequest request) {
        return ContextUtil.getRemoteIp(request);
    }
}
