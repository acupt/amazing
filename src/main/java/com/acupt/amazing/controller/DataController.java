package com.acupt.amazing.controller;

import com.acupt.domain.Result;
import com.acupt.util.GsonUtil;
import com.acupt.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liujie
 */
@RequestMapping("/data")
@Controller
public class DataController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public DataResult test(String username, String password, Object parameters) {
        logger.info("test {} {} {}", username, password, GsonUtil.toJson(parameters));
        DataResult result = check(username, password);
        Map<String, Object> data = new HashMap<>();
        data.put("apiVersion", "1.0");
        data.put("name", "liujie-test");
        result.setResult(data);
        return result;
    }

    private <T> DataResult<T> check(String username, String password) {
        if ("liujie".equals(username) && "1234".equals(password)) {
            return DataResult.success();
        }
        return DataResult.error(10086, "用户信息验证失败");
    }

    private static class DataResult<T> {

        private int code = 200;

        private String message = "OK";

        private T result;

        public static <T> DataResult<T> success() {
            return new DataResult<>();
        }

        public static <T> DataResult<T> success(T res) {
            return new DataResult<>(200, "OK", res);
        }

        public static <T> DataResult<T> error(int code, String message) {
            return new DataResult<>(code, message);
        }

        public DataResult() {
        }

        public DataResult(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public DataResult(int code, String message, T result) {
            this.code = code;
            this.message = message;
            this.result = result;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public T getResult() {
            return result;
        }

        public void setResult(T result) {
            this.result = result;
        }
    }
}
