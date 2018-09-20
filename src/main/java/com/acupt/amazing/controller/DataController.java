package com.acupt.amazing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public DataResult test(@RequestBody DataRequst dataRequst) {
        logger.info("test {}", dataRequst);
        DataResult result = check(dataRequst.username, dataRequst.password);
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

    public static class DataRequst {
        private String username;
        private String password;
        private Map<String, Object> parameters;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Map<String, Object> getParameters() {
            return parameters;
        }

        public void setParameters(Map<String, Object> parameters) {
            this.parameters = parameters;
        }

        @Override
        public String toString() {
            return "DataRequst{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", parameters=" + parameters +
                    '}';
        }
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
