package com.wisdom.system.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wisdom.common.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleDto  extends BaseEntity {

    
    private String roleName;

    
    private String status;

    
    private String roleKey;

    
    private LocalDateTime   createTime;

    
    private LocalDateTime    updateTime;

}
