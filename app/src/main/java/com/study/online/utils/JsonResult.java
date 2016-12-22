package com.study.online.utils;

import java.io.Serializable;

/**
 * json 解析工具类
 * 庞品
 */
public class JsonResult<T> implements Serializable {
    private int code;
    private String info;
    private T response;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

