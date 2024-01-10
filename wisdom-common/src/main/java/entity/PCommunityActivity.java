package entity;

import java.time.LocalDateTime;

import java.util.Date;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import lombok.Data;
import java.time.LocalDateTime;
/**
 * 社区活动信息表(PCommunityActivity)表实体类
 *
 * @author wisdom
 * @since 2024-01-04 20:56:03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("p_community_activity")
public class PCommunityActivity  implements Serializable {

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
       
}
