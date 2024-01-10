package com.wisdom.system.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserPasswordDto {
    
    private Long id;

    
    private String account;

    
    private String password;

    
    private String newPassword;

    
    private String confirmPassword;
}
