package com.acupt.amazing.controller;

import com.acupt.domain.form.wx.Wxmsg;
import com.acupt.util.DateUtil;
import com.acupt.util.GsonUtil;
import com.acupt.util.XmlUtil;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liujie on 2018/3/3.
 */
@RequestMapping("/wx")
@Controller
public class WxController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/msg", method = RequestMethod.GET)
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("wx get " + request.getQueryString());
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        out.print(echostr);
        out.close();
    }

    @RequestMapping(value = "/msg", headers = {"content-type=application/xml"}, method = RequestMethod.POST)
    public void post(HttpServletResponse response, @RequestBody(required = false) Wxmsg msg) throws IOException, DocumentException {
        logger.info(GsonUtil.toJson(msg));
        if (msg == null) {
            response.setContentType("text/xml;charset=UTF-8");
            response.getWriter().write("");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("ToUserName", msg.getFromUserName());
        map.put("FromUserName", msg.getToUserName());
        map.put("MsgType", "text");
        map.put("Content", "hello, now is " + DateUtil.format(new Date()));
        if (msg.getCreateTime() > 1020064410758L) {
            map.put("CreateTime", System.currentTimeMillis());
        } else {
            map.put("CreateTime", System.currentTimeMillis() / 1000);
        }
        response.setContentType("text/xml;charset=UTF-8");
        response.getWriter().write(XmlUtil.toXmlStr(map));
    }

}
