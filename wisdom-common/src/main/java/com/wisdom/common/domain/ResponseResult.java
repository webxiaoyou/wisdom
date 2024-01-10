package com.wisdom.common.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wisdom.common.enums.AppHttpCodeEnum;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 请求返回基类
 *
 * @author wisdom
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String msg;
    private T data;

    private static <T> ResponseResult<T> ResponseResult(Integer code, T data, String msg) {
        ResponseResult<T> r = new ResponseResult<>();
        r.setCode(code);
        r.setData(data);
        r.setMsg(msg);
        return r;
    }

    public static <T> ResponseResult<T> okResult() {
        return ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(), null, AppHttpCodeEnum.SUCCESS.getMsg());
    }
    public static <T> ResponseResult<T> okResult(AppHttpCodeEnum enums) {
        return ResponseResult(enums.getCode(),null, enums.getMsg());
    }
    public static <T> ResponseResult<T> okResult(int code, String msg) {
        return ResponseResult(code, null, msg);
    }

    public static <T> ResponseResult<T> okResult(T data, String msg) {
        return ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(), data, msg);
    }

    public static <T> ResponseResult<T> okResult(T data) {
        return ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(), data, AppHttpCodeEnum.SUCCESS.getMsg());
    }

    public static <T> ResponseResult<T> errorResult() {
        return ResponseResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), null, "操作失败");
    }
    public static <T> ResponseResult<T> errorResult(AppHttpCodeEnum enums) {
        return ResponseResult(enums.getCode(),null, enums.getMsg());
    }
    public static <T> ResponseResult<T> errorResult(int code, String msg) {
        return ResponseResult(code, null, msg);
    }

    public static <T> ResponseResult<T> errorResult(T data, String msg) {
        return ResponseResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), data, msg);
    }


//    public ResponseResult() {
//        this.code = AppHttpCodeEnum.SUCCESS.getCode();
//        this.msg = AppHttpCodeEnum.SUCCESS.getMsg();
//    }
//
//    public ResponseResult(Integer code, T data) {
//        this.code = code;
//        this.data = data;
//    }
//
//    public ResponseResult(Integer code, String msg, T data) {
//        this.code = code;
//        this.msg = msg;
//        this.data = data;
//    }
//
//
//    public ResponseResult( String msg, T data) {
//        this.code = AppHttpCodeEnum.USER_ERROR.getCode();
//        this.msg = msg;
//        this.data = data;
//    }
//
//    public ResponseResult(Integer code, String msg) {
//        this.code = code;
//        this.msg = msg;
//    }


//    public static <T> ResponseResult<T> okResult() {
//        return new ResponseResult<>();
//    }
//
//    public static <T> ResponseResult<T> okResult(int code, String msg) {
//        return new ResponseResult<>(code, msg);
//    }
//
//    public static <T> ResponseResult<T> okResult(T data) {
//        ResponseResult<T> result = new ResponseResult<>();
//        result.setData(data);
//        return result;
//    }
//
//    public static <T> ResponseResult<T> errorResult(int code, String msg) {
//        return new ResponseResult<>(code, msg);
//    }
//    public static <T> ResponseResult<T> errorResult(AppHttpCodeEnum enums) {
//        return new ResponseResult<>(enums.getCode(), enums.getMsg());
//    }
//
//    public static <T> ResponseResult<T> errorResult(AppHttpCodeEnum enums, String msg) {
//        return new ResponseResult<>(enums.getCode(), msg);
//    }
//
//    public static <T> ResponseResult<T> errorResult(int code, String msg) {
//        return new ResponseResult<>(code, msg);
//    }

    public static <T> ResponseResult<T> setAppHttpCodeEnum(AppHttpCodeEnum enums) {
        return okResult(enums.getCode(), enums.getMsg());
    }

    public ResponseResult<T> error(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public ResponseResult<T> ok(Integer code, T data) {
        this.code = code;
        this.data = data;
        return this;
    }

    public ResponseResult<T> ok(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        return this;
    }

    public ResponseResult<T> ok(T data) {
        this.data = data;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
