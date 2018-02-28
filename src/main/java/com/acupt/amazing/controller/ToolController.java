package com.acupt.amazing.controller;

import com.acupt.amazing.util.ContextUtil;
import com.acupt.domain.Result;
import com.acupt.tool.JsonTool;
import com.acupt.util.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liujie on 2017/8/12.
 */
@RequestMapping("/tool")
@Controller
public class ToolController {

    @RequestMapping(value = "/2json", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> json(String s) {
        return new Result<String>().setData(JsonTool.formatJson(s));
    }

    @RequestMapping(value = "/2bjtime", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> bjtime(@RequestParam(required = false, defaultValue = "0") Long t) {
        return new Result<String>().setData(DateUtil.format(DateUtil.newDate(t)));
    }

    @RequestMapping(value = "/ip", method = RequestMethod.GET)
    @ResponseBody
    public Object ip(HttpServletRequest request) {
        String agent = ContextUtil.getAgent(request).toLowerCase();
        if (agent != null && agent.startsWith("curl/")) {
            return ContextUtil.getRemoteIp(request) + "\n";
        }
        return new Result<String>().setData(ContextUtil.getRemoteIp(request));
    }
}
