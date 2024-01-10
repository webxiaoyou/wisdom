package com.wisdom.common.domain.entity;

import java.time.LocalDateTime;
import java.util.Date;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.*;
import com.wisdom.common.constants.UserConstants;
import com.wisdom.common.domain.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 用户信息表(SysUser)表实体类
 *
 * @author makejava
 * @since 2023-11-28 22:32:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class SysUser  extends BaseEntity {

    private static final long serialVersionUID = 1L;

             
    /** 
    * 用户ID 
    */    
    @TableId(value = "id", type = IdType.AUTO) 
    private Long id; 
    
               
    /** 
    * 账号 
    */           
    @TableField("account")
    @NotBlank(message = "用户账号不能为空")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过{max}个字符")
    private String account;
            
    /** 
    * 姓名 
    */           
    @TableField("name") 
    private String name;
            
    /** 
    * 密码 
    */           
    @TableField("password") 
    private String password;
            
    /** 
    * 用户邮箱 
    */           
    @TableField("email") 
    private String email;
            
    /** 
    * 年龄 
    */           
    @TableField("age") 
    private Integer age;
            
    /** 
    * 性别(0男 1女 2未知) 
    */           
    @TableField("sex") 
    private String sex;
            
    /** 
    * 头像 
    */           
    @TableField("avatar") 
    private String avatar;
            
    /** 
    * 电话 
    */           
    @TableField("phone") 
    private String phone;
            
    /** 
    * 角色 0超级管理员 ， 1管理员， 2 普通账号 
    */           
    @TableField("role_id") 
    private Integer roleId;
            
    /** 
    * 帐号状态（0正常 1停用） 
    */           
    @TableField("status") 
    private String status;
            
    /** 
    * 删除标志（0代表存在 1代表删除） 
    */           
    @TableField("del_flag") 
    private String delFlag;
            
    /** 
    * 上级员工ID 
    */           
    @TableField("supervisor_id") 
    private Integer supervisorId;
            
    /** 
    * 职位 
    */           
    @TableField("position") 
    private String position;
            
    /** 
    * 工资 
    */           
    @TableField("salary") 
    private Double salary;
            
    /** 
    * 入职日期 
    */           
    @TableField("hire_date") 
    private LocalDateTime hireDate;
            
    /** 
    * 离职日期 
    */           
    @TableField("termination_date") 
    private LocalDateTime terminationDate;
            
    /** 
    * 部门ID 
    */           
    @TableField("dept_id") 
    private Long deptId;
            
    /** 
    * 工作地点 
    */           
    @TableField("work_location") 
    private String workLocation;
            
    /** 
    * 最后登录IP 
    */           
    @TableField("login_ip") 
    private String loginIp;
            
    /** 
    * 最后登录时间 
    */           
    @TableField("login_date") 
    private LocalDateTime loginDate;
            
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
    * 备注 
    */           
    @TableField("remark") 
    private String remark;
            
    /** 
    * 扩展字段 1 
    */           
    @TableField("extension1") 
    private String extension1;
            
    /** 
    * 扩展字段 2 
    */           
    @TableField("extension2") 
    private String extension2;
            
    /** 
    * 扩展字段 3 
    */           
    @TableField("extension3") 
    private String extension3;
            
    /** 
    * 用户类型（sys_user系统用户） 
    */           
    @TableField("user_type") 
    private String userType;

    /**
     * 用户权限
     */
    @TableField(exist = false)
    private List roleIds;


    public boolean isAdmin() {
        return UserConstants.ADMIN_ID.equals(this.id);
    }
}
