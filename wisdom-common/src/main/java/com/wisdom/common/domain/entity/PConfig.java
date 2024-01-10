package com.wisdom.common.domain.entity;

import java.time.LocalDateTime;
import java.util.Date;

import java.util.Date;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import lombok.Data;

/**
 * 小程序配置表(PConfig)表实体类
 *
 * @author makejava
 * @since 2023-11-28 22:31:16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("p_config")
public class PConfig  implements Serializable {

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
    * 状态（0正常 1停用）
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
