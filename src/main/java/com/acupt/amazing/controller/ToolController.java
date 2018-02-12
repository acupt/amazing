package com.acupt.amazing.controller;

import com.acupt.amazing.util.ContextUtil;
import com.acupt.amazing.web.LoginContext;
import com.acupt.domain.Result;
import com.acupt.tool.JsonTool;
import com.acupt.tool.ToolEnum;
import com.acupt.util.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by liujie on 2017/8/12.
 */
@RequestMapping("")
@Controller
public class ToolController {

    private Map<String, ToolEnum> nameMap = ToolEnum.getNameMap();

    @RequestMapping(value = "/topbar", method = RequestMethod.GET)
    public ModelAndView topbar(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("tool/topbar");
        mv.addObject("ctx", LoginContext.get(request));
        return mv;
    }

    @RequestMapping(value = "/{tool}", method = RequestMethod.GET)
    public String ip(HttpServletResponse response, @PathVariable String tool) {
        ToolEnum toolEnum = nameMap.get(tool);
        if (toolEnum != null) {
            return toolEnum.getResource();
        }
        try {
            response.sendError(404);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

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
