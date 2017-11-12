package com.acupt.domain;

import com.acupt.util.GsonUtil;

/**
 * Created by liujie on 2017/8/15.
 */
public class Result<T> {

    private int code;

    private String msg;

    private T data;

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public Result(int code) {
        this.code = code;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public boolean isOk() {
        return code == 0;
    }

    public String getMsg() {
        return msg;
    }

    public Result<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return GsonUtil.toJson(this);
    }
}
