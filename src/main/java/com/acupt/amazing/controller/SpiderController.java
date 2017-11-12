package com.acupt.amazing.controller;

import com.acupt.domain.Result;
import com.acupt.qspider.Qspider;
import com.scienjus.smartqq.callback.LoginCallback;
import com.scienjus.smartqq.model.UserInfo;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liujie on 2017/11/11.
 */
@RequestMapping("/qq")
@Controller
public class SpiderController {

    private static Map<String, Qspider> qspiderMap = new ConcurrentHashMap<>();

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, Qspider> entry : qspiderMap.entrySet()) {
            map.put(entry.getKey(), entry.getValue().getState());
        }
        return new ModelAndView("qq").addObject("map", map);
    }

    @RequestMapping(value = "/pause", method = RequestMethod.POST)
    @ResponseBody
    public Result pause(@RequestParam("id") String id) {
        Qspider qspider = qspiderMap.get(id);
        if (qspider == null) {
            return new Result(1, id + " not exist");
        }
        qspider.setState(Qspider.StateEnum.PAUSE);
        return new Result();
    }

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @ResponseBody
    public Result start(@RequestParam("id") String id) {
        Qspider qspider = qspiderMap.get(id);
        if (qspider == null) {
            return new Result(1, id + " not exist");
        }
        qspider.setState(Qspider.StateEnum.RUNNING);
        return new Result();
    }

    @RequestMapping(value = "/stop", method = RequestMethod.POST)
    @ResponseBody
    public Result stop(@RequestParam("id") String id) {
        Qspider qspider = qspiderMap.remove(id);
        if (qspider == null) {
            return new Result(1, id + " not exist");
        }
        qspider.close();
        return new Result();
    }

    @RequestMapping(value = "/qrcode", method = RequestMethod.GET)
    public void qrcode(HttpServletResponse response) {
        Qspider qspider = new Qspider().init();
        try {
            InputStream inputStream = qspider.getQRCode();
            IOUtils.copy(inputStream, response.getOutputStream());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return;
        }
        qspider.start(new LoginCallback() {
            @Override
            public void onFailed(com.scienjus.smartqq.model.Result<?> result) {
                logger.error("login failed {} {}", result.getCode(), result.getMsg());
            }

            @Override
            public void onSuccess(UserInfo userInfo) {
                qspiderMap.put(userInfo.getAccount(), qspider);
            }

            @Override
            public void onException(Throwable throwable) {
                logger.error("login exception " + throwable.getMessage(), throwable);
            }
        });
    }

}
