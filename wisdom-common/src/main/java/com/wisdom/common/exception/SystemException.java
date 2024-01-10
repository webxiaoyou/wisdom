package com.wisdom.common.exception;


import com.wisdom.common.enums.AppHttpCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 错误信息
 *
 * @author wisdom
 */
public class SystemException extends RuntimeException{

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

    public SystemException(String message) {
        super(message);
        // 使用自定义的错误码和传入的消息
        this.msg = message;
    }

    public SystemException(int code,String message) {
        super(message);
        this.code = code;
        // 使用自定义的错误码和传入的消息
        this.msg = message;
    }

    // 新增构造函数，接收占位符的值
    public SystemException(int code, String message, Object... args) {
        super(String.format(message, args));
        this.code = code;
        this.msg = String.format(message, args);
    }
    
}