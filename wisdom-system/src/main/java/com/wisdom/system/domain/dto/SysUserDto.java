package com.wisdom.system.domain.dto;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserDto {
    
    private Long id;

    
    @TableField(insertStrategy = FieldStrategy.NOT_EMPTY)
    private String account;

    
    @TableField(insertStrategy = FieldStrategy.NOT_EMPTY)
    private String name;

    
    @TableField(insertStrategy = FieldStrategy.NOT_EMPTY)
    @Size(min = 0, max = 50, message = "邮箱长度不能超过{max}个字符")
    private String password;

    
    private String email;

    
    private Integer age;

    
    private String sex;

    
    private String avatar;

    
    private String phone;

    
    private Integer roleId;

    
    private String status;


    
    private Integer supervisorId;

    
    private String position;
    /**
     * 部门ID
     */
    @TableField("dept_id")
    private Long deptId;
    
    private BigDecimal salary;


    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime hireDate;


    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime terminationDate;

    
    private String workLocation;

    
    private String loginIp;

    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime loginDate;

    
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    
    @TableField(fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    
    @TableField(fill = FieldFill.INSERT)
    private String updateBy;

    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    
    private String remark;

    
    @TableField(exist = false)
    private List<Integer> roleIds;

}
