package com.wisdom.common.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 房屋信息表(PPropertyUnit)内容返回实体类表
 *
 * @author wisdom
 * @since 2023-12-03 20:02:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PPropertyUnitAndPUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

             
    /** 
    * 房屋ID 
    */    
    @TableId(value = "unit_id", type = IdType.AUTO) 
    private Long unitId; 
    
               
    /** 
    * 楼栋ID 
    */           
    @TableField("building_id") 
    private Long buildingId;
            
    /** 
    * 楼栋编号 
    */           
    @TableField("building_number") 
    private String buildingNumber;
            
    /** 
    * 楼层ID 
    */           
    @TableField("floor_id") 
    private Long floorId;
            
    /** 
    * 楼层编号 
    */           
    @TableField("floor_number") 
    private Integer floorNumber;
            
    /** 
    * 房屋编号 
    */           
    @TableField("unit_number") 
    private String unitNumber;
            
    /** 
    * 业主ID 
    */           
    @TableField("owner_id") 
    private Long ownerId;
            
    /** 
    * 认证状态（0未认证 1已认证 2认证未通过） 
    */           
    @TableField("authentication_status") 
    private String authenticationStatus;
            
    /** 
    * 图片地址 
    */           
    @TableField("image_url") 
    private String imageUrl;
            
    /** 
    * 备注 
    */           
    @TableField("remark") 
    private String remark;
            
    /** 
    * 创建者 
    */           
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
            
    /** 
    * 创建时间 
    */           
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
            
    /** 
    * 更新者 
    */           
    @TableField(fill = FieldFill.INSERT)
    private String updateBy;
            
    /** 
    * 更新时间 
    */           
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
            
    /** 
    * 删除标志（0代表存在 1代表删除） 
    */           
    @TableField("del_flag") 
    private String delFlag;
            
    /** 
    * 物业项目ID 
    */           
    @TableField("property_id") 
    private Long propertyId;
            
    /** 
    * 物业项目名称 
    */           
    @TableField("property_name") 
    private String propertyName;
    /**
     * id
     */
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
