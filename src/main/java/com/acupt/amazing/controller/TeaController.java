package com.acupt.amazing.controller;

import com.acupt.amazing.util.ContextUtil;
import com.acupt.amazing.web.LoginContext;
import com.acupt.domain.Tea;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
public class TeaController {

    private Map<String, Tea> nameMap = Tea.getNameMap();

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        String agent = ContextUtil.getAgent(request);
        if (agent != null && agent.toLowerCase().startsWith("curl/")) {
            return "forward:/tea/ip";
        }
        return Tea.BIU.getResource();
    }

    @RequestMapping(value = "/topbar", method = RequestMethod.GET)
    public ModelAndView topbar(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("topbar");
        mv.addObject("ctx", LoginContext.get(request));
        return mv;
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ModelAndView ip(HttpServletRequest request, HttpServletResponse response, @PathVariable String name) {
        Tea tea = nameMap.get(name);
        if (tea != null) {
            ModelAndView mv = new ModelAndView(tea.getResource());
            mv.addObject("ctx", LoginContext.get(request));
            return mv;
        }
        try {
            response.sendError(404);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
