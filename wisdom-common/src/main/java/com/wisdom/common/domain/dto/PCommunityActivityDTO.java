package com.wisdom.common.domain.dto;

import java.time.LocalDateTime;

import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.*;
import com.wisdom.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 社区活动信息表(PCommunityActivity)内容返回实体类表
 *
 * @author wisdom
 * @since 2024-01-04 20:48:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PCommunityActivityDTO extends BaseEntity {

    private static final long serialVersionUID = 1L;

             
    /** 
    * 活动唯一标识符 
    */    
    @TableId(value = "activity_id", type = IdType.AUTO) 
    private Long activityId; 
    
               
    /** 
    * 物业项目的唯一标识符 
    */           
    @TableField("property_id") 
    private Long propertyId;
            
    /** 
    * 活动标题 
    */           
    @TableField("title") 
    private String title;
            
    /** 
    * 活动描述 
    */           
    @TableField("description") 
    private String description;

    /**
     * 活动封面URL或文件路径
     */
    @TableField("cover_url")
    private String coverUrl;

    /** 
    * 活动内容 
    */           
    @TableField("content") 
    private String content;
            
    /** 
    * 活动地点 
    */           
    @TableField("location") 
    private String location;
            
    /** 
    * 活动开始时间 
    */           
    @TableField("start_time") 
    private LocalDateTime startTime;
            
    /** 
    * 活动结束时间 
    */           
    @TableField("end_time") 
    private LocalDateTime endTime;
            
    /** 
    * 报名开始时间 
    */           
    @TableField("registration_start_time") 
    private LocalDateTime registrationStartTime;
            
    /** 
    * 报名结束时间 
    */           
    @TableField("registration_end_time") 
    private LocalDateTime registrationEndTime;
            
    /** 
    * 最大参与人数 
    */           
    @TableField("max_participants") 
    private Integer maxParticipants;
            
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
     * 开始时间与结束时间
     */
    @TableField(exist = false)
    private List<LocalDateTime> params ;
}
