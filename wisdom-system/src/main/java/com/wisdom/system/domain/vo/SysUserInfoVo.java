package com.wisdom.system.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class SysUserInfoVo {
    
    @TableId(value = "id", type = IdType.AUTO)
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
    /**
     * 部门ID
     */
    @TableField("dept_id")
    private Long deptId;
    
    @TableField("supervisor_id")
    private Integer supervisorId;

    
    @TableField("position")
    private String position;

    
    @TableField("salary")
    private BigDecimal salary;


    @TableField("hire_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime hireDate;


    @TableField("termination_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime terminationDate;

    
    @TableField("work_location")
    private String workLocation;

    
    @TableField("login_ip")
    private String loginIp;

    
    @TableField("login_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime loginDate;

    
    @TableField("create_by")
    private String createBy;


    @TableField("create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;


    @TableField("update_by")
    private String updateBy;


    @TableField("update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    
    @TableField("remark")
    private String remark;

    
    @TableField("extension1")
    private String extension1;

    
    @TableField("extension2")
    private String extension2;

    
    @TableField("extension3")
    private String extension3;

    
    //    查询的时候告诉它不要查询这个字段
    @TableField(exist = false)
    private List<Long> roleIds;
}
