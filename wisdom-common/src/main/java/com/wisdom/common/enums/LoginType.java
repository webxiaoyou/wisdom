package com.wisdom.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录类型
 *
 * @author wisdom
 */
@Getter
@AllArgsConstructor
public enum LoginType {

    /**
     * 密码登录
     */
    PASSWORD("密码重试次数超过限制", "您已经超过密码重试限制 {0} 次"),

    /**
     * 短信登录
     */
    SMS("短信验证码重试次数超过限制", "您已经超过短信验证码重试限制 {0} 次"),

    /**
     * 小程序登录
     */
    XCX("小程序登录重试次数超过限制", "您已经超过小程序登录重试限制 {0} 次");

    /**
     * 登录重试超出限制提示
     */
    final String retryLimitExceed;

    /**
     * 登录重试限制计数提示
     */
    final String retryLimitCount;
}
