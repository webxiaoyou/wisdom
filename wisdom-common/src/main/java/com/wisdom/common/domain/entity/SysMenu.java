package com.wisdom.common.domain.entity;

import java.time.LocalDateTime;
import java.util.Date;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import lombok.Data;

/**
 * 菜单权限表(SysMenu)表实体类
 *
 * @author makejava
 * @since 2023-11-28 22:32:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_menu")
public class SysMenu  implements Serializable {

    private static final long serialVersionUID = 1L;

             
    /** 
    * 菜单ID 
    */    
    @TableId(value = "id", type = IdType.AUTO) 
    private Long id; 
    
               
    /** 
    * 菜单名称 
    */           
    @TableField("menu_name") 
    private String menuName;
            
    /** 
    * 父菜单ID 
    */           
    @TableField("parent_id") 
    private Long parentId;
            
    /** 
    * 显示顺序 
    */           
    @TableField("order_num") 
    private Integer orderNum;
            
    /** 
    * 路由地址 
    */           
    @TableField("path") 
    private String path;
            
    /** 
    * 组件路径 
    */           
    @TableField("component") 
    private String component;
            
    /** 
    * 是否缓存（0缓存 1不缓存） 
    */           
    @TableField("is_cache") 
    private Integer isCache;
            
    /** 
    * 是否为外链（0是 1否） 
    */           
    @TableField("is_frame") 
    private Integer isFrame;
            
    /** 
    * 菜单类型（M目录 C菜单 F按钮） 
    */           
    @TableField("menu_type") 
    private String menuType;
            
    /** 
    * 菜单状态（0显示 1隐藏） 
    */           
    @TableField("visible") 
    private String visible;
            
    /** 
    * 菜单状态（0正常 1停用） 
    */           
    @TableField("status") 
    private String status;
            
    /** 
    * 权限标识 
    */           
    @TableField("perms") 
    private String perms;
            
    /** 
    * 菜单图标 
    */           
    @TableField("icon") 
    private String icon;
            
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
                  
    @TableField("del_flag") 
    private String delFlag;

    @TableField(exist = false)
    private List children;

}
