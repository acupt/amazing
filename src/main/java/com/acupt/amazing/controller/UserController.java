package com.acupt.amazing.controller;

import com.acupt.amazing.view.ApiResult;
import com.acupt.dao.UserDAO;
import com.acupt.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by liujie on 2017/8/14.
 */
@RequestMapping("/u")
@Controller
public class UserController {

    @Resource
    private UserDAO userDAO;

    @RequestMapping("/get")
    @ResponseBody
    public ApiResult get(Long id) {
        User userDO = userDAO.findOne(id);
        return new ApiResult().put("user", userDO);
    }

    @RequestMapping("/new")
    @ResponseBody
    public ApiResult insert(User user) {
        userDAO.save(user);
        return new ApiResult().put("user", user);
    }
}
