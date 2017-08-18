package com.acupt.amazing.view;

import com.acupt.service.domain.ServiceResult;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liujie on 2017/8/11.
 */
public class ApiResult implements Serializable {

    private static final long serialVersionUID = -5304302598814680856L;

    /**
     * 0表示流程正确，其他值表示错误
     */
    private int code = 0;

    /**
     * 返回给用户的提示信息，code = 0 时一般不需要
     */
    private String msg = "OK";

    /**
     * 返回给前端的数据放在此集合里
     * Date, Calendar -> 时间戳
     */
    private Map<String, Object> data = new HashMap<String, Object>();

    public ApiResult() {
    }

    public ApiResult(int code) {
        this.code = code;
    }

    public ApiResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ApiResult fromServiceResult(String dataName, ServiceResult serviceResult) {
        ApiResult apiResult = new ApiResult().setMsg(serviceResult.getMsg());
        if (!serviceResult.isSuccess()) {
            apiResult.setCode(1);
        } else {
            if (serviceResult.getData() == null) {
                apiResult.setCode(2333);
                if (serviceResult.getMsg() == null) {
                    apiResult.setMsg("nothing");
                }
            } else {
                apiResult.put(dataName, serviceResult.getData());
            }
        }
        return apiResult;
    }

    public ApiResult put(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public ApiResult putAll(Map<String, Object> map) {
        data.putAll(map);
        return this;
    }

    public int getCode() {
        return code;
    }

    public ApiResult setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ApiResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public ApiResult setData(Map<String, Object> data) {
        this.data = data;
        return this;
    }

    public static void main(String[] args) {

    }
}
