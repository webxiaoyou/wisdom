package com.wisdom.common.enums;

/**
 * 信息返回枚举
 *
 * @author wisdom
 */
public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200, "操作成功"),
    ADD_SUCCESS(200, "新增成功"),
    UP_SUCCESS(200, "修改成功"),
    ADD_ERROR(422, "新增失败"),
    UP_ERROR(422, "修改失败"),
    // 登录
    NEED_LOGIN(401, "需要登录后操作"),
    LOGIN_ABNORMAL(402, "用户状态异常，禁止登录"),
    LOGIN_STATUS(402, "用户已被停用，禁止登录,请联系管理员"),

    INVALID_TOKEN(401, "用户状态过期请重新登录"),
    NO_OPERATOR_AUTH(403, "无权限操作"),
    DATA_NOT_NULL(404, "数据不存在"),
    FILE_DATA_NOT_NULL(404, "文件数据不存在"),
    SYSTEM_ERROR(500, "出现错误"),
    USER_ERROR(500, "用户不存在"),
    USERNAME_EXIST(501, "用户名已存在"),
    PHONENUMBER_EXIST(502, "手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    DATA_ERROR(504, "数据已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    CONTENT_NOT_NULL(506, "内容不能为空"),
    LOGIN_ERROR(505, "用户名或密码错误");
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage) {
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
