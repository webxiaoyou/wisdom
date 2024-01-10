package com.wisdom.system.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserLoginDto {
    
    @TableField("account")
    private String account;

    
    @TableField("password")
    private String password;
}
