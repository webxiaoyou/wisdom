package com.wisdom.common.domain.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wisdom.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 物业通知表(PNotification)内容返回实体类表
 *
 * @author wisdom
 * @since 2023-12-19 14:03:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PNotificationDTO extends BaseEntity {

    private static final long serialVersionUID = 1L;

             
    /** 
    * 通知ID 
    */    
    @TableId(value = "notification_id", type = IdType.AUTO) 
    private Long notificationId; 
    
               
    /** 
    * 物业项目ID 
    */           
    @TableField("property_id") 
    private Long propertyId;
            
    /** 
    * 通知类型 
    */           
    @TableField("notification_type") 
    private String notificationType;
            
    /** 
    * 接收对象类型 
    */           
    @TableField("target_type") 
    private String targetType;
            
    /** 
    * 接收对象ID（楼栋ID、楼层ID、用户ID等） 
    */           
    @TableField("target_id") 
    private Long targetId;

    /**
     * 接收对象IDS（楼栋ID、楼层ID、用户ID等）
     */
    @TableField(exist = false)
    private List<Long> targetIds;
            
    /** 
    * 通知标题 
    */           
    @TableField("title") 
    private String title;

    /**
     * 通知内容
     */
    @TableField("content")
    private String content;

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
       
}
