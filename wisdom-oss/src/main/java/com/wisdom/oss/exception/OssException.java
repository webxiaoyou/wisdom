package com.wisdom.oss.exception;

/**
 * OSS异常类
 *
 * @author wisdom
 */
public class OssException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OssException(String msg) {
        super(msg);
    }

}
