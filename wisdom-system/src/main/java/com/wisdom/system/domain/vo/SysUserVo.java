package com.wisdom.system.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class SysUserVo {
    
    private Integer pageNum = 1;

    
    private Integer pageSize =15;

    
    private Long id;

    
    @TableField("account")
    private String account;

    
    @TableField("name")
    private String name;

    
    @TableField("email")
    private String email;

    
    @TableField("age")
    private Integer age;

    
    @TableField("sex")
    private String sex;

    
    @TableField("avatar")
    private String avatar;

    
    @TableField("phone")
    private String phone;

    
    @TableField("role_id")
    private Integer roleId;

    
    @TableField("status")
    private String status;

    
    @TableField("del_flag")
    private String delFlag;

    
    @TableField("supervisor_id")
    private Integer supervisorId;

    
    @TableField("position")
    private String position;

    
    @TableField("salary")
    private BigDecimal salary;

    
    @TableField("hire_date")
    private LocalDate hireDate;

    
    @TableField("termination_date")
    private LocalDate terminationDate;

    
    @TableField("work_location")
    private String workLocation;

    
    @TableField("login_ip")
    private String loginIp;

    
    @TableField("login_date")

    private LocalDateTime loginDate;



    
    @TableField("update_by")
    private String updateBy;



    
    @TableField("remark")
    private String remark;



}
