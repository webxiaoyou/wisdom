package com.wisdom.common.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.wisdom.common.domain.model.LoginUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 小程序登录
 * </p>
 *
 * @author wisdom
 * @since 2023-10-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class WxUser extends LoginUser {

    /**
     * id
     */
    private Long userId;


    /**
     * 父id
     */
    private Long pid;
    /**
     * unionid
     */
    private String unionid;
    /**
     * openid
     */
    private String openid;
    /**
     * 手机
     */
    private String tel;
    /**
     * 用户类型
     */
    private String utype;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 性别
     */
    private String sex;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 城市
     */
    private String city;
    /**
     * 地址
     */
    private String address;
    /**
     * 积分
     */
    private Long cedits;
    /**
     * 劵数
     */
    private Long tickets;
    /**
     * 余额
     */
    private BigDecimal balance;
    /**
     * 身份
     */
    private String identity;
    /**
     * 最近登录
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime lastLoginTime;
    /**
     * 备注
     */
    private String remark;
}
