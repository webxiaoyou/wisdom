package com.wisdom.system.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleSaveDto {

    
    private Long id;

    
    private String roleName;

    
    private String roleKey;

    
    private Integer roleSort;

    
    private String status;

    
    @TableField("remark")
    private String remark;

    
    @TableField(exist = false)
    private List<Integer> menuIds;
}
