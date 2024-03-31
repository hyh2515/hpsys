package com.shou.hpsys.common.pojo;

import lombok.Getter;

import java.io.Serializable;

/**
 * @description: 对Ajax请求返回Json格式数据进行封装
 * @author: Yaohui Hu
 * @date 2024/3/17 16:17
 */
public class CommonResult<T> implements Serializable {
    private static final long serialVersionID = 1L;
    public static final int CODE_SUCCESS = 200;
    public static final int CODE_ERROR = 500;

    //    状态码
    private int code;

    //    提示语
    @Getter
    private String msg;

    //    返回数据
    @Getter
    private T data;

    public CommonResult() {}

    public CommonResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public CommonResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public CommonResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public CommonResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    //    ----------------------------构建-----------------------------
    // 构建成功
    public static <T> CommonResult<T> ok() {
        return new CommonResult<>(CODE_SUCCESS, "操作成功", null);
    }
    public static <T> CommonResult<T> ok(String msg) {
        return new CommonResult<>(CODE_SUCCESS, msg, null);
    }
    public static <T> CommonResult<T> code(int code) {
        return new CommonResult<>(code, null, null);
    }
    public static <T> CommonResult<T> data(T data) {
        return new CommonResult<>(CODE_SUCCESS, "操作成功", data);
    }

    // 构建失败
    public static <T> CommonResult<T> error() {
        return new CommonResult<>(CODE_ERROR, "服务器异常", null);
    }
    public static <T> CommonResult<T> error(String msg) {
        return new CommonResult<>(CODE_ERROR, msg, null);
    }

    // 构建指定状态码
    public static <T> CommonResult<T> get(int code, String msg, T data) {
        return new CommonResult<>(code, msg, data);
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\": " + code +
                ", \"msg\": \"" + msg + '\"' +
                ", \"data\": \"" + data + '\"' +
                '}';
    }
}
