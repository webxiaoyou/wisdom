package com.wisdom.common.domain.entity;

import java.time.LocalDateTime;

import java.util.Date;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import lombok.Data;
import java.time.LocalDateTime;
/**
 * 投诉建议信息表(PComplaintSuggestion)表实体类
 *
 * @author wisdom
 * @since 2024-01-02 22:12:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("p_complaint_suggestion")
public class PComplaintSuggestion  implements Serializable {

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
     * 阅读状态（0已阅读 1未阅读）
     */
    @TableField("status")
    private String status;

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

    /**
     * 投诉/建议（0投诉 1建议）
     */
    @TableField("is_suggest")
    private String isSuggest;
}
