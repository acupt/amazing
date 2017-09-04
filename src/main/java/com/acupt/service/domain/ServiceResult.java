package com.acupt.service.domain;

/**
 * Created by liujie on 2017/8/15.
 */
public class ServiceResult<T> {

    private boolean success;

    private String msg;

    private transient Throwable e;

    private T data;

    public ServiceResult(boolean success) {
        this.success = success;
    }

    public static <T> ServiceResult<T> newSuccess(T data) {
        return new ServiceResult<T>(true).setData(data);
    }

    public static <T> ServiceResult<T> newFailed(Throwable e) {
        return newFailed(e, e.getClass().getSimpleName() + ":" + e.getMessage());
    }

    public static <T> ServiceResult<T> newFailed(Throwable e, String msg) {
        return new ServiceResult<T>(false).setE(e).setMsg(msg);
    }

    public boolean isSuccess() {
        return success;
    }

    public ServiceResult<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ServiceResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Throwable getE() {
        return e;
    }

    public ServiceResult<T> setE(Throwable e) {
        this.e = e;
        return this;
    }

    public T getData() {
        return data;
    }

    public ServiceResult<T> setData(T data) {
        this.data = data;
        return this;
    }
}
