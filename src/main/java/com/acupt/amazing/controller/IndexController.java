package com.acupt.amazing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by liujie on 2017/8/12.
 */
@RequestMapping("/")
@Controller
public class IndexController {

    @RequestMapping("")
    public String index() {
        return "index";
    }
}
