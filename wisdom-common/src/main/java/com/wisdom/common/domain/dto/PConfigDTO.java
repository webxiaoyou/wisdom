package com.wisdom.common.domain.dto;

import java.time.LocalDateTime;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.wisdom.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 小程序配置表(PConfig)内容返回实体类表
 *
 * @author makejava
 * @since 2023-11-30 18:55:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PConfigDTO extends BaseEntity {

    private static final long serialVersionUID = 1L;

             
    /** 
    * id 
    */    
    @TableId(value = "id", type = IdType.AUTO) 
    private Long id; 
    
               
    /** 
    * 微信id 
    */           
    @TableField("wid") 
    private Long wid;
            
    /** 
    * 类型 
    */           
    @TableField("type") 
    private String type;
            
    /** 
    * 身份标识 
    */           
    @TableField("appid") 
    private String appid;
            
    /** 
    * 身份秘钥 
    */           
    @TableField("secret") 
    private String secret;
            
    /** 
    * 配置token 
    */           
    @TableField("token") 
    private String token;
            
    /** 
    * aeskey 
    */           
    @TableField("aeskey") 
    private String aeskey;
            
    /** 
    * 是否支付 
    */           
    @TableField("is_pay") 
    private String isPay;
            
    /** 
    * 是否使用 
    */           
    @TableField("status")
    private String status;
            
    /** 
    * 创建时间 
    */           
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
            
    /** 
    * 更新时间 
    */           
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
            
    /** 
    * 创建人 
    */           
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
            
    /** 
    * 修改人 
    */           
    @TableField(fill = FieldFill.INSERT)
    private String updateBy;
            
    /** 
    * 删除标志（0代表存在 2代表删除） 
    */           
    @TableField("del_flag") 
    private String delFlag;
       
}
