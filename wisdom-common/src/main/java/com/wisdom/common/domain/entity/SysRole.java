package com.wisdom.common.domain.entity;

import java.time.LocalDateTime;
import java.util.Date;

import java.util.Date;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import lombok.Data;

/**
 * 角色信息表(SysRole)表实体类
 *
 * @author makejava
 * @since 2023-11-28 22:34:09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
public class SysRole  implements Serializable {

    private static final long serialVersionUID = 1L;

             
    /** 
    * 角色ID 
    */    
    @TableId(value = "id", type = IdType.AUTO) 
    private Long id; 
    
               
    /** 
    * 角色名称 
    */           
    @TableField("role_name") 
    private String roleName;
            
    /** 
    * 角色权限字符串 
    */           
    @TableField("role_key") 
    private String roleKey;
            
    /** 
    * 显示顺序 
    */           
    @TableField("role_sort") 
    private Integer roleSort;
            
    /** 
    * 角色状态（0正常 1停用） 
    */           
    @TableField("status") 
    private String status;
            
    /** 
    * 删除标志（0代表存在 1代表删除） 
    */           
    @TableField("del_flag") 
    private String delFlag;
            
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
       
}
