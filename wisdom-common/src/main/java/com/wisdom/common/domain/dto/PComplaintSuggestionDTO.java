package com.wisdom.common.domain.dto;

import java.time.LocalDateTime;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.wisdom.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 投诉建议信息表(PComplaintSuggestion)内容返回实体类表
 *
 * @author wisdom
 * @since 2024-01-02 22:12:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PComplaintSuggestionDTO extends BaseEntity {

    private static final long serialVersionUID = 1L;

             
    /** 
    * 投诉建议唯一标识符 
    */    
    @TableId(value = "id", type = IdType.AUTO) 
    private Long id; 
    
               
    /** 
    * 物业项目的唯一标识符 
    */           
    @TableField("property_id") 
    private Long propertyId;
            
    /** 
    * 用户的唯一标识符 
    */           
    @TableField("user_id") 
    private Long userId;
            
    /** 
    * 投诉建议内容 
    */           
    @TableField("content") 
    private String content;

    /**
     * 阅读状态（0已阅读 1未阅读）
     */
    @TableField("status")
    private String status;

    /** 
    * 创建时间 
    */           
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
            
    /** 
    * 创建人 
    */           
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
            
    /** 
    * 更新时间 
    */           
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
            
    /** 
    * 更新人 
    */           
    @TableField(fill = FieldFill.INSERT)
    private String updateBy;
       
}
