package com.wisdom.common.domain.vo;

import com.baomidou.mybatisplus.annotation.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 小程序用户信息表(PUser)内容返回实体类表
 *
 * @author wisdom
 * @since 2023-12-01 11:01:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PUserVo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;


    /**
     * 父id
     */
    @TableField("pid")
    private Long pid;

    /**
     * unionid
     */
    @TableField("unionid")
    private String unionid;

    /**
     * openid
     */
    @TableField("openid")
    private String openid;

    /**
     * 手机
     */
    @TableField("tel")
    private String tel;

    /**
     * 用户类型
     */
    @TableField("utype")
    private String utype;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 用户姓名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 是否认证（0已认证 1未认证）
     */
    @TableField("authenticated")
    private String authenticated;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 性别
     */
    @TableField("sex")
    private String sex;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 城市
     */
    @TableField("city")
    private String city;

    /**
     * 地址
     */
    @TableField("address")
    private String address;

    /**
     * 积分
     */
    @TableField("cedits")
    private Long cedits;

    /**
     * 劵数
     */
    @TableField("tickets")
    private Long tickets;

    /**
     * 余额
     */
    @TableField("balance")
    private Double balance;

    /**
     * 车牌号
     */
    @TableField("license_plate")
    private String licensePlate;

    /**
     * 身份证
     */
    @TableField("identity")
    private String identity;

    /**
     * 最近登录
     */
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT)
    private String updateBy;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @TableField("del_flag")
    private String delFlag;

    /**
     * 头像ossid
     */
    @TableField("avatar_ossid")
    private String avatarOssid;

    /**
     * 账号
     */
    @TableField("account")
    private String account;


}
