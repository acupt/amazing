package com.acupt.amazing.controller;

import com.acupt.amazing.util.ContextUtil;
import com.acupt.amazing.view.ApiResult;
import com.acupt.domain.Biubiu;
import com.acupt.service.BiubiuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/get")
    @ResponseBody
    public ApiResult get() {
        return ApiResult.fromServiceResult("biu", biubiuService.biu());
    }

    @RequestMapping("/post")
    @ResponseBody
    public ApiResult post(HttpServletRequest request, @RequestParam("biu") String biu) {
        Biubiu biubiu = new Biubiu();
        biubiu.setContent(biu);
        biubiu.setIp(ContextUtil.getRemoteIp(request));
        biubiu.setAgent(ContextUtil.getAgent(request));
        biubiu.setGmtCreated(new Date());
        biubiuService.insert(biubiu);
        return new ApiResult().put("biu", biubiu.getContent());
    }
}
