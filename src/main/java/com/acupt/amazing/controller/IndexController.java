package com.acupt.amazing.controller;

import com.acupt.amazing.util.ContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liujie on 2017/8/12.
 */
@RequestMapping("/")
@Controller
public class IndexController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        String agent = ContextUtil.getAgent(request);
        if (agent == null) {
            return "index";
        }
        if (agent != null && agent.toLowerCase().startsWith("curl/")) {
            return "forward:/ip";
        }
        return "index";
    }
}
