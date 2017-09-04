package com.acupt.amazing.controller;

import com.acupt.amazing.util.ContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liujie on 2017/8/12.
 */
@RequestMapping("/ip")
@Controller
public class IpController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public String index(HttpServletRequest request) {
        String agent = ContextUtil.getAgent(request).toLowerCase();
        if (agent != null && agent.startsWith("curl/")) {
            return ContextUtil.getRemoteIp(request) + "\n";
        }
        return ContextUtil.getRemoteIp(request);
    }
}
